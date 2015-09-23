package chatroom;

import java.util.List;
import java.util.Scanner;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class ChatClient {
	static boolean stop;
	
	public static void main(String[] args) throws TTransportException {
		
		TTransport transport = new TSocket("localhost",9090);
		transport.open();
		TProtocol prot = new TBinaryProtocol(transport);
		final ChatRoom.Client chatClient = new ChatRoom.Client(prot);
		
		// Start the server polling thread
		Thread poller = new Thread() {
			public void run() {
				try {
					pollChatroom(chatClient, 1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		poller.start();
		pollUser(chatClient);
		stopPolling();
		try {
			poller.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static synchronized void stopPolling() {
		stop = true;
	}
	
	public static synchronized boolean mustStop() {
		return stop;
	}
	
	public static void pollChatroom(ChatRoom.Client client, int interval) throws InterruptedException {
		long lastTS = System.currentTimeMillis();
		//1. Loop at interval
		while(!mustStop()) {
			List<ChatMessage> msgList = null;
			try { 		//2. update messages
				msgList = client.updateFrom(lastTS);
			} catch (TException e) { e.printStackTrace();	}
			
			if(msgList != null && msgList.size() > 0) {
				for(ChatMessage cm: msgList) {
					System.out.println("[" + cm.getUsername() + "] says: " + cm.getContent());
					// 3. find last Timestamp
					lastTS = lastTS < cm.getTimestamp() ? cm.getTimestamp() : lastTS;
				}
				Thread.sleep(interval);
			}
		}
	}

	public static void pollUser(ChatRoom.Client client) {
		Scanner scan = new Scanner(System.in);
		String name, msg="";
		System.out.println("What is your name ?");
		name = scan.nextLine();
		
		while(!msg.equals("bye")) {
			msg = scan.nextLine();
			ChatMessage cm = new ChatMessage();
			cm.setContent(msg);
			cm.setUsername(name);
			cm.setTimestamp(System.currentTimeMillis());
			
			try {
				client.send(cm);
			} catch (TException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
}

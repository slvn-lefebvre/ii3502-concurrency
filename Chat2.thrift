namespace java chatroom

struct ChatMessage {
 1: i64 timestamp,
 2: string username,
 3: string content
}


service ChatRoom {
    void send(1: ChatMessage cm);
    list<ChatMessage> updateFrom(1: i64  timestamp);
}

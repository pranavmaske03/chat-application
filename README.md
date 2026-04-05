# Java Client-Server Chat Application

A simple real-time chat app built using **Java Sockets**. Two people can chat with each other over a network using the terminal. Uses **multithreading** so both sides can send and receive messages at the same time.

## Features
- Real-time messaging between client and server
- Usernames — each person sets their name before chatting starts
- Timestamps on every message so you know when it was sent
- Graceful disconnection — if either side exits, the other is notified cleanly
- Built on Java Socket Programming with TCP
- Multithreaded so sending and receiving never block each other

## Technologies Used
- Java
- TCP Sockets
- Multithreading
- `java.time` for timestamps

## How to Run

Open two terminal windows.

#### 1. Start the Server
```bash
javac Server.java
java Server
```
You will be asked to enter your name. Once the client connects, the chat begins.

#### 2. Start the Client
```bash
javac Client.java
java Client
```
You will be asked to enter your name. Once connected, you will see the server's name and can start chatting right away.

#### 3. Chatting
- Just type your message and press Enter to send
- Messages show the sender's name and the time they were sent
- Type `end` to close the chat on your side
- If the other person disconnects, you will be notified automatically

## How It Works
1. The **server** starts and waits for someone to connect on port 2100
2. Both sides enter their names — these are exchanged automatically as a handshake
3. A background thread on each side listens for incoming messages continuously
4. The main thread handles sending messages
5. Type `end` or close the terminal to end the session cleanly

## File Structure
```
📂 chat_application
 ├── Server.java   # Server-side implementation
 ├── Client.java   # Client-side implementation
 └── README.md     # Project documentation
```

## Example Session

**Server terminal:**
```
Server Application is running...
Waiting for client to connect...
Enter your name: Alice
Client connected!
Bob has joined the chat!
Enter message:

[10:35 AM] Bob: Hey Alice!
Enter message: Hi Bob, good to see you here!
```

**Client terminal:**
```
Client Application is running...
Connected to server!
Enter your name: Bob
Alice is online. Start chatting!
Enter message: Hey Alice!

[10:35 AM] Alice: Hi Bob, good to see you here!
Enter message:
```

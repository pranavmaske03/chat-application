import java.net.*;
import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

class Client {
    static volatile boolean connected = true;

    public static void main(String arg[]) throws Exception {
        System.out.println("Client Application is running...");

        Socket s = new Socket("127.0.0.1", 2100);
        System.out.println("Connected to server!");

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        BufferedReader br1 = new BufferedReader(new InputStreamReader(s.getInputStream()));
        BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter your name: ");
        String clientName = br2.readLine();

        String serverName = br1.readLine();
        System.out.println(serverName + " is online. Start chatting!");

        bw.write(clientName);
        bw.newLine();
        bw.flush();

        final String finalServerName = serverName;

        Thread receiveThread = new Thread(() -> {
            try {
                String str;
                while ((str = br1.readLine()) != null) {
                    String time = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a"));
                    System.out.println("\n[" + time + "] " + finalServerName + ": " + str);
                    System.out.print("Enter message: ");
                }
            } catch (Exception e) {
            } finally {
                connected = false;
                System.out.println("\n" + finalServerName + " disconnected.");
            }
        });
        receiveThread.start();

        String str1;
        while (connected && !(str1 = br2.readLine()).equalsIgnoreCase("end")) {
            if (!connected) break;
            try {
                bw.write(str1);
                bw.newLine();
                bw.flush();
            } catch (Exception e) {
                System.out.println("Could not send — server disconnected.");
                break;
            }
        }

        System.out.println("Chat ended.");
        try { bw.close(); } catch (Exception ignored) {}
        try { br1.close(); } catch (Exception ignored) {}
        try { br2.close(); } catch (Exception ignored) {}
        try { s.close(); } catch (Exception ignored) {}
    }
}
import java.net.*;
import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

class Server {
    static volatile boolean connected = true;

    public static void main(String arg[]) throws Exception {
        System.out.println("Server Application is running...");
        ServerSocket ssobj = new ServerSocket(2100);
        System.out.println("Waiting for client to connect...");

        Socket s = ssobj.accept();
        System.out.println("Client connected!");

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        BufferedReader br1 = new BufferedReader(new InputStreamReader(s.getInputStream()));
        BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter your name: ");
        String serverName = br2.readLine();

        bw.write(serverName);
        bw.newLine();
        bw.flush();

        String clientName = br1.readLine();
        System.out.println(clientName + " has joined the chat!");

        final String finalClientName = clientName;

        Thread receiveThread = new Thread(() -> {
            try {
                String str;
                while ((str = br1.readLine()) != null) {
                    String time = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a"));
                    System.out.println("\n[" + time + "] " + finalClientName + ": " + str);
                    System.out.print("Enter message: ");
                }
            } catch (Exception e) {
            } finally {
                connected = false;
                System.out.println("\n" + finalClientName + " disconnected.");
            }
        });
        receiveThread.start();

        String str2;
        while (connected && !(str2 = br2.readLine()).equalsIgnoreCase("end")) {
            if (!connected) break;
            try {
                bw.write(str2);
                bw.newLine();
                bw.flush();
            } catch (Exception e) {
                System.out.println("Could not send — client disconnected.");
                break;
            }
        }

        System.out.println("Chat ended.");
        try { bw.close(); } catch (Exception ignored) {}
        try { br1.close(); } catch (Exception ignored) {}
        try { br2.close(); } catch (Exception ignored) {}
        try { s.close(); } catch (Exception ignored) {}
        try { ssobj.close(); } catch (Exception ignored) {}
    }
}
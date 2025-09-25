package uno;

import java.io.*;
import java.net.*;

/**
 * Xử lý kết nối của từng client
 */
public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private UNOServer server;
    private BufferedReader in;
    private PrintWriter out;
    private String playerName;
    private boolean connected;
    
    public ClientHandler(Socket socket, UNOServer server) throws IOException {
        this.clientSocket = socket;
        this.server = server;
        this.connected = true;
        
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }
    
    @Override
    public void run() {
        try {
            // Nhận tên người chơi từ client
            String nameMessage = in.readLine();
            if (nameMessage != null && nameMessage.startsWith("PLAYER_NAME:")) {
                playerName = nameMessage.substring(12);
                // Server already has predefined player names, just acknowledge
                sendMessage("NAME_ACCEPTED:" + playerName);
            }
            
            // Lắng nghe messages từ client
            String inputLine;
            while (connected && (inputLine = in.readLine()) != null) {
                handleMessage(inputLine);
            }
            
        } catch (IOException e) {
            System.err.println("Error in client handler: " + e.getMessage());
        } finally {
            close();
        }
    }
    
    private void handleMessage(String message) {
        System.out.println("ClientHandler.handleMessage - Player: " + playerName + ", Message: '" + message + "'");
        
        if (message.startsWith("MOVE:")) {
            String move = message.substring(5);
            server.handlePlayerMove(playerName, move);
        } else if (message.equals("CATCH_UNO")) {
            // Xử lý trực tiếp lệnh CATCH_UNO
            System.out.println("ClientHandler: Processing CATCH_UNO from " + playerName);
            server.handlePlayerMove(playerName, "CATCH_UNO");
        } else if (message.equals("DISCONNECT")) {
            connected = false;
        } else {
            System.out.println("ClientHandler: Unhandled message '" + message + "' from " + playerName);
        }
    }
    
    public void sendMessage(String message) {
        if (out != null && connected) {
            out.println(message);
        }
    }
    
    public void close() {
        try {
            connected = false;
            if (in != null) in.close();
            if (out != null) out.close();
            if (clientSocket != null) clientSocket.close();
        } catch (IOException e) {
            System.err.println("Error closing client handler: " + e.getMessage());
        }
    }
    
    public String getPlayerName() {
        return playerName;
    }
    
    public boolean isConnected() {
        return connected;
    }
}

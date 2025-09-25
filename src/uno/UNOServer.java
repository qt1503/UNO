/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change tlick nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uno;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import javax.swing.*;
import java.awt.Font;

/**
 *
 * @author quytien
 */
public class UNOServer {
    private ServerSocket serverSocket;
    private List<ClientHandler> clients;
    private Game game;
    private boolean gameStarted;
    private int expectedPlayers;
    private List<String> playerNames;
    
    public UNOServer(int port, int expectedPlayers) throws IOException {
        serverSocket = new ServerSocket(port);
        clients = new ArrayList<>();
        this.expectedPlayers = expectedPlayers;
        playerNames = new ArrayList<>();
        gameStarted = false;
        
        System.out.println("UNO Server started on port " + port);
        System.out.println("Waiting for " + expectedPlayers + " players...");
    }
    
    public UNOServer(int port, String[] playerNames) throws IOException {
        serverSocket = new ServerSocket(port);
        clients = new ArrayList<>();
        this.expectedPlayers = playerNames.length;
        this.playerNames = new ArrayList<>();
        for (String name : playerNames) {
            this.playerNames.add(name);
        }
        gameStarted = false;
        
        System.out.println("UNO Server started on port " + port);
        System.out.println("Waiting for " + expectedPlayers + " players: " + this.playerNames);
    }
    
    public void startServer() {
        try {
            while (clients.size() < expectedPlayers) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
                
                System.out.println("Player " + clients.size() + " connected. " + 
                                 (expectedPlayers - clients.size()) + " players remaining.");
            }
            
            // Khi đủ người chơi, bắt đầu game
            startGame();
            
        } catch (IOException e) {
            System.err.println("Error in server: " + e.getMessage());
        }
    }
    
    private void startGame() {
        if (clients.size() == expectedPlayers && !gameStarted) {
            gameStarted = true;
            
            System.out.println("Starting game with " + playerNames.size() + " players: " + playerNames);
            System.out.println("Expected players: " + expectedPlayers + ", Connected clients: " + clients.size());
            
            // Tạo game với tên người chơi
            String[] playerIds = playerNames.toArray(new String[playerNames.size()]);
            System.out.println("Player IDs array: " + java.util.Arrays.toString(playerIds));
            game = new Game(playerIds);
            
            System.out.println("Game object created, starting game...");
            game.start(game);
            
            System.out.println("Game started, current player: " + game.getCurrentPlayer());
            System.out.println("Top card: " + game.getTopCard());
            
            // Thông báo cho tất cả client game bắt đầu
            broadcastMessage("GAME_START:" + String.join(",", playerNames));
            
            // Gửi trạng thái game ban đầu
            System.out.println("Sending initial game state to all clients...");
            sendGameState();
            
            System.out.println("Game started with players: " + Arrays.toString(playerIds));
        }
    }
    
    public synchronized void addPlayer(String playerName) {
        playerNames.add(playerName);
    }
    
    public synchronized void broadcastMessage(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }
    
    public synchronized void sendGameState() {
        if (game != null && game.getCurrentPlayer() != null) {
            try {
                for (int i = 0; i < clients.size(); i++) {
                    ClientHandler client = clients.get(i);
                    
                    // Gửi thông tin game state cho từng client
                    String gameState = buildGameState(i);
                    client.sendMessage("GAME_STATE:" + gameState);
                }
            } catch (Exception e) {
                System.err.println("Error sending game state: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    private String buildGameState(int playerIndex) {
        StringBuilder sb = new StringBuilder();
        
        try {
            // Kiểm tra playerIndex valid
            if (playerIndex < 0 || playerIndex >= playerNames.size()) {
                return "ERROR:Invalid player index";
            }
            
            // Thêm current player
            sb.append("CURRENT_PLAYER:").append(game.getCurrentPlayer()).append(";");
            
            // Thêm top card với màu hiệu lực
            sb.append("TOP_CARD:").append(game.getTopCardDisplay()).append(";");
            
            // Thêm bài của người chơi hiện tại
            String playerName = playerNames.get(playerIndex);
            ArrayList<UnoCard> playerHand = game.getPlayerHand(playerName);
            sb.append("HAND:");
            for (int i = 0; i < playerHand.size(); i++) {
                sb.append(playerHand.get(i).toString());
                if (i < playerHand.size() - 1) sb.append(",");
            }
            sb.append(";");
        
        // Thêm số lượng bài của các người chơi khác
        sb.append("OTHER_HANDS:");
        boolean first = true;
        for (int i = 0; i < playerNames.size(); i++) {
            if (i != playerIndex) {
                if (!first) sb.append(",");
                sb.append(playerNames.get(i)).append(":")
                  .append(game.getPlayerHandSize(playerNames.get(i)));
                first = false;
            }
        }
        sb.append(";");
        
        // Thêm thông tin có thể bắt UNO
        String catchablePlayer = game.findPlayerWithoutUno();
        boolean canCatchUno = (catchablePlayer != null && !catchablePlayer.equals(playerName));
        sb.append("UNO_CATCHABLE:").append(canCatchUno);
        
        } catch (Exception e) {
            System.err.println("Error building game state for player " + playerIndex + ": " + e.getMessage());
            return "ERROR:Game state error";
        }
        
        return sb.toString();
    }
    
    public synchronized void handlePlayerMove(String playerName, String move) {
        System.out.println("handlePlayerMove - playerName: '" + playerName + "', move: '" + move + "'");
        System.out.println("Current player from game: '" + (game != null ? game.getCurrentPlayer() : "null") + "'");
        System.out.println("Game null? " + (game == null) + ", getCurrentPlayer null? " + (game != null && game.getCurrentPlayer() == null));
        System.out.println("Players equal? " + (game != null && game.getCurrentPlayer() != null && game.getCurrentPlayer().equals(playerName)));
        
        if (game != null && game.getCurrentPlayer() != null && game.getCurrentPlayer().equals(playerName)) {
            // Xử lý nước đi của người chơi
            try {
                if (move.startsWith("PLAY_CARD:")) {
                    String cardIndexStr = move.substring(10);
                    int cardIndex = Integer.parseInt(cardIndexStr);
                    System.out.println("Playing card at index: " + cardIndex + " for player: " + playerName);
                    
                    // Lấy thẻ bài từ tay người chơi
                    UnoCard cardToPlay = game.getPlayerCard(playerName, cardIndex);
                    System.out.println("Card to play: " + cardToPlay);
                    
                    // Đặt màu khai báo (cho bài wild, tạm thời dùng màu của bài)
                    UnoCard.Color declaredColor = cardToPlay.getColor();
                    if (declaredColor == UnoCard.Color.Wild) {
                        // Tạm thời chọn màu ngẫu nhiên cho wild card
                        UnoCard.Color[] colors = {UnoCard.Color.Red, UnoCard.Color.Blue, UnoCard.Color.Green, UnoCard.Color.Yellow};
                        declaredColor = colors[0]; // Tạm thời chọn Red
                    }
                    
                    // Thực hiện nước đi
                    game.submitPlayerCard(playerName, cardToPlay, declaredColor);
                    System.out.println("Card played successfully. New current player: " + game.getCurrentPlayer());
                    
                    // Gửi lại game state mới cho tất cả client
                    sendGameState();
                    
                    // Kiểm tra game over
                    if (game.isGameOver()) {
                        String winner = game.getWinner();
                        broadcastMessage("GAME_OVER:" + winner);
                    }
                } else if (move.startsWith("PLAY_WILD_CARD:")) {
                    // Xử lý Wild card với màu đã chọn
                    String[] parts = move.substring(15).split(":");
                    int cardIndex = Integer.parseInt(parts[0]);
                    UnoCard.Color chosenColor = UnoCard.Color.valueOf(parts[1]);
                    
                    System.out.println("Playing Wild card at index: " + cardIndex + " with chosen color: " + chosenColor + " for player: " + playerName);
                    
                    // Lấy thẻ bài từ tay người chơi
                    UnoCard cardToPlay = game.getPlayerCard(playerName, cardIndex);
                    System.out.println("Wild card to play: " + cardToPlay);
                    
                    // Thực hiện nước đi với màu đã chọn
                    game.submitPlayerCard(playerName, cardToPlay, chosenColor);
                    System.out.println("Wild card played successfully with color: " + chosenColor + ". New current player: " + game.getCurrentPlayer());
                    
                    // Gửi lại game state mới cho tất cả client
                    sendGameState();
                    
                    // Kiểm tra game over
                    if (game.isGameOver()) {
                        String winner = game.getWinner();
                        broadcastMessage("GAME_OVER:" + winner);
                    }
                } else if (move.equals("DRAW_CARD")) {
                    game.submitDraw(playerName);
                    sendGameState();
                } else if (move.equals("CALL_UNO")) {
                    // Người chơi gọi UNO
                    game.setPlayerUnoStatus(playerName, true);
                    broadcastMessage("MESSAGE:" + playerName + " đã gọi UNO!");
                    sendGameState();
                }
            } catch (Exception e) {
                System.err.println("Error handling move: " + e.getMessage());
            }
        }
        
        // Xử lý bắt UNO (không cần kiểm tra lượt)
        if (move.equals("CATCH_UNO")) {
            System.out.println("DEBUG: Received CATCH_UNO command from " + playerName);
            try {
                String caughtPlayer = game.findPlayerWithoutUno();
                System.out.println("DEBUG: Found player without UNO: " + caughtPlayer);
                if (caughtPlayer != null && !caughtPlayer.equals(playerName)) {
                    System.out.println("CATCH UNO: " + playerName + " bắt " + caughtPlayer);
                    
                    // Phạt người chơi chưa gọi UNO
                    game.penalizePlayer(caughtPlayer, 2);
                    
                    // Thông báo cho tất cả người chơi
                    broadcastMessage("MESSAGE:" + playerName + " đã bắt được " + caughtPlayer + " chưa gọi UNO! " + caughtPlayer + " bị phạt rút 2 lá bài!");
                    
                    // Gửi lại game state để cập nhật số lá bài
                    sendGameState();
                    
                    System.out.println("CATCH UNO completed successfully");
                } else {
                    // Không có ai để bắt hoặc tự bắt chính mình
                    ClientHandler playerClient = findClientByName(playerName);
                    if (playerClient != null) {
                        playerClient.sendMessage("MESSAGE:Không có ai để bắt UNO!");
                    }
                    System.out.println("CATCH UNO failed: No valid target or self-targeting");
                }
            } catch (Exception e) {
                System.err.println("Error handling CATCH_UNO: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    private ClientHandler findClientByName(String playerName) {
        for (ClientHandler client : clients) {
            if (client.getPlayerName().equals(playerName)) {
                return client;
            }
        }
        return null;
    }
    
    public void stop() {
        try {
            gameStarted = false;
            for (ClientHandler client : clients) {
                client.close();
            }
            serverSocket.close();
        } catch (IOException e) {
            System.err.println("Error stopping server: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        try {
            // Hỏi số lượng người chơi
            String input = JOptionPane.showInputDialog("Enter number of players (2-10):");
            int numPlayers = Integer.parseInt(input);
            
            if (numPlayers < 2 || numPlayers > 10) {
                JLabel message = new JLabel("Invalid number of players. Must be between 2 and 10.");
                message.setFont(new Font("Arial", Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, message);
                return;
            }
            
            UNOServer server = new UNOServer(12345, numPlayers);
            server.startServer();
            
        } catch (IOException e) {
            System.err.println("Could not start server: " + e.getMessage());
        } catch (NumberFormatException e) {
            JLabel message = new JLabel("Invalid input. Please enter a number.");
            message.setFont(new Font("Arial", Font.BOLD, 16));
            JOptionPane.showMessageDialog(null, message);
        }
    }
}

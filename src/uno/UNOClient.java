/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uno;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author quytien
 */
public class UNOClient extends JFrame {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String playerName;
    private boolean connected;
    
    // GUI components
    private JPanel mainPanel;
    private JPanel handPanel;
    private JPanel otherPlayersPanel;
    private JLabel topCardLabel;
    private JLabel currentPlayerLabel;
    private JLabel playerNameLabel;
    private JButton drawCardButton;
    private JButton unoButton;
    private JButton catchUnoButton;
    private ArrayList<JButton> handButtons;
    private ArrayList<UnoCard> playerHand;
    private UnoCard topCard;
    private String currentPlayer;
    private boolean hasCalledUno;
    private Timer catchUnoTimer;
    
    public UNOClient() {
        connected = false;
        handButtons = new ArrayList<>();
        playerHand = new ArrayList<>();
        hasCalledUno = false;
        initializeGUI();
    }
    
    private void initializeGUI() {
        setTitle("UNO Client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        
        mainPanel = new JPanel(new BorderLayout());
        
        // Top panel - chỉ hiển thị lượt của ai
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setOpaque(false); // Cho phép background của mainPanel hiển thị qua
        currentPlayerLabel = new JLabel("Lượt của ");
        currentPlayerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(currentPlayerLabel);
        
        // Center panel - chứa tất cả thành phần chính
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false); // Cho phép background của mainPanel hiển thị qua
        
        // Panel cho thông tin người chơi ở trên cùng của center
        JPanel playerInfoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        playerInfoPanel.setOpaque(false);
        playerNameLabel = new JLabel("Người chơi: ");
        playerNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        playerInfoPanel.add(playerNameLabel);
        
        // Panel cho thẻ bài ở giữa
        JPanel cardPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        cardPanel.setOpaque(false);
        topCardLabel = new JLabel();
        topCardLabel.setPreferredSize(new Dimension(130, 182));
        topCardLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        cardPanel.add(topCardLabel);
        
        // Panel cho các nút ở dưới thẻ bài
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        
        drawCardButton = createStyledButton("Rút bài", new Color(255, 204, 204));
        drawCardButton.setPreferredSize(new Dimension(100, 30));
        drawCardButton.addActionListener(e -> drawCard());

        unoButton = createStyledButton("UNO!", new Color(255, 204, 204));
        unoButton.setPreferredSize(new Dimension(80, 30));
        unoButton.setVisible(false);
        unoButton.addActionListener(e -> callUno());

        catchUnoButton = createStyledButton("BẮT UNO", new Color(255, 100, 100));
        catchUnoButton.setPreferredSize(new Dimension(100, 30));
        catchUnoButton.setVisible(false);
        catchUnoButton.addActionListener(e -> catchUno());
        
        buttonPanel.add(drawCardButton);
        buttonPanel.add(unoButton);
        buttonPanel.add(catchUnoButton);
        
        // Panel cho bài của người chơi ở cuối center
        handPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        handPanel.setOpaque(false); // Cho phép background của mainPanel hiển thị qua
        handPanel.setPreferredSize(new Dimension(1200, 120));
        
        // Tạo panel chung cho game content (card + buttons)
        JPanel gameContentPanel = new JPanel(new BorderLayout());
        gameContentPanel.setOpaque(false);
        gameContentPanel.add(cardPanel, BorderLayout.CENTER);
        gameContentPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        centerPanel.add(playerInfoPanel, BorderLayout.NORTH);
        centerPanel.add(gameContentPanel, BorderLayout.CENTER);
        centerPanel.add(handPanel, BorderLayout.SOUTH);
        
        // Left panel - hiển thị người chơi khác với back cards
        otherPlayersPanel = new JPanel();
        otherPlayersPanel.setLayout(new BoxLayout(otherPlayersPanel, BoxLayout.Y_AXIS));
        otherPlayersPanel.setOpaque(false);
        
        // Tạo JScrollPane cho otherPlayersPanel
        JScrollPane scrollPane = new JScrollPane(otherPlayersPanel);
        scrollPane.setPreferredSize(new Dimension(220, 600)); // Chiều rộng 220 để chứa scrollbar
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.WEST); // Thêm scrollPane thay vì otherPlayersPanel trực tiếp
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        add(mainPanel);
        
        // Disable tất cả controls ban đầu
        setControlsEnabled(false);
    }

    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Create button background (white with pink hover effects)
                Color buttonBgColor = Color.WHITE;
                Color borderColor = backgroundColor; // Pink border
                Color textColor = Color.BLACK;
                
                if (getModel().isPressed()) {
                    buttonBgColor = new java.awt.Color(255, 204, 204, 100); // Darker pink when pressed
                    borderColor = new java.awt.Color(255, 204, 204, 100);
                    textColor = Color.WHITE; // White text on pink background
                } else if (getModel().isRollover()) {
                    buttonBgColor = backgroundColor; // Full pink on hover
                    borderColor = backgroundColor;
                    textColor = Color.WHITE; // White text on pink background
                }
                
                // Fill button background
                g2d.setColor(buttonBgColor);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50);
                
                // Draw pink border
                g2d.setColor(borderColor);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, 50, 50);
                
                // Draw text
                g2d.setColor(textColor);
                FontMetrics fm = g2d.getFontMetrics();
                int textX = (getWidth() - fm.stringWidth(getText())) / 2;
                int textY = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2d.drawString(getText(), textX, textY);
            }
        };
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(new java.awt.Color(255, 204, 204));
        button.setPreferredSize(new Dimension(280, 45));
        button.setMaximumSize(new Dimension(280, 45));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add mouse listeners để đảm bảo repaint khi hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                button.repaint();
            }
            
            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                button.repaint();
            }
        });
        
        return button;
    }

    
    public void connectToServer(String host, int port, String playerName) {
        try {
            socket = new Socket(host, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            
            this.playerName = playerName;
            playerNameLabel.setText("Người chơi: " + playerName);
            connected = true;
            
            // Gửi tên người chơi
            out.println("PLAYER_NAME:" + playerName);
            
            // Bắt đầu thread để nhận messages
            new Thread(this::listenToServer).start();
            
            // Chỉ hiển thị status trong title thay vì popup
            setTitle(getTitle() + " - Kết nối server thành công! Đang chờ người chơi khác...");
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Không thể kết nối server: " + e.getMessage());
        }
    }
    
    private void listenToServer() {
        try {
            String message;
            while (connected && (message = in.readLine()) != null) {
                handleServerMessage(message);
            }
        } catch (IOException e) {
            if (connected) {
                JOptionPane.showMessageDialog(this, "Mất kết nối với server: " + e.getMessage());
            }
        }
    }
    
    private void handleServerMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            if (message.startsWith("NAME_ACCEPTED:")) {
                // Tên được chấp nhận
                
            } else if (message.startsWith("GAME_START:")) {
                String players = message.substring(11);
                setTitle(getTitle().split(" - ")[0] + " - Trò chơi bắt đầu!");
                setControlsEnabled(true);
                
            } else if (message.startsWith("GAME_STATE:")) {
                String gameState = message.substring(11);
                System.out.println("Client received game state: " + gameState);
                updateGameState(gameState);
                
            } else if (message.startsWith("GAME_OVER:")) {
                String winner = message.substring(10);
                JOptionPane.showMessageDialog(this, "Trò chơi kết thúc! Người thắng: " + winner);
                setControlsEnabled(false);
            }
        });
    }
    
    private void updateGameState(String gameState) {
        String[] parts = gameState.split(";");
        
        for (String part : parts) {
            if (part.startsWith("CURRENT_PLAYER:")) {
                currentPlayer = part.substring(15);
                currentPlayerLabel.setText("Lượt của: " + currentPlayer);
                
                // Enable/disable controls dựa trên turn
                boolean isMyTurn = currentPlayer.equals(playerName);
                drawCardButton.setEnabled(isMyTurn);
                for (JButton button : handButtons) {
                    button.setEnabled(isMyTurn);
                }
                
            } else if (part.startsWith("TOP_CARD:")) {
                String cardInfo = part.substring(9);
                updateTopCard(cardInfo);
                
            } else if (part.startsWith("HAND:")) {
                String handInfo = part.substring(5);
                updatePlayerHand(handInfo);
                
            } else if (part.startsWith("OTHER_HANDS:")) {
                String otherHandsInfo = part.substring(12);
                updateOtherPlayersInfo(otherHandsInfo);
                
            } else if (part.startsWith("UNO_CATCHABLE:")) {
                String catchableInfo = part.substring(14);
                updateCatchUnoButton(catchableInfo);
            }
        }
    }
    
    private void updateTopCard(String cardInfo) {
        if (!cardInfo.isEmpty()) {
            // Load card image
            try {
                String imagePath = "/com/mycompany/uno/small/" + cardInfo + ".png";
                ImageIcon cardIcon = new ImageIcon(getClass().getResource(imagePath));
                if (cardIcon.getIconWidth() > 0) {
                    Image img = cardIcon.getImage().getScaledInstance(125, 175, Image.SCALE_SMOOTH);
                    topCardLabel.setIcon(new ImageIcon(img));
                    topCardLabel.setText("");
                    topCardLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    topCardLabel.setVerticalAlignment(SwingConstants.CENTER);
                } else {
                    throw new Exception("Lỗi hình ảnh");
                }
            } catch (Exception e) {
                // Fallback to text display
                // topCardLabel.setText("<html><center>Lá bài hiện tại<br>" + cardInfo + "</center></html>");
                topCardLabel.setIcon(null);
            }
            
            // Set background color dựa trên màu bài
            String color = cardInfo.split("-")[0];
            Color bgColor;
            switch (color) {
                case "Red": bgColor = new Color(243, 129, 129); break;
                case "Blue": bgColor = new Color(149, 225, 211); break;
                case "Yellow": bgColor = new Color(252, 227, 138); break;
                case "Green": bgColor = new Color(214, 247, 173); break;
                default: bgColor = Color.WHITE; break;
            }
            
            // Đổi màu background của tất cả các panel
            mainPanel.setBackground(bgColor);
            getContentPane().setBackground(bgColor);
            
            // Đổi màu các panel con
            Component[] components = mainPanel.getComponents();
            for (Component comp : components) {
                if (comp instanceof JPanel) {
                    comp.setBackground(bgColor);
                }
            }
            
            // Force repaint để hiển thị màu mới
            mainPanel.repaint();
            this.repaint();
        }
    }
    
    private void updatePlayerHand(String handInfo) {
        // Clear current hand
        handPanel.removeAll();
        handButtons.clear();
        playerHand.clear();

        if (!handInfo.isEmpty()) {
            String[] cards = handInfo.split(",");
            
            for (int i = 0; i < cards.length; i++) {
                String cardString = cards[i].trim();
                System.out.println("Processing card " + i + ": " + cardString);
                
                try {
                    // Parse card
                    UnoCard card = parseCard(cardString);
                    playerHand.add(card);
                    
                // Tạo button cho card
                JButton cardButton = new JButton(cardString);
                cardButton.setPreferredSize(new Dimension(80, 100));
                
                // Thêm hình ảnh cho card
                try {
                    String imagePath = "/com/mycompany/uno/small/" + cardString + ".png";
                    
                    ImageIcon cardIcon = new ImageIcon(getClass().getResource(imagePath));
                    if (cardIcon.getIconWidth() > 0) {  // Check if image loaded successfully
                        Image img = cardIcon.getImage().getScaledInstance(70, 90, Image.SCALE_SMOOTH);
                        cardButton.setIcon(new ImageIcon(img));
                        cardButton.setText(""); // Remove text when image is loaded
                        cardButton.setHorizontalTextPosition(JButton.CENTER);
                        cardButton.setVerticalTextPosition(JButton.BOTTOM);
                        cardButton.setIconTextGap(0);
                    } else {
                        throw new Exception("Image not found or invalid");
                    }
                } catch (Exception e) {
                    // Keep text as fallback
                    cardButton.setIcon(null);
                }
                
                final int cardIndex = i;
                cardButton.addActionListener(e -> playCard(cardIndex));                    handButtons.add(cardButton);
                    handPanel.add(cardButton);
                    System.out.println("Added card button: " + cardString);
                } catch (Exception e) {
                    System.out.println("Error parsing card: " + cardString + " - " + e.getMessage());
                }
            }
            
            // Logic hiển thị nút UNO
            updateUnoButtonVisibility();
        } else {
            System.out.println("Hand info is empty!");
            // Ẩn nút UNO khi không có bài
            unoButton.setVisible(false);
        }
        
        handPanel.revalidate();
        handPanel.repaint();
        
        // Force refresh the entire frame
        SwingUtilities.invokeLater(() -> {
            this.repaint();
            this.validate();
        });
        
        System.out.println("Hand panel updated with " + handPanel.getComponentCount() + " card buttons");
    }
    
    private void updateOtherPlayersInfo(String otherHandsInfo) {
        // Clear current other players display
        otherPlayersPanel.removeAll();
        
        if (!otherHandsInfo.isEmpty()) {
            String[] players = otherHandsInfo.split(",");
            
            for (String playerInfo : players) {
                String[] parts = playerInfo.split(":");
                if (parts.length == 2) {
                    String otherPlayerName = parts[0];
                    int cardCount = Integer.parseInt(parts[1]);
                    
                    // Tạo panel cho mỗi người chơi khác
                    JPanel playerPanel = new JPanel();
                    playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
                    playerPanel.setOpaque(false);
                    playerPanel.setBorder(BorderFactory.createTitledBorder(otherPlayerName));
                    
                    // Tạo panel để hiển thị back cards với layout tự động
                    JPanel cardsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 2));
                    cardsPanel.setOpaque(false);
                    
                    // Tính toán kích thước phù hợp cho cardsPanel
                    int cardsPerRow = 5; // Tối đa 5 lá bài mỗi hàng
                    int rows = (int) Math.ceil((double) cardCount / cardsPerRow);
                    int panelWidth = Math.min(cardCount, cardsPerRow) * 34 + 20; // 34 = 30 + 4 (kích thước card + khoảng cách)
                    int panelHeight = rows * 46 + 10; // 46 = 42 + 4 (chiều cao card + khoảng cách)
                    
                    cardsPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
                    
                    // Tạo back cards
                    for (int i = 0; i < cardCount; i++) {
                        JLabel backCard = createBackCard();
                        cardsPanel.add(backCard);
                    }
                    
                    // Label hiển thị số lượng bài
                    JLabel countLabel = new JLabel(cardCount + " lá bài");
                    countLabel.setFont(new Font("Arial", Font.BOLD, 12));
                    countLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    
                    playerPanel.add(cardsPanel);
                    playerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
                    playerPanel.add(countLabel);
                    playerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                    
                    // Set kích thước tự động cho playerPanel
                    int totalPanelHeight = panelHeight + 40 + 20 + 10; // cards + label + border + spacing
                    playerPanel.setPreferredSize(new Dimension(190, totalPanelHeight));
                    playerPanel.setMaximumSize(new Dimension(190, totalPanelHeight));
                    
                    otherPlayersPanel.add(playerPanel);
                }
            }
        }
        
        // Tự động điều chỉnh kích thước của otherPlayersPanel
        otherPlayersPanel.revalidate();
        otherPlayersPanel.repaint();
        
        // Force update layout của main panel
        SwingUtilities.invokeLater(() -> {
            mainPanel.revalidate();
            this.revalidate();
        });
    }
    
    private JLabel createBackCard() {
        JLabel backCard = new JLabel();
        backCard.setPreferredSize(new Dimension(30, 42));
        
        try {
            // Sử dụng hình ảnh card_back_alt.png
            ImageIcon backIcon = new ImageIcon(getClass().getResource("/com/mycompany/uno/small/card_back_alt.png"));
            if (backIcon.getIconWidth() > 0) {
                Image img = backIcon.getImage().getScaledInstance(30, 42, Image.SCALE_SMOOTH);
                backCard.setIcon(new ImageIcon(img));
            } else {
                // Fallback: tạo back card đơn giản bằng màu và text
                backCard.setOpaque(true);
                backCard.setBackground(new Color(0, 0, 139)); // Màu xanh đậm
                backCard.setText("UNO");
                backCard.setForeground(Color.WHITE);
                backCard.setFont(new Font("Arial", Font.BOLD, 8));
                backCard.setHorizontalAlignment(SwingConstants.CENTER);
                backCard.setVerticalAlignment(SwingConstants.CENTER);
            }
        } catch (Exception e) {
            // Fallback: tạo back card đơn giản
            backCard.setOpaque(true);
            backCard.setBackground(new Color(0, 0, 139)); // Màu xanh đậm
            backCard.setText("UNO");
            backCard.setForeground(Color.WHITE);
            backCard.setFont(new Font("Arial", Font.BOLD, 8));
            backCard.setHorizontalAlignment(SwingConstants.CENTER);
            backCard.setVerticalAlignment(SwingConstants.CENTER);
        }
        
        backCard.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        return backCard;
    }

    private UnoCard parseCard(String cardString) {
        // Temporary simple parsing
        String[] parts = cardString.split("-");
        UnoCard.Color color = UnoCard.Color.valueOf(parts[0]);
        UnoCard.Value value = UnoCard.Value.valueOf(parts[1]);
        return new UnoCard(color, value);
    }
    
    private void playCard(int cardIndex) {
        System.out.println("playCard called - cardIndex: " + cardIndex);
        System.out.println("connected: " + connected + ", currentPlayer: " + currentPlayer + ", playerName: " + playerName);
        
        if (connected && currentPlayer != null && currentPlayer.equals(playerName)) {
            // Kiểm tra xem có phải Wild card không
            if (cardIndex < playerHand.size()) {
                UnoCard card = playerHand.get(cardIndex);
                if (card.getColor() == UnoCard.Color.Wild) {
                    // Hiển thị PickColorFrame để chọn màu
                    showColorPicker(cardIndex);
                    return;
                }
            }
            
            // Reset trạng thái UNO khi chơi bài (có thể cần hiện nút UNO lại nếu còn 1 bài)
            hasCalledUno = false;
            
            System.out.println("Sending PLAY_CARD command for card index: " + cardIndex);
            out.println("MOVE:PLAY_CARD:" + cardIndex);
        } else {
            System.out.println("Cannot play card - not player's turn or not connected");
        }
    }
    
    private void drawCard() {
        System.out.println("drawCard called");
        System.out.println("connected: " + connected + ", currentPlayer: " + currentPlayer + ", playerName: " + playerName);
        
        if (connected && currentPlayer != null && currentPlayer.equals(playerName)) {
            System.out.println("Sending DRAW_CARD command");
            // Reset trạng thái UNO khi rút bài (sẽ có thêm bài)
            hasCalledUno = false;
            out.println("MOVE:DRAW_CARD");
        } else {
            System.out.println("Cannot draw card - not player's turn or not connected");
        }
    }
    
    private void updateUnoButtonVisibility() {
        // Hiển thị nút UNO chỉ khi:
        // 1. Có đúng 1 lá bài
        // 2. Chưa gọi UNO 
        boolean shouldShowUno = (playerHand.size() == 1) && !hasCalledUno;
        unoButton.setVisible(shouldShowUno);
        
        // Reset trạng thái UNO nếu có nhiều hơn 1 lá bài (người chơi đã rút thêm bài)
        if (playerHand.size() > 1) {
            hasCalledUno = false;
        }
    }
    
    private void callUno() {
        if (connected && currentPlayer != null && currentPlayer.equals(playerName)) {
            out.println("CALL_UNO");
            JOptionPane.showMessageDialog(this, "UNO!");
            hasCalledUno = true; // Đánh dấu đã gọi UNO
            unoButton.setVisible(false);
        }
    }
    
    private void catchUno() {
        System.out.println("DEBUG: catchUno() method called");
        System.out.println("  connected: " + connected);
        if (connected) {
            System.out.println("  Sending CATCH_UNO command to server...");
            out.println("CATCH_UNO");
            JOptionPane.showMessageDialog(this, "Bạn đã bắt được người chơi chưa gọi UNO!");
            catchUnoButton.setVisible(false);
            System.out.println("  CATCH_UNO command sent successfully");
        } else {
            System.out.println("  Cannot send CATCH_UNO - not connected");
        }
    }
    
    private void updateCatchUnoButton(String catchableInfo) {
        // catchableInfo format: "true" hoặc "false"
        boolean canCatchUno = "true".equals(catchableInfo.trim());
        
        System.out.println("DEBUG Catch UNO Button:");
        System.out.println("  canCatchUno: " + canCatchUno);
        System.out.println("  currentPlayer: " + currentPlayer);
        System.out.println("  playerName: " + playerName);
        
        // Cancel timer cũ nếu có
        if (catchUnoTimer != null) {
            catchUnoTimer.stop();
            catchUnoTimer = null;
        }
        
        // Ẩn nút trước khi bắt đầu timer
        catchUnoButton.setVisible(false);
        
        if (canCatchUno) {
            System.out.println("  Starting 3-second timer for catch UNO button...");
            
            // Tạo timer 3 giây để hiện nút bắt UNO (chỉ hiện cho người chơi khác)
            catchUnoTimer = new Timer(3000, e -> {
                System.out.println("  3 seconds elapsed - checking if should show CATCH UNO BUTTON...");
                // Chỉ hiện nút nếu có ai có thể bị bắt và không phải chính mình
                if (canCatchUno) {
                    catchUnoButton.setVisible(true);
                    System.out.println("  CATCH UNO BUTTON IS NOW VISIBLE!");
                }
                catchUnoTimer = null;
            });
            catchUnoTimer.setRepeats(false);
            catchUnoTimer.start();
        } else {
            System.out.println("  No one to catch - button remains hidden");
        }
    }
    
    private void showColorPicker(int cardIndex) {
        // Tạo PickColorFrame để chọn màu
        PickColorFrameForClient colorFrame = new PickColorFrameForClient(this, cardIndex);
        colorFrame.setVisible(true);
    }
    
    public void playWildCard(int cardIndex, UnoCard.Color chosenColor) {
        // Reset trạng thái UNO khi chơi Wild card
        hasCalledUno = false;
        
        // Gửi lệnh chơi Wild card với màu đã chọn đến server
        System.out.println("Sending PLAY_WILD_CARD command for card index: " + cardIndex + " with color: " + chosenColor);
        out.println("MOVE:PLAY_WILD_CARD:" + cardIndex + ":" + chosenColor);
    }
    
    private void setControlsEnabled(boolean enabled) {
        drawCardButton.setEnabled(enabled);
        for (JButton button : handButtons) {
            button.setEnabled(enabled);
        }
    }
    
    public void disconnect() {
        try {
            connected = false;
            if (out != null) out.println("DISCONNECT");
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            System.err.println("Error disconnecting: " + e.getMessage());
        }
    }
    
    @Override
    public void dispose() {
        disconnect();
        super.dispose();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            // Hỏi thông tin kết nối
            String host = JOptionPane.showInputDialog("Nhập server (mặc định: localhost):");
            if (host == null || host.trim().isEmpty()) host = "localhost";

            String portStr = JOptionPane.showInputDialog("Nhập server (mặc định: 12345):");
            int port = 12345;
            try {
                if (portStr != null && !portStr.trim().isEmpty()) {
                    port = Integer.parseInt(portStr);
                }
            } catch (NumberFormatException e) {
                port = 12345;
            }

            String playerName = JOptionPane.showInputDialog("Nhập tên người chơi của bạn:");
            if (playerName == null || playerName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Tên người chơi là bắt buộc!");
                return;
            }
            
            UNOClient client = new UNOClient();
            client.setVisible(true);
            client.connectToServer(host, port, playerName.trim());
        });
    }
}

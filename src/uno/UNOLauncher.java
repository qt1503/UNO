package uno;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Launcher cho UNO Multiplayer Game
 */
public class UNOLauncher extends JFrame {
    
    public UNOLauncher() {
        initializeGUI();
    }
    
    private void initializeGUI() {
        setTitle("Game UNO");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 600);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new java.awt.Color(255, 204, 204));
        // Tạo cửa sổ bo tròn cross-platform
        try {
            // Dành cho macOS
            getRootPane().putClientProperty("apple.awt.windowRounding", 25.0);
            
            // Dành cho Windows và Linux - sử dụng setShape sau khi window hiển thị
            SwingUtilities.invokeLater(() -> {
                if (!System.getProperty("os.name").toLowerCase().contains("mac")) {
                    setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 25, 25));
                }
            });
        } catch (Exception e) {
            System.out.println("Rounded corners not supported on this platform");
        }
        
        // Title
        JLabel titleLabel = new JLabel("GAME UNO", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 30, 0));
        
        // Create center panel with rounded card
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new GridBagLayout());
        
        // Create card panel with rounded corners
        JPanel cardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw rounded rectangle with semi-transparent white background
                g2d.setColor(new Color(255, 255, 255, 100));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                
                // Draw border
                g2d.setColor(new Color(255, 255, 255, 100));
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, 20, 20);
            }
        };
        cardPanel.setOpaque(false);
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        // Subtitle
        JLabel subtitleLabel = new JLabel("Chọn chế độ chơi", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.BOLD, 25));
        subtitleLabel.setForeground(new Color(70, 70, 70));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        
        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        
        JButton startServerButton = createStyledButton("BẮT ĐẦU TRÒ CHƠI", new Color(255, 204, 204));
        startServerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startServer();
            }
        });
        
        JButton joinGameButton = createStyledButton("THAM GIA TRÒ CHƠI", new Color(255, 204, 204));
        joinGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                joinGame();
            }
        });
        
        JButton exitButton = createStyledButton("THOÁT", new Color(255, 204, 204));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        // Add spacing between buttons
        buttonPanel.add(startServerButton);
        buttonPanel.add(Box.createVerticalStrut(15));
        buttonPanel.add(joinGameButton);
        buttonPanel.add(Box.createVerticalStrut(15));
        buttonPanel.add(exitButton);
        
        // Add components to card
        cardPanel.add(subtitleLabel);
        cardPanel.add(buttonPanel);
        
        // Add card to center panel
        centerPanel.add(cardPanel);
        
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        add(mainPanel);
        
        // Add component listener để cập nhật rounded corners trên Windows/Linux
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                if (!System.getProperty("os.name").toLowerCase().contains("mac")) {
                    SwingUtilities.invokeLater(() -> {
                        setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 25, 25));
                    });
                }
            }
        });
        
        // Add window listener to properly close application
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    // Tạo custom input dialog với nền hồng
    private String showCustomInputDialog(JFrame parent, String message, String title) {
        JDialog dialog = new JDialog(parent, title, true);
        dialog.setSize(320, 180);
        dialog.setLocationRelativeTo(parent);
        
        // Main panel với nền hồng
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(255, 204, 204));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Top panel với icon và message
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        
        // Icon label
        JLabel iconLabel = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/Img/icon.png"));
            // Scale icon xuống size nhỏ hơn cho phù hợp với dialog nhỏ
            Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            iconLabel.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            // Nếu không load được icon, để trống
        }
        
        // Message label
        JLabel messageLabel = new JLabel(message, SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 14));
        messageLabel.setForeground(Color.BLACK);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        topPanel.add(iconLabel, BorderLayout.WEST);
        topPanel.add(messageLabel, BorderLayout.CENTER);
        
        // Input field với nền trắng trong panel riêng
        JTextField inputField = new JTextField(12);
        inputField.setFont(new Font("Arial", Font.PLAIN, 13));
        inputField.setHorizontalAlignment(JTextField.CENTER);
        inputField.setPreferredSize(new Dimension(180, 20));
        inputField.setMaximumSize(new Dimension(180, 20));
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        
        final String[] result = new String[1];
        
        JButton okButton = createStyledButton("OK", new Color(255, 204, 204, 100));
        okButton.setPreferredSize(new Dimension(70, 30));
        okButton.setMaximumSize(new Dimension(70, 30));
        okButton.addActionListener(e -> {
            result[0] = inputField.getText();
            dialog.dispose();
        });
        
        JButton cancelButton = createStyledButton("Hủy", new Color(255, 204, 204, 100));
        cancelButton.setPreferredSize(new Dimension(70, 30));
        cancelButton.setMaximumSize(new Dimension(70, 30));
        cancelButton.addActionListener(e -> {
            result[0] = null;
            dialog.dispose();
        });
        
        buttonPanel.add(okButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(cancelButton);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(inputField, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.add(mainPanel);
        dialog.setVisible(true);
        
        return result[0];
    }
    
    // Tạo custom message dialog với nền hồng
    private void showCustomMessageDialog(JFrame parent, String message, String title) {
        JDialog dialog = new JDialog(parent, title, true);
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(parent);
        
        // Main panel với nền hồng
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(255, 204, 204));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Center panel với icon và message
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        
        // Icon label
        JLabel iconLabel = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/Img/icon.png"));
            // Scale icon xuống size nhỏ hơn cho phù hợp
            Image img = icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            iconLabel.setIcon(new ImageIcon(img));
            iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));
        } catch (Exception e) {
            // Nếu không load được icon, để trống
        }
        
        // Message label
        JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>" + message + "</div></html>", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 14));
        messageLabel.setForeground(Color.WHITE);
        
        centerPanel.add(iconLabel, BorderLayout.WEST);
        centerPanel.add(messageLabel, BorderLayout.CENTER);
        
        // OK button
        JButton okButton = createStyledButton("OK", new Color(255, 204, 204));
        okButton.setPreferredSize(new Dimension(80, 35));
        okButton.addActionListener(e -> dialog.dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.add(okButton);
        
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.add(mainPanel);
        dialog.setVisible(true);
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
    
    private void startServer() {
        try {
            String input = showCustomInputDialog(this, "Nhập số lượng người chơi (2-10):", "Bắt đầu Trò Chơi");
            if (input == null) return;
            
            int numPlayers = Integer.parseInt(input);
            
            if (numPlayers < 2 || numPlayers > 10) {
                showCustomMessageDialog(this, "Số lượng người chơi không hợp lệ. Phải từ 2 đến 10 người.", "Lỗi");
                return;
            }
            
            // Collect player names first
            String[] playerNames = new String[numPlayers];
            for (int i = 0; i < numPlayers; i++) {
                String playerName = showCustomInputDialog(this, 
                    "Nhập tên cho Người chơi " + (i + 1) + ":", 
                    "Tên Người Chơi");
                
                if (playerName == null || playerName.trim().isEmpty()) {
                    playerName = "Người chơi " + (i + 1); // Default name if cancelled or empty
                }
                playerNames[i] = playerName.trim();
            }
            
            // Hide launcher window after collecting all player names
            this.setVisible(false);
            
            // Start everything in background thread to avoid blocking EDT
            new Thread(() -> {
                try {
                    System.out.println("Starting server for " + numPlayers + " players: " + java.util.Arrays.toString(playerNames));
                    UNOServer server = new UNOServer(12345, playerNames);
                    
                    // Start server in background thread
                    new Thread(() -> server.startServer()).start();
                    
                    // Wait for server to be ready
                    Thread.sleep(2000);
                    
                    System.out.println("Creating " + numPlayers + " client windows...");
                    
                    // Create client windows for each player
                    for (int i = 1; i <= numPlayers; i++) {
                        final int playerNum = i;
                        final String playerName = playerNames[i - 1];
                        
                        System.out.println("Creating client window " + playerNum + " for " + playerName);
                        
                        // Create client window for each player on EDT
                        SwingUtilities.invokeLater(() -> {
                            UNOClient client = new UNOClient();
                            client.setTitle("UNO - " + playerName);
                            
                            // Position windows side by side
                            int windowWidth = 600;
                            int windowHeight = 700;
                            int x = (playerNum - 1) * (windowWidth + 10);
                            int y = 50;
                            
                            client.setSize(windowWidth, windowHeight);
                            client.setLocation(x, y);
                            client.setVisible(true);
                            
                            System.out.println("Window for " + playerName + " created at position (" + x + ", " + y + ")");
                            
                            // Auto-connect to server with slight delay
                            Timer timer = new Timer(1000 + (500 * playerNum), new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    System.out.println("Connecting " + playerName + " to server...");
                                    client.connectToServer("localhost", 12345, playerName);
                                }
                            });
                            timer.setRepeats(false);
                            timer.start();
                        });
                        
                        Thread.sleep(800);
                    }
                    System.out.println("Server started successfully with " + numPlayers + " players");
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    SwingUtilities.invokeLater(() -> {
                        this.setVisible(true);
                        showCustomMessageDialog(this, "Không thể khởi tạo server: " + e.getMessage(), "Lỗi");
                    });
                }
            }).start();
            
        } catch (NumberFormatException e) {
            showCustomMessageDialog(this, "Dữ liệu không hợp lệ. Vui lòng nhập một số.", "Lỗi");
        }
    }
    
    private void joinGame() {
        String hostInput = showCustomInputDialog(this, "Nhập địa chỉ server:", "Tham Gia Trò Chơi");
        final String host = (hostInput == null || hostInput.trim().isEmpty()) ? "localhost" : hostInput;
        
        String portStr = showCustomInputDialog(this, "Nhập cổng server:", "Cổng Server");
        if (portStr == null) portStr = "12345";
        int portTemp = 12345;
        try {
            if (!portStr.trim().isEmpty()) {
                portTemp = Integer.parseInt(portStr);
            }
        } catch (NumberFormatException e) {
            portTemp = 12345;
        }
        final int port = portTemp;
        
        String playerName = showCustomInputDialog(this, "Nhập tên người chơi của bạn:", "Tham Gia Trò Chơi");
        if (playerName == null || playerName.trim().isEmpty()) {
            showCustomMessageDialog(this, "Tên người chơi là bắt buộc!", "Lỗi");
            return;
        }
        
        final String finalPlayerName = playerName.trim();
        
        // Start client
        SwingUtilities.invokeLater(() -> {
            UNOClient client = new UNOClient();
            client.setVisible(true);
            client.connectToServer(host, port, finalPlayerName);
        });
    }
    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            new UNOLauncher().setVisible(true);
        });
    }
}

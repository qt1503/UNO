package uno;

import java.awt.*;

import javax.swing.*;


/**
 * PickColorFrame cho UNOClient - chọn màu cho Wild cards
 */
public class PickColorFrameForClient extends javax.swing.JFrame {

    private UnoCard.Color selectedColor = null;
    private UNOClient client;
    private int cardIndex;
    
    public PickColorFrameForClient(UNOClient client, int cardIndex) {
        this.client = client;
        this.cardIndex = cardIndex;
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Chọn màu cho Wild Card");
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        RedBtn = new javax.swing.JButton();
        BlueBtn = new javax.swing.JButton();
        GreenBtn = new javax.swing.JButton();
        YellowBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new Color(255, 255, 255));

        jLabel1.setFont(new Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Chọn màu bạn muốn chuyển");
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setVerticalAlignment(SwingConstants.CENTER);

        RedBtn.setBackground(new Color(243, 129, 129));
        RedBtn.setOpaque(true);
        RedBtn.setBorderPainted(false);
        RedBtn.setFocusPainted(false);
        RedBtn.setFont(new Font("Segoe UI", 1, 18)); // NOI18N
        RedBtn.setText("MÀU ĐỎ");
        RedBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RedBtnActionPerformed(evt);
            }
        });

        BlueBtn.setBackground(new Color(149, 225, 211));
        BlueBtn.setOpaque(true);
        BlueBtn.setBorderPainted(false);
        BlueBtn.setFocusPainted(false);
        BlueBtn.setFont(new Font("Segoe UI", 1, 18)); // NOI18N
        BlueBtn.setText("MÀU XANH DƯƠNG");
        BlueBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BlueBtnActionPerformed(evt);
            }
        });

        GreenBtn.setBackground(new Color(214, 247, 173));
        GreenBtn.setOpaque(true);
        GreenBtn.setBorderPainted(false);
        GreenBtn.setFocusPainted(false);
        GreenBtn.setFont(new Font("Segoe UI", 1, 18)); // NOI18N
        GreenBtn.setText("MÀU XANH LÁ");
        GreenBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GreenBtnActionPerformed(evt);
            }
        });

        YellowBtn.setBackground(new Color(252, 227, 138));
        YellowBtn.setOpaque(true);
        YellowBtn.setBorderPainted(false);
        YellowBtn.setFocusPainted(false);
        YellowBtn.setFont(new Font("Segoe UI", 1, 18)); // NOI18N
        YellowBtn.setText("MÀU VÀNG");
        YellowBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                YellowBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(RedBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(YellowBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(BlueBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(GreenBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(29, 29, 29))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(jLabel1)
                .addContainerGap(104, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BlueBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RedBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(YellowBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(GreenBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>                        

    private void RedBtnActionPerformed(java.awt.event.ActionEvent evt) {                                       
        selectedColor = UnoCard.Color.Red;
        // JOptionPane.showMessageDialog(null, message);
        showCustomMessageDialog(this, "Màu được đổi là màu đỏ", "Màu đã chọn");
        
        // Gọi phương thức của client để chơi Wild card với màu đã chọn
        client.playWildCard(cardIndex, selectedColor);
        this.dispose();
    }                                      

    private void BlueBtnActionPerformed(java.awt.event.ActionEvent evt) {                                        
        selectedColor = UnoCard.Color.Blue;
        showCustomMessageDialog(this, "Màu được đổi là màu xanh dương", "Màu đã chọn");
        
        // Gọi phương thức của client để chơi Wild card với màu đã chọn
        client.playWildCard(cardIndex, selectedColor);
        this.dispose();
    }                                       

    private void GreenBtnActionPerformed(java.awt.event.ActionEvent evt) {                                         
        selectedColor = UnoCard.Color.Green;
        showCustomMessageDialog(this, "Màu được đổi là màu xanh lá", "Màu đã chọn");
        
        // Gọi phương thức của client để chơi Wild card với màu đã chọn
        client.playWildCard(cardIndex, selectedColor);
        this.dispose();
    }                                        

    private void YellowBtnActionPerformed(java.awt.event.ActionEvent evt) {                                          
        selectedColor = UnoCard.Color.Yellow;
        showCustomMessageDialog(this, "Màu được đổi là màu vàng", "Màu đã chọn");
        
        // Gọi phương thức của client để chơi Wild card với màu đã chọn
        client.playWildCard(cardIndex, selectedColor);
        this.dispose();
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
        messageLabel.setForeground(Color.BLACK);
        
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

    // Variables declaration - do not modify                     
    private javax.swing.JButton BlueBtn;
    private javax.swing.JButton GreenBtn;
    private javax.swing.JButton RedBtn;
    private javax.swing.JButton YellowBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration                   
}

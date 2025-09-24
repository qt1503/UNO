/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package uno;

import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author ASUS
 */
public class AddPlayer extends javax.swing.JFrame {

    public ArrayList<String> playerId;
    
    /**
     * Creates new form AddPlayer
     */
    public AddPlayer() {
        initComponents();
        playerId = new ArrayList<>();
    }
    
    public String [] getPids(){
        String [] pids = playerId.toArray(new String[playerId.size()]);
        return pids;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnDone = new javax.swing.JButton();
        txtAdd = new javax.swing.JTextField();
        Player1 = new javax.swing.JLabel();
        Player2 = new javax.swing.JLabel();
        Player3 = new javax.swing.JLabel();
        Player4 = new javax.swing.JLabel();
        Player5 = new javax.swing.JLabel();
        Player6 = new javax.swing.JLabel();
        Player7 = new javax.swing.JLabel();
        Player8 = new javax.swing.JLabel();
        Player9 = new javax.swing.JLabel();
        Player10 = new javax.swing.JLabel();
        namePlayer = new javax.swing.JLabel();
        txtAddPlay = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 204, 204));

        jPanel1.setBackground(new java.awt.Color(255, 204, 204));

        btnSave.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnSave.setText("Lưu");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnDone.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnDone.setText("Xong");
        btnDone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoneActionPerformed(evt);
            }
        });

        txtAdd.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAddActionPerformed(evt);
            }
        });

        Player1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        Player2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        Player3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        Player4.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        Player5.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        Player6.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        Player7.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        Player8.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        Player9.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        Player10.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        namePlayer.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        namePlayer.setText("Tên người chơi :");

        txtAddPlay.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        txtAddPlay.setText("Mời nhập tên người chơi");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(151, 151, 151)
                        .addComponent(txtAddPlay))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(namePlayer)
                                .addGap(18, 18, 18)
                                .addComponent(txtAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(Player3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Player2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Player1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Player4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Player5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(89, 89, 89)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(Player6, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(Player8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(Player7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(Player9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(Player10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addGap(95, 96, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addComponent(btnSave)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDone)
                .addGap(119, 119, 119))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(txtAddPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(namePlayer, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAdd))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Player1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Player6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Player2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Player3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Player4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Player5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Player7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Player8, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Player9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Player10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDone, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if(txtAdd.getText().isEmpty()){
            JLabel message = new JLabel("Mời nhập tên người chơi!!!");
            message.setFont(new Font("Times New Roman",Font.BOLD,20));
            JOptionPane.showMessageDialog(null, message);
        }
        else{
            String name = txtAdd.getText().trim();
            playerId.add(name);
            
            if(playerId.size()==1){
                Player1.setText(playerId.get(0));
            }
            else if(playerId.size()==2){
                Player1.setText(playerId.get(0));
                Player2.setText(playerId.get(1));
            }
            else if(playerId.size()==3){
                Player1.setText(playerId.get(0));
                Player2.setText(playerId.get(1));
                Player3.setText(playerId.get(2));
            }
            else if(playerId.size()==4){
                Player1.setText(playerId.get(0));
                Player2.setText(playerId.get(1));
                Player3.setText(playerId.get(2));
                Player4.setText(playerId.get(3));
            }
            else if(playerId.size()==5){
                Player1.setText(playerId.get(0));
                Player2.setText(playerId.get(1));
                Player3.setText(playerId.get(2));
                Player4.setText(playerId.get(3));
                Player5.setText(playerId.get(4));
            }
            else if(playerId.size()==6){
                Player1.setText(playerId.get(0));
                Player2.setText(playerId.get(1));
                Player3.setText(playerId.get(2));
                Player4.setText(playerId.get(3));
                Player5.setText(playerId.get(4));
                Player6.setText(playerId.get(5));
            }
            else if(playerId.size()==7){
                Player1.setText(playerId.get(0));
                Player2.setText(playerId.get(1));
                Player3.setText(playerId.get(2));
                Player4.setText(playerId.get(3));
                Player5.setText(playerId.get(4));
                Player6.setText(playerId.get(5));
                Player7.setText(playerId.get(6));
            }
            else if(playerId.size()==8){
                Player1.setText(playerId.get(0));
                Player2.setText(playerId.get(1));
                Player3.setText(playerId.get(2));
                Player4.setText(playerId.get(3));
                Player5.setText(playerId.get(4));
                Player6.setText(playerId.get(5));
                Player7.setText(playerId.get(6));
                Player8.setText(playerId.get(7));
            }
            else if(playerId.size()==9){
                Player1.setText(playerId.get(0));
                Player2.setText(playerId.get(1));
                Player3.setText(playerId.get(2));
                Player4.setText(playerId.get(3));
                Player5.setText(playerId.get(4));
                Player6.setText(playerId.get(5));
                Player7.setText(playerId.get(6));
                Player8.setText(playerId.get(7));
                Player9.setText(playerId.get(8));
            }
            else if(playerId.size()==10){
                Player1.setText(playerId.get(0));
                Player2.setText(playerId.get(1));
                Player3.setText(playerId.get(2));
                Player4.setText(playerId.get(3));
                Player5.setText(playerId.get(4));
                Player6.setText(playerId.get(5));
                Player7.setText(playerId.get(6));
                Player8.setText(playerId.get(7));
                Player9.setText(playerId.get(8));
                Player10.setText(playerId.get(9));
            }
            if(playerId.size() > 0 && playerId.size() < 11){
                JLabel message = new JLabel("Lưu thành công");
                message.setFont(new Font("Times New Roman",Font.BOLD,20));
                JOptionPane.showMessageDialog(null, message);
                txtAdd.setText("");
            }
            if(playerId.size()== 11){
                playerId.remove(name);
                JLabel message = new JLabel("Tối đa có 10 người chơi!");
                message.setFont(new Font("Times New Roman",Font.BOLD,20));
                JOptionPane.showMessageDialog(null, message);
                txtAdd.setText("");
            }
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void txtAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAddActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAddActionPerformed

    private void btnDoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoneActionPerformed
        if(playerId.size() <= 1 || playerId.size() > 11 || playerId.isEmpty()){
                JLabel message = new JLabel("Tối thiểu 2 người và tối đa 10 người");
                message.setFont(new Font("Times New Roman",Font.BOLD,20));
                JOptionPane.showMessageDialog(null, message);
                txtAdd.setText("");
            }
        else{
            this.dispose();
            //Lỗiười
            new GameStage(playerId).setVisible(true);
        }
    }//GEN-LAST:event_btnDoneActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AddPlayer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddPlayer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddPlayer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddPlayer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddPlayer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Player1;
    private javax.swing.JLabel Player10;
    private javax.swing.JLabel Player2;
    private javax.swing.JLabel Player3;
    private javax.swing.JLabel Player4;
    private javax.swing.JLabel Player5;
    private javax.swing.JLabel Player6;
    private javax.swing.JLabel Player7;
    private javax.swing.JLabel Player8;
    private javax.swing.JLabel Player9;
    private javax.swing.JButton btnDone;
    private javax.swing.JButton btnSave;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel namePlayer;
    private javax.swing.JTextField txtAdd;
    private javax.swing.JLabel txtAddPlay;
    // End of variables declaration//GEN-END:variables
}

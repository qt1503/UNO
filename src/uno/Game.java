/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uno;

import java.awt.Font;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author ASUS
 */
public class Game {
    private String[] playerId;
    private int curPlayer;
    
    private UnoDeck deck;
    private ArrayList<ArrayList<UnoCard>> playerHand;
    private ArrayList<UnoCard> stockPile;
    
    private UnoCard.Color validColor;
    private UnoCard.Value validValue;
    
    boolean gameDirection;
    
    // Map theo dõi trạng thái UNO của từng người chơi
    private java.util.Map<String, Boolean> playerUnoStatus;
    
    public Game(String [] pids){
        deck=new UnoDeck();
        deck.shuffle();
        stockPile = new ArrayList<UnoCard>();
        
        playerId = pids;
        curPlayer = 0;
        gameDirection = false;
        
        playerHand = new ArrayList<ArrayList<UnoCard>>();
        playerUnoStatus = new java.util.HashMap<>();
        
        for(int i = 0; i < pids.length; i++){
            ArrayList<UnoCard> hand = new ArrayList<UnoCard>(Arrays.asList(deck.drawCard(6)));
            playerHand.add(hand);
            playerUnoStatus.put(pids[i], false); // Khởi tạo trạng thái UNO
        }
    }

    
    public void start(Game game){
        UnoCard card = deck.drawCard();
        validColor = card.getColor();
        validValue = card.getValue();
        
        // Nếu lá đầu tiên là Wild, đặt màu là Wild để cho phép người chơi đầu tiên chơi bất kỳ lá nào
        if(card.getValue()==UnoCard.Value.Wild || card.getValue()==UnoCard.Value.Wild_Four){
            validColor = UnoCard.Color.Wild;
            validValue = card.getValue();
            stockPile.add(card);
            System.out.println("WILD CARD START: Top card is Wild, players can play any card!");
            return; // Không cần rút lá khác, người chơi đầu tiên có thể chọn bất kỳ lá nào
        }
        // Xử lý các lá bài đặc biệt (trừ Wild đã xử lý ở trên)
        if(card.getValue() == UnoCard.Value.DrawTwo){
            JLabel message = new JLabel(playerId[curPlayer]+" được qua lượt");
            message.setFont(new Font("Times New Roman", Font.BOLD, 20));
            JOptionPane.showMessageDialog(null,message);
            
            if(gameDirection==false){
                curPlayer = (curPlayer + 1) % playerId.length;
            }
            else if(gameDirection==true){
                curPlayer = (curPlayer - 1) % playerId.length;
                if(curPlayer==-1){
                    curPlayer =playerId.length - 1;
                }
            }
        }
        if(card.getValue()==UnoCard.Value.Reverse){
            JLabel message = new JLabel(playerId[curPlayer]+" Đổi hướng trò chơi");
            message.setFont(new Font("Times New Roman", Font.BOLD, 20));
            JOptionPane.showMessageDialog(null,message);
            gameDirection ^=true;
            curPlayer = playerId.length - 1;
        }
        
        // Chỉ thêm vào stockPile nếu chưa được thêm (Wild cards đã được thêm ở trên)
        if(card.getValue() != UnoCard.Value.Wild && card.getValue() != UnoCard.Value.Wild_Four) {
            stockPile.add(card);
        }
    }
    
    public UnoCard getTopCard(){
        return new UnoCard(validColor, validValue);
    }
    
    public String getTopCardDisplay() {
        // Trả về string hiển thị cho top card với màu hiệu lực
        return validColor + "-" + validValue;
    }
    
    public ImageIcon getTopCardImage(){

        return new ImageIcon(validColor+"-"+ validValue+".png");
    }
    public boolean isGameOver(){
        for(String player : this.playerId){
            if(hasEmptyHand(player))
                return true;         
        }
        return false;
    }
    
    public String getCurrentPlayer(){
        return this.playerId[this.curPlayer];
    }
    public String getPreviousPlayer(int i){
        int index = this.curPlayer - i;
        if(index == -1){
            index = playerId.length - 1;
        }
        return this.playerId[index];
    }
    
    public String[] getPlayer(){
        return playerId;
    }
    
    public ArrayList<UnoCard> getPlayerHand(String pid){
        int index = Arrays.asList(playerId).indexOf(pid);
        return playerHand.get(index);
    }
    
    public int getPlayerHandSize(String pid){
        return getPlayerHand(pid).size();
    }
    
    public UnoCard getPlayerCard(String pid, int choice){
        ArrayList<UnoCard> hand = getPlayerHand(pid);
        return hand.get(choice);
    }
    private boolean hasEmptyHand(String pid) {
       return getPlayerHand(pid).isEmpty();
    }
    public boolean validCardPLay(UnoCard card){
        // Nếu top card hiện tại có màu Wild, cho phép chơi bất kỳ lá bài nào
        if (validColor == UnoCard.Color.Wild) {
            return true;
        }
        // Kiểm tra bình thường: cùng màu hoặc cùng giá trị
        return card.getColor()==validColor||card.getValue()==validValue;
    }
    public void checkPlayerTurn(String pid) throws InvalidPlayerTurnException{
        if(!this.playerId[this.curPlayer].equals(pid)){
            throw new InvalidPlayerTurnException("Không phải lượt của "+pid,pid);
        }
    }
    
    public void submitDraw(String pid) throws InvalidPlayerTurnException{
        checkPlayerTurn(pid);
        if(deck.isEmpty()){
            deck.replaceDeckWith(stockPile);
            deck.shuffle();
        }
        getPlayerHand(pid).add(deck.drawCard());
        
        // Reset trạng thái UNO khi rút bài (có thêm bài rồi)
        playerUnoStatus.put(pid, false);
        
        if(gameDirection == false){
            curPlayer = (curPlayer + 1) % playerId.length;
        }
        else{
            curPlayer = (curPlayer - 1) % playerId.length;
            if(curPlayer==-1){
                curPlayer = playerId.length -1;
            }
        }

    }
    public void setCardColor(UnoCard.Color color){
        validColor = color;
    }
    
    public void submitPlayerCard(String pid, UnoCard card, UnoCard.Color declaredColor)
        throws InvalidColorSubmissionException, InvalidValueSubmissionException, InvalidPlayerTurnException, IOException{
            checkPlayerTurn(pid);
            ArrayList<UnoCard> pHand = getPlayerHand(pid);
            
            if(!validCardPLay(card)){
                if(card.getColor() == UnoCard.Color.Wild){
                    validColor=card.getColor();
                    validValue=card.getValue();
                }
                
                if(card.getColor()!=validColor){
                    JLabel message = new JLabel("Không thể, màu bạn chọn: "+validColor+" nhưng thực tế là : "+card.getColor());
                    message.setFont(new Font("Times New Roman", Font.BOLD,20));
                    JOptionPane.showMessageDialog(null,message);
                    throw new InvalidColorSubmissionException("Không thể, màu chọn: "+validColor+" nhưng thực tế là :" +card.getColor(),card.getColor(),validColor);
                }
                else if(card.getValue() != validValue){
                    JLabel message2 = new JLabel("Không thể, giá trị chọn: "+validValue+" nhưng thực tế là : "+card.getValue());
                    message2.setFont(new Font("Times New Roman", Font.BOLD,20));
                    JOptionPane.showMessageDialog(null,message2);
                    throw new InvalidValueSubmissionException("Không thể, giá trị chọn: "+validValue+" nhưng thực tế là : "+card.getValue(),card.getValue(),validValue);
                }
            }
            pHand.remove(card);
            
            // Reset trạng thái UNO khi chơi bài (trừ khi còn đúng 1 lá)
            if (pHand.size() != 1) {
                playerUnoStatus.put(pid, false);
            }
            
            if(hasEmptyHand(this.playerId[curPlayer])){
                JLabel message2 = new JLabel(this.playerId[curPlayer]+ " đã chiến thắng!");
                    message2.setFont(new Font("Times New Roman", Font.BOLD,20));
                    JOptionPane.showMessageDialog(null,message2);
                    try{
                        FileWriter writer = new FileWriter("winners.txt", true);
                        BufferedWriter bw = new BufferedWriter(writer);
                        bw.write(LocalDate.now(ZoneId.of("Asia/Ho_Chi_Minh"))+" ");
                        bw.write(java.time.LocalTime.now()+": ");
                        bw.write(this.playerId[curPlayer] +"\n");
                        bw.close();
                        writer.close();
                    }
                    catch(IOException e){
                            e.printStackTrace();
                    }                
                    System.exit(0);
            }
            
            validColor = card.getColor();
            validValue = card.getValue();
            stockPile.add(card);
            
            if(gameDirection){
                curPlayer=(curPlayer - 1) % playerId.length;
                if(curPlayer==-1){
                    curPlayer=playerId.length-1;
                }
            }
            else
                curPlayer=(curPlayer + 1) % playerId.length;
            
            if(card.getColor()==UnoCard.Color.Wild){
                validColor=declaredColor;
            }
            if(card.getValue()==UnoCard.Value.DrawTwo){
                pid=playerId[curPlayer];
                getPlayerHand(pid).add(deck.drawCard());
                getPlayerHand(pid).add(deck.drawCard());
                JLabel message = new JLabel(pid + " bốc 2 lá");
            }
            
            if(card.getValue()==UnoCard.Value.Wild_Four){
                pid=playerId[curPlayer];
                getPlayerHand(pid).add(deck.drawCard());
                getPlayerHand(pid).add(deck.drawCard());
                getPlayerHand(pid).add(deck.drawCard());
                getPlayerHand(pid).add(deck.drawCard());
                JLabel message = new JLabel(pid + " bốc 4 lá");
            }
            
            if(card.getValue()==UnoCard.Value.Skip){
                JLabel message = new JLabel(this.playerId[curPlayer]+ " qua lượt");
                    message.setFont(new Font("Times New Roman", Font.BOLD,20));
                    JOptionPane.showMessageDialog(null,message);
                    if(gameDirection){
                        curPlayer=(curPlayer - 1) % playerId.length;
                        if(curPlayer==-1){
                            curPlayer = playerId.length - 1;
                        }
                    }
                    else
                         curPlayer=(curPlayer + 1) % playerId.length;
            }
            
            if(card.getValue()==UnoCard.Value.Reverse){
                JLabel message = new JLabel(pid+ " trò chơi đã được đổi chiều");
                    message.setFont(new Font("Times New Roman", Font.BOLD,20));
                    JOptionPane.showMessageDialog(null,message);
                    
                    gameDirection ^= true;
                    if(gameDirection){
                        curPlayer=(curPlayer - 2) % playerId.length;
                        if(curPlayer==-1){
                            curPlayer = playerId.length - 1;
                        }
                        if(curPlayer==-2){
                            curPlayer = playerId.length - 2;
                        }
                    }
                    else
                         curPlayer=(curPlayer + 2) % playerId.length;
            }
                
    }

    class InvalidPlayerTurnException extends Exception {
        String playerId;
        public InvalidPlayerTurnException(String message, String pid) {
            super(message);
            playerId = pid;
        }
        
        public String getPid(){
            return playerId;
        }
    }
    class InvalidColorSubmissionException extends Exception{
        private UnoCard.Color expected;
        private UnoCard.Color actual;

        public InvalidColorSubmissionException(String message, UnoCard.Color actual, UnoCard.Color expected) {
            this.actual=actual;
            this.expected=expected;
        }
        
        
    }
    class InvalidValueSubmissionException extends Exception{
        private UnoCard.Value expected;
        private UnoCard.Value actual;

        public InvalidValueSubmissionException(String message, UnoCard.Value actual, UnoCard.Value expected) {
            this.actual=actual;
            this.expected=expected;
        }
        
        
    }
    
    public String getWinner() {
        for(String player : this.playerId) {
            if(hasEmptyHand(player)) {
                return player;
            }
        }
        return null;
    }
    
    // Methods for UNO catching feature
    public void setPlayerUnoStatus(String playerName, boolean hasCalledUno) {
        playerUnoStatus.put(playerName, hasCalledUno);
    }
    
    public String findPlayerWithoutUno() {
        for (String player : playerId) {
            int handSize = getPlayerHandSize(player);
            Boolean hasCalledUno = playerUnoStatus.get(player);
            
            System.out.println("DEBUG findPlayerWithoutUno:");
            System.out.println("  Player: " + player);
            System.out.println("  Hand size: " + handSize);
            System.out.println("  Has called UNO: " + hasCalledUno);
            
            // Nếu người chơi có 1 lá bài và chưa gọi UNO (hoặc null = chưa gọi)
            if (handSize == 1 && (hasCalledUno == null || !hasCalledUno)) {
                System.out.println("  -> Found player without UNO: " + player);
                return player;
            }
        }
        System.out.println("  -> No player found without UNO");
        return null;
    }
    
    public void penalizePlayer(String playerName, int cardsToDraw) {
        ArrayList<UnoCard> hand = getPlayerHand(playerName);
        int handSizeBefore = hand.size();
        
        System.out.println("PENALTY: " + playerName + " bị phạt " + cardsToDraw + " lá bài");
        System.out.println("  Số lá trước khi phạt: " + handSizeBefore);
        
        for (int i = 0; i < cardsToDraw; i++) {
            UnoCard card = deck.drawCard();
            if (card != null) {
                hand.add(card);
                System.out.println("  Rút lá: " + card);
            }
        }
        
        int handSizeAfter = hand.size();
        System.out.println("  Số lá sau khi phạt: " + handSizeAfter);
        System.out.println("  Đã phạt thành công: " + (handSizeAfter - handSizeBefore) + " lá bài");
        
        // Reset trạng thái UNO sau khi phạt
        playerUnoStatus.put(playerName, false);
        System.out.println("  Reset UNO status cho " + playerName);
    }
}

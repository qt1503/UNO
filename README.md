# UNO Multiplayer Game

## Tổng quan

Đây là phiên bản mở rộng của game UNO với hỗ trợ multiplayer sử dụng socket programming. Mỗi người chơi sẽ có màn hình riêng và có thể tham gia game từ các máy tính khác nhau.

## Tính năng mới

✅ **Multiplayer Support**: Hỗ trợ 2-10 người chơi  
✅ **Socket Programming**: Sử dụng TCP/IP để kết nối  
✅ **Màn hình riêng biệt**: Mỗi người chơi có giao diện riêng  
✅ **Real-time Updates**: Cập nhật trạng thái game theo thời gian thực  
✅ **Cross-platform**: Chạy trên mọi hệ điều hành có Java  
🆕 **Catch UNO System**: Bắt người chơi quên gọi UNO với hệ thống timer 3 giây  
🆕 **Penalty System**: Tự động phạt 2 lá bài khi bị bắt UNO  
🆕 **Enhanced Logging**: Hệ thống debug logging chi tiết  

## Cách chạy

### 1. Khởi động Launcher
```bash
ant run
```

Launcher sẽ hiển thị 4 tùy chọn:
- **Start Server**: Tạo server game
- **Join Game (Client)**: Tham gia game có sẵn
- **Single Player (Original)**: Chơi đơn như phiên bản gốc
- **Exit**: Thoát

### 2. Tạo Server (Host game)

1. Chọn "Start Server"
2. Nhập số lượng người chơi (2-10)
3. Server sẽ khởi động trên port 12345
4. Chờ người chơi kết nối

### 3. Tham gia game (Client)

1. Chọn "Join Game (Client)"
2. Nhập thông tin:
   - **Server host**: IP của máy host (mặc định: localhost)
   - **Server port**: Port của server (mặc định: 12345)
   - **Player name**: Tên người chơi của bạn
3. Màn hình client sẽ xuất hiện
4. Chờ đủ người chơi để bắt đầu game

## Kiến trúc hệ thống

### Server (UNOServer.java)
- Quản lý kết nối của nhiều client
- Điều khiển logic game
- Gửi trạng thái game đến tất cả client
- Xử lý nước đi của người chơi

### Client (UNOClient.java)  
- Giao diện người chơi riêng biệt
- Kết nối đến server qua socket
- Nhận và hiển thị trạng thái game
- Gửi nước đi của người chơi đến server

### ClientHandler (ClientHandler.java)
- Xử lý kết nối của từng client
- Chạy trong thread riêng cho mỗi người chơi
- Quản lý communication giữa client và server

### Launcher (UNOLauncher.java)
- Giao diện chọn chế độ chơi
- Khởi động server hoặc client
- Hỗ trợ cả chế độ mới và cũ

## Protocol Communication

### Message Format:
- `PLAYER_NAME:tên_người_chơi` - Đăng ký tên
- `GAME_START:player1,player2,...` - Thông báo game bắt đầu  
- `GAME_STATE:thông_tin_game` - Cập nhật trạng thái
- `MOVE:PLAY_CARD:index` - Đánh bài
- `MOVE:DRAW_CARD` - Rút bài
- `MOVE:CALL_UNO` - Gọi UNO
- `CATCH_UNO` - 🆕 Bắt người chơi chưa gọi UNO
- `GAME_OVER:winner` - Thông báo kết thúc

### Game State Format:
```
CURRENT_PLAYER:tên;TOP_CARD:màu-giá_trị;HAND:bài1,bài2,...;OTHER_HANDS:player:số_bài,...;UNO_CATCHABLE:true/false
```

### 🆕 UNO Catching System:
- **UNO_CATCHABLE**: Cho biết có người nào có thể bị bắt UNO không
- **Timer System**: Nút "Bắt UNO" xuất hiện sau 3 giây delay
- **Auto-hide**: Nút tự động ẩn khi không còn ai để bắt
- **Penalty Logging**: Chi tiết log hình phạt trong console

## Hướng dẫn chơi Multiplayer

### Cách chơi cơ bản:
1. **Tạo game**: Một người chọn "Start Server" và đặt số lượng người chơi
2. **Tham gia**: Các người chơi khác chọn "Join Game" và nhập IP của host
3. **Đợi**: Chờ đủ số lượng người chơi đã đặt
4. **Chơi**: Game tự động bắt đầu khi đủ người
5. **Luân phiên**: Chỉ người chơi có lượt mới được đánh bài
6. **Thắng**: Người nào hết bài trước sẽ thắng

### 🆕 Tính năng Bắt UNO:
- **Khi nào xuất hiện**: Sau 3 giây khi có người chơi còn 1 lá bài mà chưa gọi UNO
- **Cách bắt**: Click nút "Bắt UNO" màu đỏ xuất hiện trong game
- **Hình phạt**: Người bị bắt sẽ phải rút thêm 2 lá bài
- **Lưu ý**: Chỉ những người chơi khác mới có thể bắt UNO (không thể tự bắt mình)

## Network Setup

### Chơi trên cùng máy:
- Server host: `localhost` hoặc `127.0.0.1`
- Port: `12345`

### Chơi qua mạng LAN:
- Server host: IP của máy host (VD: `192.168.1.100`)
- Port: `12345`
- **Lưu ý**: Đảm bảo firewall cho phép port 12345

### Chơi qua Internet:
- Cần port forwarding trên router của host
- Forward port 12345 đến IP local của máy host
- Client dùng IP public của router host

## Troubleshooting

### Network Issues:
**"Could not connect to server"**
- Kiểm tra IP và port có đúng không
- Đảm bảo server đã khởi động
- Kiểm tra firewall/antivirus

**"Server already in use"**  
- Port 12345 đã được sử dụng
- Đóng server cũ hoặc đổi port

**Game không bắt đầu**
- Đảm bảo đủ số lượng người chơi đã đặt
- Tất cả client phải kết nối thành công


## Core Files

### Multiplayer System:
- `UNOServer.java` - Server game multiplayer với enhanced logging
- `UNOClient.java` - Client với GUI và UNO catching system
- `ClientHandler.java` - Xử lý kết nối client với CATCH_UNO support
- `UNOLauncher.java` - Launcher chọn chế độ chơi

### 🆕 Enhanced Features:
- **Game.java**: Cập nhật với penalty system và UNO tracking
- **Timer System**: 3-second delay cho nút Bắt UNO
- **Debug Logging**: Comprehensive logging throughout the system
- **UNO Status Tracking**: Real-time tracking của trạng thái UNO

## Version History

### v2.1 (Latest) - Enhanced UNO Features
- ✅ Catch UNO system với 3-second timer
- ✅ Automatic penalty system (2 cards)  
- ✅ Enhanced debug logging
- ✅ Improved error handling

### v2.0 - Multiplayer Support  
- ✅ Client-server architecture
- ✅ Multi-platform support
- ✅ Real-time game updates
- ✅ Socket communication

## 🔧 Development & Debugging

### Compile và chạy:
```bash
# Compile project
ant compile

# Chạy game
ant run
```

### Debug Mode:
Game tự động bật debug logging. Các log quan trọng:
- `DEBUG: catchUno() method called` - Client gửi lệnh bắt UNO
- `DEBUG: Received CATCH_UNO command` - Server nhận lệnh 
- `CATCH UNO: [player1] bắt [player2]` - Thành công bắt UNO
- `PENALTY: Player [name] được phạt X lá bài` - Hình phạt được áp dụng

## Compatibility

- ✅ **Java 8+**: Yêu cầu tối thiểu
- ✅ **Windows/Mac/Linux**: Cross-platform  
- ✅ **Swing GUI**: Giao diện native
- ✅ **TCP Socket**: Reliable connection

---

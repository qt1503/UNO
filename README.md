# UNO Multiplayer Game

A Java-based implementation of the classic UNO card game with multiplayer support and enhanced gameplay features.

## 🎮 Features

### Core Game Features
- **Multiplayer Support**: 2-10 players via socket connection
- **Real-time Gameplay**: Synchronized game state across all clients
- **Complete UNO Rules**: All standard UNO cards and game mechanics
- **Wild Card Support**: Color selection for Wild and Wild Draw Four cards
- **Game Over Detection**: Automatic winner announcement

### 🆕 Enhanced Features
- **UNO Catch System**: 3-second timer mechanism to catch players who forget to call UNO
- **Penalty System**: Automatic 2-card penalty for rule violations
- **Enhanced Logging**: Comprehensive debug system for troubleshooting
- **Dynamic Player Management**: Support for variable player count
- **Real-time Messaging**: In-game chat and notification system

## 🚀 Getting Started

### Prerequisites
- Java 8 or higher
- Network connectivity for multiplayer

### Installation
1. Clone the repository
2. Compile the Java files:
   ```bash
   javac -d build src/uno/*.java
   ```
3. Run the launcher:
   ```bash
   java -cp build uno.Main
   ```

### Quick Start
1. **Start Server**: Run `UNOServer.main()` and specify number of players (2-10)
2. **Connect Clients**: Each player runs `UNOClient` and connects to server
3. **Play Game**: Follow standard UNO rules with enhanced catching mechanics

## 🎯 How to Play

### Standard UNO Rules
- Match cards by color or number
- Special action cards: Skip, Reverse, Draw Two
- Wild cards can be played anytime and change color
- Call "UNO" when you have one card left

### 🆕 UNO Catch System
1. **When a player has 1 card**: They must call UNO before playing their last card
2. **3-second grace period**: Other players can catch them if they forget
3. **Catch UNO button appears**: Red button shows up for other players after 3 seconds
4. **Penalty enforcement**: Caught players automatically draw 2 cards
5. **Fair play**: System prevents false accusations

#### How UNO Catching Works:
- Player has 1 card but doesn't call UNO → `UNO_CATCHABLE:true` sent to other players
- 3-second timer starts automatically
- "Catch UNO" button appears for other players
- Any player can click to catch the violator
- Violator receives 2-card penalty automatically

## 🔧 Technical Details

### Architecture
- **Client-Server Model**: Socket-based communication on port 12345
- **Multi-threading**: Concurrent client handling with `ClientHandler`
- **Game State Synchronization**: Real-time updates across all clients
- **Protocol-based Communication**: Structured message format

### Communication Protocol
```
GAME_START:[player1,player2,...]
GAME_STATE:CURRENT_PLAYER:name;TOP_CARD:color-value;HAND:card1,card2;OTHER_HANDS:player:count;UNO_CATCHABLE:true/false
PLAY_CARD:[cardIndex]
PLAY_WILD_CARD:[cardIndex]:[chosenColor]
DRAW_CARD
CALL_UNO
CATCH_UNO
MESSAGE:[text]
GAME_OVER:[winner]
```

### Game State Format
```
CURRENT_PLAYER:PlayerName
TOP_CARD:Color-Value (e.g., "Blue-Seven", "Red-Wild")
HAND:Card1,Card2,Card3 (player's cards)
OTHER_HANDS:Player1:5,Player2:3 (other players' card counts)
UNO_CATCHABLE:true/false (whether someone can be caught)
```

## 🎮 Controls

### In-Game Actions
- **Click Card**: Play the selected card
- **Draw Card Button**: Draw from deck when no valid plays
- **UNO Button**: Call UNO when you have 1 card (appears automatically)
- **Catch UNO Button**: Catch other players who forgot UNO (appears after 3 seconds)

### Wild Card Selection
- When playing Wild or Wild Draw Four
- Color selection popup appears
- Choose new color for continued play

## 🔍 Troubleshooting

### Common Issues

#### UNO Catching Not Working
- **Check console logs**: Look for `DEBUG: catchUno() method called`
- **Verify timer**: Should see `3 seconds elapsed - checking if should show CATCH UNO BUTTON...`
- **Button visibility**: Red "Catch UNO" button should appear top-right

#### Connection Issues
- **Port conflicts**: Ensure port 12345 is available
- **Firewall**: Check firewall settings for Java applications
- **Network**: Verify localhost connectivity

#### Game State Sync Problems
- **Check logs**: Look for `Error sending game state` messages
- **Player count**: Verify expected vs connected players match
- **Threading**: Check for concurrent modification exceptions

### Debug Mode
Enable detailed logging by monitoring console output:
```
DEBUG: Found player without UNO: [PlayerName]
CATCH UNO: [Catcher] caught [Violator]
PENALTY: Player [Name] penalized with 2 cards...
```

### Key Log Messages
- `UNO Server started on port 12345` - Server initialization
- `Player X connected` - Client connections
- `Starting game with X players` - Game initialization
- `CATCH UNO BUTTON IS NOW VISIBLE!` - Catch mechanism active
- `Card played successfully` - Move processing

## 📁 Project Structure

```
src/
├── uno/
│   ├── Main.java                    # Application entry point
│   ├── UNOLauncher.java            # Game launcher interface
│   ├── UNOServer.java              # Multiplayer server
│   ├── UNOClient.java              # Game client
│   ├── ClientHandler.java          # Client connection handler
│   ├── Game.java                   # Core game logic
│   ├── UnoCard.java               # Card representation
│   ├── UnoDeck.java               # Deck management
│   └── PickColorFrameForClient.java # Wild card color selector
└── com/mycompany/uno/small/        # Card images
```

## 🎯 Game Rules Implementation

### Standard Rules ✅
- Color/number matching
- Action cards (Skip, Reverse, Draw Two)
- Wild and Wild Draw Four cards
- Turn-based gameplay
- Winner determination

### Enhanced Rules ✅
- UNO call requirement when at 1 card
- 3-second grace period for catching
- Automatic penalty system (2 cards)
- Real-time rule enforcement
- Fair play mechanisms

## 🔨 Development

### Compilation
```bash
javac -cp . src/uno/*.java
```

### Running Server
```bash
java -cp . uno.UNOServer
```

### Running Client
```bash
java -cp . uno.UNOClient
```

### Debug Mode
Monitor console output for detailed game flow information.

## 📝 Version History

**v2.1 (Current)**
- Enhanced UNO catching system with 3-second timer
- Automatic penalty enforcement
- Improved multiplayer stability
- Comprehensive debug logging

**v2.0**
- Multiplayer client-server architecture
- Socket-based communication
- Real-time game synchronization

**v1.0**
- Basic UNO game implementation
- Single-player functionality

## 👥 Contributors

- **Author**: quytien
- **Enhanced multiplayer features**: Advanced UNO catching system

## 📄 License

This project is open source. Feel free to modify and distribute.

---

**🎉 Enjoy playing UNO with friends! The enhanced catching system makes the game more exciting and fair for everyone.**
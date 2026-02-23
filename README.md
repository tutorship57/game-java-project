# POP-OBJ Java Game Project | Object-Oriented Programming Project

A simple 2D Java Swing game that allows a player to control a bird using the keyboard.
The bird can move in four directions within a fixed game window.

## ✨ Features

- Java Swing GUI
- Keyboard movement (WASD / Arrow keys)
- Movement boundary detection (cannot move outside the window)
- Background image
- Sprite-based character (bird)

---

## 🎮 Controls

| Key        | Action     |
| ---------- | ---------- |
| `W` or `↑` | Move Up    |
| `S` or `↓` | Move Down  |
| `A` or `←` | Move Left  |
| `D` or `→` | Move Right |

---

## 🖼️ Game Preview

The game window size is **800 x 600 px** with:

- Background image
- Movable bird character

---

## 🛠️ Technologies Used

- Java
- Java Swing
- AWT Event Handling

---

## 📂 Project Structure

```
project-root
│── src
│   ├── Game.java
│   ├── background.jpg
│   └── bird.png
```

---

## ▶️ How to Run

### 1️⃣ Compile

```bash
javac src/Game.java
```

### 2️⃣ Run

```bash
java -cp src Game
```

---

## ⚙️ Game Settings

You can change these values inside the code:

```java
int velocity = 50;        // movement speed
int frameWidth = 800;     // window width
int frameHeight = 600;    // window height
```

---

## 🧠 How It Works

- `JFrame` → main game window
- `JLabel` → used for:
  - background
  - bird sprite

- `KeyListener` → detects keyboard input
- `setLocation()` → updates bird position
- Boundary checking prevents the bird from moving outside the screen

---

## 🚀 Future Improvements

- Smooth movement (Timer / game loop)
- Collision detection
- Gravity system (Flappy Bird style)
- Score system
- Sound effects
- Object-oriented refactor (separate classes)

---

## 👤 Author

Developed as a Java Swing practice project for learning basic game mechanics and event handling.

---

💬 ถ้าต้องการเวอร์ชัน:

- ใส่ GIF gameplay
- เขียนแบบโปรขึ้น (สำหรับ Portfolio)
- หรือแยก MVC / OOP อธิบายเชิงวิชาการ

บอกได้เลย เดี๋ยวจัดให้เหมาะกับสาย **Data / Software / Game dev CV** 😄

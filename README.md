# Clip Sync

Clip Sync is a cross-platform clipboard synchronization tool that allows you to seamlessly share clipboard content between your PC and Android device.

## How it Works

The project consists of two main components:
1.  **Backend (PC):** A Spring Boot application that runs on your computer. It monitors and updates your PC's clipboard via a REST API.
2.  **Android App:** A mobile application that synchronizes its clipboard with the backend server.

When you trigger a "Sync", the Android app sends its current clipboard text to the PC. The PC updates its own clipboard with this text and returns its previous clipboard content back to the Android app, which then updates the phone's clipboard.

---

## 🚀 Setup Instructions

### 1. Clone the Repository
First, clone the project to your PC:
```bash
git clone https://github.com/PiyushJT/Clip-Sync.git
cd clip-sync
```

### 2. Backend Setup (PC)
The backend is built with Java 17 and Spring Boot.

**Prerequisites:**
- Java 17 or higher installed.

**Running the Backend (must be running in background):**
1. Run the JAR:
   ```bash
   java -jar backend/clipsync.jar
   ```
   The server will start on port `9876`.

#### Running on Startup (Optional)
To ensure Clip Sync is always ready, you can set it to run on startup:

- **Windows:**
  1. Copy the jar file `java -jar backend/clipsync.jar`.
  2. Press `Win + R`, type `shell:startup`, and paste the shortcut there.
- **macOS:**
  1. Open **System Settings** > **General** > **Login Items**.
  2. Add your JAR or a script to the list.
- **Linux:**
  1. Use `crontab -e` and add `@reboot java -jar backend/clipsync.jar &`.

---

### 3. Android Setup

#### Install the App
1. Transfer the `ClipSync.apk` file (in /android) to your phone and install it (enable "Install from unknown sources" if prompted).

#### Connect to PC (IP Address)
Your phone and PC must be on the **same local network**.

**How to find your PC's IP Address:**
- **Windows:** Open Command Prompt, type `ipconfig`, and look for `IPv4 Address` (e.g., `192.168.1.5`).
- **macOS/Linux:** Open Terminal, type `ifconfig` (or `ip addr`), and look for the `inet` address under your active network interface (usually `en0` or `eth0`).

**Configuration in App:**
1. Launch **ClipSync**.
2. Enter your PC's IP address in the configuration field.
3. Tap **Save Configuration**.
4. Close the app completely (optional).

---

## 📱 Using the Widget

For the best experience, use the **Home Screen Widget**:

1. Long-press on your home screen.
2. Select **Widgets**.
3. Find **ClipSync** and add the 1x1 widget to your screen.
4. **Tap the widget** to instantly sync clipboards!

> [!TIP]
> **Better UX:** After setting up the app, you can remove it from your "Recent Apps" list. The widget works independently and provides a cleaner experience for quick syncing.

---

## Project Structure

- `backend/`: Spring Boot server handling desktop clipboard operations.
- `android/`: Android application with Compose UI and Glance Widget.

## License

No license, use however you want! 😀
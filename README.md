# Clip Sync

Clip Sync is a cross-platform clipboard synchronization tool that allows you to seamlessly share clipboard content between your PC and Android device.

## How it Works

The project consists of two main components:
1.  **Backend (PC):** A Spring Boot application that runs on your computer. It monitors and updates your PC's clipboard via a REST API.
2.  **Android App:** A mobile application that synchronizes its clipboard with the backend server. It includes a user-friendly interface and a Home Screen Widget for quick access.

When you trigger a "Sync", the Android app sends its current clipboard text to the PC. The PC updates its own clipboard with this text and returns its previous clipboard content back to the Android app, which then updates the phone's clipboard.

## How to use

### 1. Backend Setup (PC)

The backend is built with Java 21 and Spring Boot.

**Prerequisites:**
- Java 21 or higher installed on your PC.

**Running the Backend:**

Method 1: Run the JAR directly (Recommended for production)

1.  Run the generated JAR:
    ```bash
    git clone https://github.com/piyushjt/clipboard-sync.git
    
    cd clipboard-sync/backend

    java -jar build/libs/clipsync.jar
    ```
    The server will start on port `9876` by default.



Method 2: Run using Intellij IDEA (Recommended for development)

1. Open the backend directory in Intellij IDEA.
2. Run the application:
    - Open the `MainApplication` class.
    - Click the green play button to run the application.

### 2. Android Setup

**Prerequisites:**
- Android Studio installed.
- Your Android device and PC must be on the same local network.

**Configuration:**
1.  Find your PC's local IP address (e.g., `192.168.1.5`).
2.  Open `android/app/src/main/java/com/piyushjt/clipsync/api/ClipService.kt`.
3.  Update the `BASE_URL` to match your PC's IP and port:
    ```kotlin
    private const val BASE_URL = "http://YOUR_PC_IP:9876"
    ```
4.  Build and install the app on your Android device.

## Usage

### On Android
- **Manual Sync:** Open the Clip Sync app and tap the **Sync Clipboard** button.
- **Widget:** Add the Clip Sync widget to your home screen for one-tap synchronization without opening the app.

### On PC
- Once the backend is running, any text synced from your phone will automatically appear in your PC's clipboard.
- Conversely, whatever is in your PC's clipboard will be sent to your phone when you initiate a sync from the Android side.

## Project Structure

- `backend/`: Spring Boot server handling clipboard operations on the desktop.
- `android/`: Android application with Compose UI and Glance Widget.

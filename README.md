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
    - Open the `ClipsyncApplication` file.
    - Click the green play button to run the application.

### 2. Android Setup

**Prerequisites:**
- Android Studio installed.
- Your Android device and PC must be on the same local network.

**Configuration:**
1. Build and install the app on your Android device.
2. Launch the **ClipSync** app.
3. Find your PC's local IP address (e.g., `192.168.1.5`).
4. Enter the IP address in the configuration field within the app.
5. Tap **Save Configuration**.

## Usage

### On Android
- **Configuration:** Open the ClipSync app to set or update the backend server IP address. The app features a modern Material 3 design with adaptive dynamic colors based on your device theme.
- **Widget:** Add the ClipSync widget to your home screen for one-tap synchronization.

### On PC
- Once the backend is running, any text synced from your phone will automatically appear in your PC's clipboard.
- Conversely, whatever is in your PC's clipboard will be sent to your phone when you initiate a sync from the Android side.

## Project Structure

- `backend/`: Spring Boot server handling clipboard operations on the desktop.
- `android/`: Android application with Compose UI and Glance Widget.

## Project Status & Customization

ClipSync is an open-source initiative designed for flexibility and cross-platform utility. We welcome innovation and encourage developers to **fork** this codebase to suit specific workflow requirements or to integrate additional features.

If you develop improvements or identify opportunities for enhancement, pull requests and community contributions are highly encouraged as we strive to refine the seamless clipboard synchronization experience.

## Licence

No licence, use however you want😀.
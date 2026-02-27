package com.piyushjt.clipsync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClipsyncApplication {

    static {
        // 1. Enable AWT
        System.setProperty("java.awt.headless", "false");
        // 2. Hide from Mac Dock
        System.setProperty("apple.awt.UIElement", "true");
    }

    public static void main(String[] args) {
        SpringApplication.run(ClipsyncApplication.class, args);
    }

}

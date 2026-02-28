package com.piyushjt.clipsync;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

import java.awt.*;
import java.awt.datatransfer.*;


@RestController
@RequiredArgsConstructor
public class ClipController {


    @PostMapping("/")
    public ResponseEntity<RequestResponse> exchangeClip(
            @RequestBody RequestResponse request
    ) {

        String currentText = getClipboardText();
        String currentImage = getClipboardImage();
        
        System.out.println("--- Sync Request ---");
        System.out.println("PC Current Text: " + (currentText != null ? "present (" + currentText.length() + " chars)" : "null"));
        System.out.println("PC Current Image: " + (currentImage != null ? "present (" + currentImage.length() + " chars)" : "null"));

        if (request.getImage() != null && !request.getImage().isEmpty()) {
            setClipboardImage(request.getImage());
            System.out.println("Action: Received Image from Phone, updated PC clipboard");
        } else if (request.getText() != null && !request.getText().isEmpty()) {
            setClipboardText(request.getText());
            System.out.println("Action: Received Text from Phone: " + request.getText());
        } else {
            System.out.println("Action: No new content from Phone");
        }

        return ResponseEntity.ok(
                new RequestResponse(currentText, currentImage)
        );

    }

    public static void setClipboardText(String text) {
        StringSelection selection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }

    public static String getClipboardText() {
        try {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            Transferable contents = clipboard.getContents(null);
            if (contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                String text = (String) contents.getTransferData(DataFlavor.stringFlavor);
                return !text.isEmpty() ? text : null;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static void setClipboardImage(String base64Image) {
        try {
            byte[] imageBytes = java.util.Base64.getDecoder().decode(base64Image);
            java.io.ByteArrayInputStream bais = new java.io.ByteArrayInputStream(imageBytes);
            javax.imageio.ImageIO.read(bais); // Verify it's a valid image
            bais.reset();
            java.awt.image.BufferedImage img = javax.imageio.ImageIO.read(bais);
            
            ImageTransferable transferable = new ImageTransferable(img);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(transferable, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getClipboardImage() {
        try {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            if (!clipboard.isDataFlavorAvailable(DataFlavor.imageFlavor)) {
                return null;
            }

            Transferable contents = clipboard.getContents(null);
            if (contents != null && contents.isDataFlavorSupported(DataFlavor.imageFlavor)) {
                Object data = contents.getTransferData(DataFlavor.imageFlavor);
                if (!(data instanceof java.awt.Image)) {
                    return null;
                }
                
                java.awt.Image img = (java.awt.Image) data;
                
                // Force loading of image (especially for ToolkitImage)
                int width = img.getWidth(null);
                int height = img.getHeight(null);
                
                if (width <= 0 || height <= 0) {
                    java.awt.MediaTracker tracker = new java.awt.MediaTracker(new java.awt.Component() {});
                    tracker.addImage(img, 0);
                    tracker.waitForID(0);
                    width = img.getWidth(null);
                    height = img.getHeight(null);
                }

                if (width <= 0 || height <= 0) {
                    System.out.println("Failed to get image dimensions: " + width + "x" + height);
                    return null;
                }

                java.awt.image.BufferedImage bufferedImage = new java.awt.image.BufferedImage(
                    width, height, java.awt.image.BufferedImage.TYPE_INT_ARGB);
                java.awt.Graphics2D g2d = bufferedImage.createGraphics();
                g2d.drawImage(img, 0, 0, null);
                g2d.dispose();

                java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
                javax.imageio.ImageIO.write(bufferedImage, "png", baos);
                return java.util.Base64.getEncoder().encodeToString(baos.toByteArray());
            }
            return null;
        } catch (Exception e) {
            System.err.println("Error in getClipboardImage: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    static class ImageTransferable implements Transferable {
        private java.awt.Image image;

        public ImageTransferable(java.awt.Image image) {
            this.image = image;
        }

        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[] { DataFlavor.imageFlavor };
        }

        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return DataFlavor.imageFlavor.equals(flavor);
        }

        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
            if (flavor.equals(DataFlavor.imageFlavor)) {
                return image;
            } else {
                throw new UnsupportedFlavorException(flavor);
            }
        }
    }

}

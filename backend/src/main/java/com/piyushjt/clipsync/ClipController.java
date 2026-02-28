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
    public ResponseEntity<RequestResponse> exchangeText(
            @RequestBody RequestResponse request
    ) {

        String fromPC = getClipboardText();

        if (request.getText() != null && !request.getText().isEmpty()) {
            setClipboardText(request.getText());
            System.out.println("Received: " + request.getText());
        } else {
            System.out.println("Received null or empty, current: " + fromPC);
        }

        return ResponseEntity.ok(
                new RequestResponse(fromPC)
        );

    }



    public static void setClipboardText(String text) {
        StringSelection selection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }

    /**
     * Retrieves a string from the system clipboard.
     */
    public static String getClipboardText() {

        try {

            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            Transferable contents = clipboard.getContents(null);

            if (contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                String text = (String) contents.getTransferData(DataFlavor.stringFlavor);
                return !text.isEmpty() ? text : null;
            }

            return null;

        }
        catch (Exception e) {
            return null;
        }
    }

}

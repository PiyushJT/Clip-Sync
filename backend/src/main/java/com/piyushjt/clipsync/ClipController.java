package com.piyushjt.clipsync;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;


import java.awt.*;
import java.awt.datatransfer.*;
import java.io.OutputStream;
import java.io.PrintStream;

@RestController
@RequiredArgsConstructor
public class ClipController {


    @PostMapping("/")
    public ResponseEntity<RequestResponse> exchangeText(
            @RequestBody RequestResponse request
    ) {

        String fromPC = getClipboardText();

        setClipboardText(request.getText());

        System.out.println(request.getText());

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
        // Save the original error stream
        PrintStream originalErr = System.err;
        // Redirect it to nowhere
        System.setErr(new PrintStream(OutputStream.nullOutputStream()));

        try {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            return (String) clipboard.getData(DataFlavor.stringFlavor);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        } finally {
            // Restore the original error stream so you don't miss actual app errors
            System.setErr(originalErr);
        }
    }

}

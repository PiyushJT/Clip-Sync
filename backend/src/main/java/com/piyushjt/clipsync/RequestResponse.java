package com.piyushjt.clipsync;

import lombok.Data;

@Data
public class RequestResponse {

    public RequestResponse(String text, String image) {
        this.text = text;
        this.image = image;
    }

    private String text;
    private String image;

}
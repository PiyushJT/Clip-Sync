package com.piyushjt.clipsync;

import lombok.Data;

@Data
public class RequestResponse {

    public RequestResponse(String text) {
        this.text = text;
    }

    private String text;

}
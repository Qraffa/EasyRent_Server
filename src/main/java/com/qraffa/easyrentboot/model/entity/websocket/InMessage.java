package com.qraffa.easyrentboot.model.entity.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InMessage {

    private String token;

    private String to;

    private String content;
}

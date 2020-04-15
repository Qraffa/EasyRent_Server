package com.qraffa.easyrentboot.model.entity.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutMessage {

    private String from;

    private String content;

    private Long time;
}

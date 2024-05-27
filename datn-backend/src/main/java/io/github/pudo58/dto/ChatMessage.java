package io.github.pudo58.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatMessage {
    private String user;
    private String message;
    private long timestamp;
}

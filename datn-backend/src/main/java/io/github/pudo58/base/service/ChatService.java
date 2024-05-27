package io.github.pudo58.base.service;

import io.github.pudo58.base.entity.Chat;
import io.github.pudo58.base.service.core.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ChatService extends Service<Chat> {
    Chat send(Chat chat);

    List<Chat> getMessages(UUID from);
}

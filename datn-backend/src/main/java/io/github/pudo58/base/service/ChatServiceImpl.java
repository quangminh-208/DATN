package io.github.pudo58.base.service;

import io.github.pudo58.base.entity.BaseEntity;
import io.github.pudo58.base.entity.Chat;
import io.github.pudo58.base.entity.User;
import io.github.pudo58.base.repo.ChatRepo;
import io.github.pudo58.base.repo.UserRepo;
import io.github.pudo58.base.service.core.AbstractService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl extends AbstractService<Chat> implements ChatService {
    private final ChatRepo chatRepo;
    private final UserRepo userRepo;

    @Override
    public List<Chat> getMessages(UUID from) {
        List<Chat> chats = this.chatRepo.findAll();
        List<Chat> result = new ArrayList<>();
        for (Chat chat : chats) {
            if (chat.getFrom().getId().equals(from) && chat.getTo().getId().equals(User.getContextId())) {
                result.add(chat);
            } else if (chat.getTo().getId().equals(from) && chat.getFrom().getId().equals(User.getContextId())) {
                result.add(chat);
            }
        }
        result.sort(Comparator.comparing(BaseEntity::getCreateDate));
        return result;
    }

    @Override
    public Chat send(Chat chat) {
        chat.setFrom(User.getContext());
        chat.setTo(this.userRepo.findById(chat.getToUser()).orElse(null));
        return this.save(chat);
    }
}

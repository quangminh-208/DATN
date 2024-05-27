package io.github.pudo58.endpoint.v2;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.google.firebase.database.DatabaseReference;
import io.github.pudo58.base.entity.Chat;
import io.github.pudo58.base.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v2/chat")
public class ChatV2Controller {

    private final DatabaseReference databaseReference;
    private final ChatService chatService;
    private final SocketIOServer socketIOServer;

    @Autowired
    public ChatV2Controller(DatabaseReference databaseReference, ChatService chatService , SocketIOServer socketIOServer) {
        this.databaseReference = databaseReference;
        this.chatService = chatService;
        this.socketIOServer = socketIOServer;
    }


    @PostMapping("/send")
    public String sendMessage(@RequestBody Chat message) {
        String key = databaseReference.push().getKey();
        if (key != null) {
            databaseReference.child(key).setValueAsync(message);
            Chat chat =  chatService.send(message);
            socketIOServer.getBroadcastOperations().sendEvent("chat", chat);
            return chat.getMessage();
        }
        throw new IllegalArgumentException("Failed to send message");
    }

    @GetMapping("/messages")
    public List<Chat> getMessages(@RequestParam UUID from) {
        return chatService.getMessages(from);
    }

}

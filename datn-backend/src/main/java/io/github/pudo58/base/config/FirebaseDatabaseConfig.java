package io.github.pudo58.base.config;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirebaseDatabaseConfig {
    @Bean
    public DatabaseReference firebaseDatabaseReference(FirebaseApp firebaseApp) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance(firebaseApp);
        return firebaseDatabase.getReference("chats");
    }
}

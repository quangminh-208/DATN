package io.github.pudo58.base.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @Value("classpath:static/serviceAccountKey.json")
    private Resource serviceAccountKey;

    @Bean
    public FirebaseApp initializeFirebase() throws IOException {

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccountKey.getInputStream()))
                .setDatabaseUrl("https://monatourist-d447f-default-rtdb.firebaseio.com/")
                .build();
        System.out.println("FirebaseApp.initializeApp(options)");

        return FirebaseApp.initializeApp(options);
    }
}

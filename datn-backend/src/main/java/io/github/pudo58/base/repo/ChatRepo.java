package io.github.pudo58.base.repo;

import io.github.pudo58.base.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChatRepo extends JpaRepository<Chat, UUID> {
    List<Chat> findByFromIdAndToId(UUID fromId, UUID toId);
}

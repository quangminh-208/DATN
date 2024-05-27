package io.github.pudo58.record;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

public record UserRecord(UUID id, String username, String email, Date createdDate, byte[] avatar, Set<String> roleList, int status) {
}

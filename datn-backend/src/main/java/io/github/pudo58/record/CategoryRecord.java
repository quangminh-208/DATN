package io.github.pudo58.record;

import io.github.pudo58.base.entity.Category;

import java.util.UUID;

public record CategoryRecord(UUID id, String name, Category parent) {
}

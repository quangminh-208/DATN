package io.github.pudo58.base.service;

import io.github.pudo58.base.entity.Category;
import io.github.pudo58.base.service.core.Service;
import io.github.pudo58.dto.CategorySearchRequest;
import io.github.pudo58.record.CategoryRecord;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService extends Service<Category> {
    Page<CategoryRecord> findBySearch(CategorySearchRequest model);

    List<Category> findAllForClient();
}

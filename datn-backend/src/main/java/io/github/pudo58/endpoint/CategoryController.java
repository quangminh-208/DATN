package io.github.pudo58.endpoint;

import io.github.pudo58.base.controller.AbstractController;
import io.github.pudo58.base.entity.Category;
import io.github.pudo58.base.service.CategoryService;
import io.github.pudo58.dto.CategorySearchRequest;
import io.github.pudo58.record.CategoryRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController extends AbstractController<Category> {
    private final CategoryService categoryService;

    @PostMapping("/findBySearch")
    public Page<CategoryRecord> findBySearch(@RequestBody CategorySearchRequest model) {
        return this.categoryService.findBySearch(model);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable UUID id) {
        this.categoryService.deleteById(id);
    }
}

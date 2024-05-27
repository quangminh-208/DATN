package io.github.pudo58.base.service;

import io.github.pudo58.base.entity.Category;
import io.github.pudo58.base.repo.CategoryRepo;
import io.github.pudo58.base.repo.TourRepo;
import io.github.pudo58.base.service.core.AbstractService;
import io.github.pudo58.constant.CategoryConst;
import io.github.pudo58.dto.CategorySearchRequest;
import io.github.pudo58.record.CategoryRecord;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl extends AbstractService<Category> implements CategoryService {
    private final CategoryRepo categoryRepo;
    private final TourRepo tourRepo;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Category save(Category category) {
        if (category.getParentId() != null) {
            Category parent = this.categoryRepo.findById(category.getParentId()).orElse(null);
            Assert.notNull(parent, "Parent category not found");
            category.setParent(parent);
        }
        return super.save(category);
    }

    @Override
    public Category update(UUID uuid, Category category) {
        if (category.getParentId() != null) {
            Category parent = this.categoryRepo.findById(category.getParentId()).orElse(null);
            Assert.notNull(parent, "Parent category not found");
            category.setParent(parent);
        }
        return super.update(uuid, category);
    }

    @Override
    public Page<CategoryRecord> findBySearch(CategorySearchRequest model) {
        Pageable pageable = PageRequest.of(model.getPage(), model.getSize());
        return this.categoryRepo.findBySearch(model, pageable);
    }

    @Override
    public List<Category> findAllForClient() {
        List<Category> rootCategories = this.categoryRepo.findRootCategory();
        for (Category rootCategory : rootCategories) {
            rootCategory.setChildren(this.findChildren(rootCategory.getId()));
            for (Category child : rootCategory.getChildren()) {
                child.setTours(this.tourRepo.findByCategoryId(child.getId()));
            }
        }
        return rootCategories.stream().filter(c ->c.getChildren().size() > 0).toList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(UUID uuid) {
        Category category = this.categoryRepo.findById(uuid).orElse(null);
        Assert.notNull(category, "Category not found");
        category.setStatus(CategoryConst.STATUS_INACTIVE);
        this.categoryRepo.save(category);
    }

    private List<Category> findChildren(UUID parentId) {
        Query query = this.entityManager.createQuery("select c from Category c where c.parent.id = :parentId");
        query.setParameter("parentId", parentId);
        List<Category> children = query.getResultList();
        for (Category child : children) {
            child.setChildren(this.findChildren(child.getId()));
            child.setTours(this.tourRepo.findByCategoryId(child.getId()));
        }
        return children;
    }
}

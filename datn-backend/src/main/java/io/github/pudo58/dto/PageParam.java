package io.github.pudo58.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageParam {
    protected int page;
    protected int size;

    public PageParam() {
    }

    public int getPage() {
        return Math.max(page, 0);
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return Math.max(size, 1);
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Pageable getPageable() {
        return PageRequest.of(getPage(), getSize());
    }
}

package com.font.specificationexecutor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

public class SortedPageable implements Serializable {

    private final String SORT_SEPARATOR = ",";
    private Integer page;
    private Integer size;
    private String sort;


    public Pageable getPageable() {
        if (page == null || size == null || (page == 0 && size == 0)) {
            int maxPageSize = Integer.MAX_VALUE;
            return PageRequest.of(0, maxPageSize, this.getSort());
        }
        return PageRequest.of(page, size, this.getSort());
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getSortBy() {
        if(!sort.contains(SORT_SEPARATOR)) {
            return sort;
        }
        return sort.split(SORT_SEPARATOR)[0];
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getDirection() {
        if(!sort.contains(SORT_SEPARATOR)) {
            return null;
        }
        return sort.split(SORT_SEPARATOR)[1];
    }

    public Sort getSort() {
        if (getSortBy() == null || getSortBy().isEmpty()) {
            return Sort.unsorted();
        }
        Sort.Direction sortDirection = (this.getDirection() != null && this.getDirection().equalsIgnoreCase("desc"))
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;
        return Sort.by(sortDirection, this.getSortBy());
    }
}

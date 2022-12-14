package com.qirsam.mini_library.web.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public record PageResponse<T>(List<T> content, PageResponse.Metadata metadata) {

    public static <T> PageResponse<T> of(Page<T> page) {
        var metadata = new Metadata(page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
        return new PageResponse<>(page.getContent(), metadata);
    }

    public record Metadata(int page, int size, long totalElements, int totalPages) {
    }
}

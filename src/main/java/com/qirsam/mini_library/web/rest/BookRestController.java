package com.qirsam.mini_library.web.rest;

import com.qirsam.mini_library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookRestController {

    private final BookService bookService;

    @GetMapping("/{id}/cover")
    public ResponseEntity<byte[]> findCover(@PathVariable("id") Long id) {
        return bookService.findCover(id)
                .map(content -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                        .contentLength(content.length)
                        .body(content))
                .orElseGet(ResponseEntity.notFound()::build);
    }

}

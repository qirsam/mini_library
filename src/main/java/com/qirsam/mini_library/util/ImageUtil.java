package com.qirsam.mini_library.util;

import com.qirsam.mini_library.database.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ImageUtil {
    private final BookRepository bookRepository;

    public String getBookImageFilename() {
        return bookRepository.getBookIdSeq() + 1L + ".jpg";
    }
}

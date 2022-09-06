package com.qirsam.mini_library;

import com.qirsam.mini_library.mapper.BookReadMapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.SpyBean;

@TestConfiguration
public class TestMiniLibraryApplication {

    @SpyBean
    private BookReadMapper bookReadMapper;
}

package com.qirsam.mini_library.integration.service;

import com.qirsam.mini_library.integration.IntegrationTestBase;
import com.qirsam.mini_library.service.ImageService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ImageServiceIT extends IntegrationTestBase {

    private final ImageService imageService;

//    @SneakyThrows
//    @Test
//    void uploadImageToDisk() {
//        MockMultipartFile testFile = new MockMultipartFile("test", "test", "testFile", new byte[10]);
//        Path result = imageService.uploadImageToDisk(testFile);
//        Assertions.assertThat(result)
//                .exists()
//                .hasFileName("test");
//        Files.deleteIfExists(result);
//    }
}

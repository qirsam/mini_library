package com.qirsam.mini_library.service;

import com.qirsam.mini_library.util.MainUtilityClass;
import com.qirsam.mini_library.util.MyRestClient;
import com.yandex.disk.rest.Credentials;
import com.yandex.disk.rest.RestClient;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${app.image.bucket}")
    private final String bucket;

    @Value("${yandex.disk.token}")
    private final String token;


    @SneakyThrows
    public void upload(String imagePath, InputStream content) {
        var fullImagePath = Path.of(bucket, imagePath);
        try (content) {
            Files.createDirectories(fullImagePath.getParent());
            Files.write(fullImagePath, content.readAllBytes(), CREATE, TRUNCATE_EXISTING);
        }
    }

    @SneakyThrows
    public void uploadImage(MultipartFile image) {
        if (!image.isEmpty()) {
            upload(image.getOriginalFilename(), image.getInputStream());
        }
    }

    @SneakyThrows
    public Optional<byte[]> getImage(String imagePath) {
        var fullImagePath = Path.of(bucket, imagePath);
        return Files.exists(fullImagePath)
                ? Optional.of(Files.readAllBytes(fullImagePath))
                : Optional.empty();
    }

    @SneakyThrows
    public void uploadImageYandexDisk(MultipartFile file, String folder, String filename) {
        var client = new RestClient(
                new Credentials("", token)
        );
        File tempFile = File.createTempFile("tempImage", ".jpg");
        file.transferTo(tempFile);

        var serverPath = MainUtilityClass.YA_DISK_APP_PATH_IMAGE + folder + filename;
        var uploadLink = client.getUploadLink(serverPath, true);

        client.uploadFile(uploadLink, true, tempFile, null);
        tempFile.deleteOnExit();
    }

    @SneakyThrows
    public String getImageLinkFromYandexDisk(String folder, String filename) {
        var client = new MyRestClient(
                new Credentials("", token)
        );
        return client.getDownloadLink(folder, filename);
    }
}

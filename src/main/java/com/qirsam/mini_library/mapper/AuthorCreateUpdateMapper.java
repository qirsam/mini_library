package com.qirsam.mini_library.mapper;

import com.qirsam.mini_library.database.entity.library.Author;
import com.qirsam.mini_library.web.dto.AuthorCreateUpdateDto;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static java.util.function.Predicate.not;

@Component
public class AuthorCreateUpdateMapper implements  Mapper<AuthorCreateUpdateDto, Author> {

    @Override
    public Author map(AuthorCreateUpdateDto object) {
        var author = new Author();
        copy(object, author);
        return author;
    }

    @Override
    public Author map(AuthorCreateUpdateDto fromObject, Author toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(AuthorCreateUpdateDto fromObject, Author toObject) {
        toObject.setFirstname(fromObject.getFirstname());
        toObject.setLastname(fromObject.getLastname());
        toObject.setBirthDate(fromObject.getBirthDate());
        toObject.setDescription(fromObject.getDescription());

        Optional.ofNullable(fromObject.getImage())
                .filter(not(MultipartFile::isEmpty))
                .ifPresent(image -> toObject.setImage(image.getOriginalFilename()));
    }
}

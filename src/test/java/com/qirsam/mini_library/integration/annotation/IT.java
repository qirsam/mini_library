package com.qirsam.mini_library.integration.annotation;

import com.qirsam.mini_library.TestMiniLibraryApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ActiveProfiles("test")
@Transactional
@SpringBootTest(classes = TestMiniLibraryApplication.class)
public @interface IT {
}

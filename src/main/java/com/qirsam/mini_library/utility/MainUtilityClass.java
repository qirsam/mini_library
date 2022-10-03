package com.qirsam.mini_library.utility;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;

@UtilityClass
public class MainUtilityClass {

    public static final int FIRST_PAGE = 1;

    public static PageRequest defaultPageRequest(int pageNumber){
        return PageRequest.of(pageNumber - 1, 5);
    }
}

package com.qirsam.mini_library.web.error;

import org.springframework.core.NestedRuntimeException;

public class InvalidPasswordException extends NestedRuntimeException {

    public InvalidPasswordException(String message) {
        super(message);
    }

}

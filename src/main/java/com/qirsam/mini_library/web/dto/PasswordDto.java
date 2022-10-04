package com.qirsam.mini_library.web.dto;

import com.qirsam.mini_library.validation.ValidChangePassword;
import com.qirsam.mini_library.validation.ValidOldPassword;
import lombok.Value;

@Value
@ValidChangePassword
public class PasswordDto {
    @ValidOldPassword
    String oldPassword;

    String newPassword;

    String repeatPassword;
}

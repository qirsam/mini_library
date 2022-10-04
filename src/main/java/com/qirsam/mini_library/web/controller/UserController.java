package com.qirsam.mini_library.web.controller;

import com.qirsam.mini_library.database.entity.filter.UserFilter;
import com.qirsam.mini_library.database.entity.user.Role;
import com.qirsam.mini_library.database.entity.user.User;
import com.qirsam.mini_library.service.UserBookService;
import com.qirsam.mini_library.service.UserService;
import com.qirsam.mini_library.validation.groups.CreateAction;
import com.qirsam.mini_library.validation.groups.UpdateAction;
import com.qirsam.mini_library.web.dto.PageResponse;
import com.qirsam.mini_library.web.dto.PasswordDto;
import com.qirsam.mini_library.web.dto.UserCreateUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import javax.validation.groups.Default;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserBookService userBookService;
    private final MessageSource messageSource;

    @GetMapping("/registration")
    public String registration(Model model, @ModelAttribute("user") UserCreateUpdateDto user) {
        model.addAttribute("user", user);
        return "user/registration";
    }

    @PostMapping("/registration")
    public String create(@ModelAttribute @Validated({Default.class, CreateAction.class}) UserCreateUpdateDto user,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/registration";
        }
        userService.create(user);
        return "redirect:/login";
    }

    @GetMapping("/users/{id}")
    public String findById(@PathVariable("id") Long id,
                           Model model,
                           Pageable pageable) {
        return userService.findById(id)
                .map(user -> {
                    model.addAttribute("user", user);
                    model.addAttribute("userBooks", userBookService.findByUserId(id, pageable));
                    return "user/user";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/users")
    public String findAll(Model model, UserFilter filter, Pageable pageable) {
        var page = userService.findAll(filter, pageable);
        model.addAttribute("users", PageResponse.of(page));
        model.addAttribute("filter", filter);
        return "user/users";
    }


    @GetMapping("/users/{id}/update")
    public String updateUserPage(@PathVariable Long id, Model model) {
        return userService.findById(id)
                .map(user -> {
                    model.addAttribute("user", user);
                    model.addAttribute("roles", Role.values());
                    return "user/update-user";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/users/{id}/update")
    public String update(@PathVariable Long id,
                         @ModelAttribute @Validated({Default.class, UpdateAction.class}) UserCreateUpdateDto user,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/users/{id}/update";
        }

        return userService.update(id, user)
                .map(it -> "redirect:/users/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/users/{id}/delete")
    public String delete(@PathVariable Long id) {
        if (!userService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/users/i-am")
    public String redirectToUserPage() {
        var principal = (User) userService.loadUserByUsername(userService.getPrincipal().getUsername());
        var id = principal.getId();
        return "redirect:/users/" + id;
    }

    @GetMapping("/users/{id}/change-password")
    public String changePasswordPage(@PathVariable Long id,
                                     Model model) {
        return "user/change-password";
    }

    @PostMapping("/users/{id}/change-password")
    public String changePassword(@PathVariable Long id,
                                 Model model,
                                 @ModelAttribute @Valid PasswordDto passwordDto,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/users/{id}/change-password";
        }

        userService.changeUserPassword(passwordDto.getNewPassword());
        redirectAttributes.addFlashAttribute("messages",
                messageSource.getMessage("auth.message.changePasswordSuc", null, LocaleContextHolder.getLocale()));
        return "redirect:/users/{id}/change-password";
    }

}

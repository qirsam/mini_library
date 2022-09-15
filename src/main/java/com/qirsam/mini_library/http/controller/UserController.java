package com.qirsam.mini_library.http.controller;

import com.qirsam.mini_library.dto.UserCreateUpdateDto;
import com.qirsam.mini_library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserBookService userBookService;

    @GetMapping("/registration")
    public String registration(Model model, @ModelAttribute("user") UserCreateUpdateDto user) {
        model.addAttribute("user", user);
        return "user/registration";
    }

    @PostMapping("registration")
    public String create(@ModelAttribute UserCreateUpdateDto user,
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

    @GetMapping("/user/{id}")
    public String findById(@PathVariable("id") Long id,
                           Model model,
                           @AuthenticationPrincipal UserDetails userDetails) {
        return userService.findById(id)
                .map(user -> {
                    model.addAttribute("user", user);
                    return "user/user";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/user/{id}/my_books")
    public String findUserBooks(@PathVariable("id") Long id,
                                Model model,
                                Pageable pageable) {
        var userBooks = userBookService.findByUserId(id, pageable);
        model.addAttribute("userBooks", userBooks);
        return "user/myBooks";
    }
}

package com.qirsam.mini_library.http.controller;

import com.qirsam.mini_library.dto.AuthorCreateUpdateDto;
import com.qirsam.mini_library.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/authors")
@RequiredArgsConstructor
@Controller
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model) {
        return authorService.findById(id)
                .map(author -> {
                    model.addAttribute("author", author);
                    return "book/author";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping()
    public String findAll(Model model){
        model.addAttribute("authors", authorService.findAll());
        return "book/authors";
    }

    @GetMapping("/add-author")
    public String addAuthor(Model model, @ModelAttribute AuthorCreateUpdateDto author) {
        model.addAttribute("author", author);
        return "book/add-author";
    }

    @PostMapping("/add-author")
    public String create(@ModelAttribute @Validated AuthorCreateUpdateDto author,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("author", author);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/authors/add-author";
        }

        var authorReadDto = authorService.create(author);
        redirectAttributes.addAttribute("id", authorReadDto.getId());
        return "redirect:/authors/{id}";
    }
}

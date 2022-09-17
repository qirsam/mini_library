package com.qirsam.mini_library.http.controller;

import com.qirsam.mini_library.database.entity.filter.AuthorFilter;
import com.qirsam.mini_library.dto.AuthorCreateUpdateDto;
import com.qirsam.mini_library.dto.PageResponse;
import com.qirsam.mini_library.service.AuthorService;
import com.qirsam.mini_library.service.BookService;
import com.qirsam.mini_library.validation.groups.UpdateAction;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.groups.Default;

@RequestMapping("/authors")
@RequiredArgsConstructor
@Controller
public class AuthorController {

    private final AuthorService authorService;
    private final BookService bookService;

    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model) {
        return authorService.findById(id)
                .map(author -> {
                    model.addAttribute("author", author);
                    model.addAttribute("books", bookService.findAllByAuthorId(id));
                    return "book/author";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping()
    public String findAll(Model model, AuthorFilter filter, Pageable pageable) {
        var page = authorService.findAll(filter, pageable);
        model.addAttribute("authors", PageResponse.of(page));
        model.addAttribute("filter", filter);
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

    @GetMapping("/{id}/update")
    public String updateAuthorPage(@PathVariable Integer id, Model model) {
        return authorService.findById(id)
                .map(author -> {
                    model.addAttribute("author", author);
                    return "book/update-book";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Integer id,
                         @ModelAttribute @Validated({Default.class, UpdateAction.class}) AuthorCreateUpdateDto author,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("author", author);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/authors/{id}/update";
        }

        return authorService.update(id, author)
                .map(it -> "redirect:/authors/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) {
        if (!authorService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/authors";
    }
}

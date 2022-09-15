package com.qirsam.mini_library.http.controller;

import com.qirsam.mini_library.database.entity.filter.BookFilter;
import com.qirsam.mini_library.database.entity.library.Genre;
import com.qirsam.mini_library.database.entity.user.Status;
import com.qirsam.mini_library.dto.BookCreateUpdateDto;
import com.qirsam.mini_library.dto.PageResponse;
import com.qirsam.mini_library.service.AuthorService;
import com.qirsam.mini_library.service.BookService;
import com.qirsam.mini_library.service.UserBookService;
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

@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final AuthorService authorService;
    private final BookService bookService;
    private final UserBookService userBookService;

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
        return bookService.findById(id)
                .map(book -> {
                    model.addAttribute("book", book);
                    model.addAttribute("statuses", Status.values());
                    model.addAttribute("genres", Genre.values());
                    model.addAttribute("authors", authorService.findAll());
                    userBookService.findByPrincipalUserIdAndBookId(id)
                            .map(userBook -> model.addAttribute("userBook", userBook));

                    return "book/book";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/update")
    public String setStatus(@PathVariable Long id, @ModelAttribute Status status) {
        return userBookService.updateStatus(id, status)
                .map(it -> "redirect:/books/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public String findAll(Model model, BookFilter filter, Pageable pageable) {
        var page = bookService.findAll(filter, pageable);
        model.addAttribute("books", PageResponse.of(page));
        model.addAttribute("genres", Genre.values());
        model.addAttribute("filter", filter);
        return "book/books";
    }


    @GetMapping("/add-book")
    public String addBook(Model model, @ModelAttribute("book") BookCreateUpdateDto book) {
        model.addAttribute("book", book);
        model.addAttribute("genres", Genre.values());
        model.addAttribute("authors", authorService.findAll());
        return "book/add-book";
    }

    @PostMapping("/add-book")
    public String create(@ModelAttribute @Validated BookCreateUpdateDto book,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("book", book);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/books/add-book";
        }

        var bookReadDto = bookService.create(book);
        redirectAttributes.addAttribute("id", bookReadDto.getId());
        return "redirect:/books/{id}";
    }


}

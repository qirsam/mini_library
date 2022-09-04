package com.qirsam.mini_library.http.controller;

import com.qirsam.mini_library.database.entity.library.Genre;
import com.qirsam.mini_library.dto.BookCreateEditDto;
import com.qirsam.mini_library.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final AuthorService authorService;

    public String addBook(Model model, @ModelAttribute("book")BookCreateEditDto book) {
        model.addAttribute("book", book);
        model.addAttribute("genres", Genre.values());
        model.addAttribute("author", authorService.findAll());
        model.addAttribute("coAuthor", authorService.findAll());
        return "books/add-book";
    }
}

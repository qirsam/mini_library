package com.qirsam.mini_library.http.controller;

import com.qirsam.mini_library.database.entity.library.Genre;
import com.qirsam.mini_library.dto.BookCreateUpdateDto;
import com.qirsam.mini_library.service.AuthorService;
import com.qirsam.mini_library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final AuthorService authorService;
    private final BookService bookService;


    @GetMapping("/add-book")
    public String addBook(Model model, @ModelAttribute("book") BookCreateUpdateDto book) {
        model.addAttribute("book", book);
        model.addAttribute("genres", Genre.values());
        model.addAttribute("authors", authorService.findAll());
        return "books/add-book";
    }
    
    @PostMapping("/add-book")
    public String create(@ModelAttribute BookCreateUpdateDto book,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("book", book);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/books/add-book";
        }
        var bookReadDto = bookService.create(book);
        redirectAttributes.addAttribute("id", bookReadDto.getId());
        return "redirect:/books/{id}";
    }
}

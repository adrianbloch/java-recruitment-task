package com.example.javarecruitmenttask.controller;

import com.example.javarecruitmenttask.exception.InvalidAuthorException;
import com.example.javarecruitmenttask.exception.IsbnAlreadyExistException;
import com.example.javarecruitmenttask.model.Book;
import com.example.javarecruitmenttask.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
class BookController {

    private final BookService bookService;

    @Autowired
    BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/list")
    String viewBookList(Model model) {
        model.addAttribute("bookList", bookService.getAllBooks());

        return "list";
    }

    @GetMapping("/add")
    String addBookForm(Model model) {
        model.addAttribute("book", new Book());

        return "form";
    }

    @PostMapping("/add")
    String addBook(@ModelAttribute("book") @Valid Book book,
                          BindingResult bindingResult,
                          Model model) {

        if (bindingResult.hasErrors())
            return "form";

        try {
            bookService.addBook(book);
        } catch (InvalidAuthorException e) {
            model.addAttribute("authorExceptionMessage", e.getMessage());
            return "form";
        } catch (IsbnAlreadyExistException e) {
            model.addAttribute("isbnExceptionMessage", e.getMessage());
            return "form";
        }

        return "redirect:/list";
    }


}

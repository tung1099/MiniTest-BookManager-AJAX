package com.codegym.controller;

import com.codegym.model.Book;
import com.codegym.model.Category;
import com.codegym.service.book.IBookService;
import com.codegym.service.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private IBookService bookService;

    @Autowired
    private ICategoryService categoryService;

    @ModelAttribute("category")
    private Iterable<Category> categories(){
        return categoryService.findAll();
    }


    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        return new ResponseEntity<>(bookService.save(book), HttpStatus.CREATED);
    }
    @GetMapping("/list")
    public ModelAndView getAllBook() {
        ModelAndView modelAndView = new ModelAndView("list");
        modelAndView.addObject("books", bookService.findAll());
        return modelAndView;
    }

    @GetMapping
    public ResponseEntity<Iterable<Book>> allBooks() {
        return new ResponseEntity<>(bookService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable Long id) {
        Optional<Book> bookOptional = bookService.findById(id);
        if (!bookOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        bookService.remove(id);
        return new ResponseEntity<>(bookOptional.get(), HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book){
        Optional<Book> bookOptional = bookService.findById(id);
        if (!bookOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        book.setId(bookOptional.get().getId());
        return new ResponseEntity<>(bookService.save(book), HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findOne(@PathVariable Long id){
        return new ResponseEntity<>(bookService.findById(id).get(),HttpStatus.OK);
    }

}

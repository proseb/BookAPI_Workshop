package pl.coderslab.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.coderslab.model.Book;
import pl.coderslab.service.BookService;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/books")
public class ManageBookController {
    private final BookService bookService;

    public ManageBookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/all")
    public String showPosts(Model model) {
        List<Book> books = bookService.getBooks();
        model.addAttribute("books", books);
        return "books/all";
    }

    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String showAddForm(Model model){
        model.addAttribute("book",new Book());
        return "books/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveBook(@Valid Book book, BindingResult result) {
        if (result.hasErrors()) {
            return "books/add";
        }
        bookService.add(book);
        return "redirect:/admin/books/all";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String showEditForm(@PathVariable long id, Model model) {
        model.addAttribute("book", bookService.get(id));
        return "books/edit";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editBook(@Valid Book book, BindingResult result) {
        if (result.hasErrors()) {
            return "books/edit";
        }
        bookService.add(book);
        return "redirect:/admin/books/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(Model model, @PathVariable long id) {
        bookService.delete(id);
        return "redirect:/admin/books/all";
    }

    @GetMapping("/show/{id}")
    public String showBook(Model model, @PathVariable long id) {
        model.addAttribute("book", bookService.get(id).orElseThrow(EntityNotFoundException::new));
        return "books/show";
    }

}

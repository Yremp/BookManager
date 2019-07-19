package com.springboot.book.controller.modelcontroller;

import com.springboot.book.dao.BookDao;
import com.springboot.book.dao.RendDao;
import com.springboot.book.model.Book;
import com.springboot.book.model.Rend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class bookController {
    @Autowired
    private BookDao bookDao;
    RendDao rendDao;
    @RequestMapping("/book")
   public String queryAll(Model model){
        List<Book> books =bookDao.selectAll();
        model.addAttribute("books" ,books);
        return "booklist";
    }

    @GetMapping("/book/del/{id}")
    public String deleteBook(@PathVariable("id") Integer Id,Model model){
       bookDao.deleteBookById(Id);
        List<Book> books =bookDao.selectAll();
        model.addAttribute("books" ,books);
        return "redirect:/book";
    }

    @GetMapping("/book/predit/{id}")
    public String preUpdateBook(@PathVariable("id") Integer Id,Model model){
        Book book =bookDao.selectBookById(Id);
        model.addAttribute("book" ,book);
        System.out.println(book.toString());
        return "addbook";//复用代码
    }

    @PostMapping("/book/editok/{id}")
    public String UpdateBook(@PathVariable("id") Integer Id,
                             @RequestParam("name") String name,
                             @RequestParam("author") String author,
                             @RequestParam("price")String price,
                             @RequestParam("image") String image,
                             @RequestParam("description") String description){
        System.out.println(Id+name+author+price+image+description);
        Double Price = Double.parseDouble(price);
        bookDao.updateBookById(Id,name,author,Price,image,description);
        return "editsuccess";
    }
    @RequestMapping("/book/look/return")
    public String reBack()
    {
        return "redirect:/book";
    }

    @RequestMapping("/book/newbook")
    public String openAddBook(){
        return "addbook";
    }

    @PostMapping("/book/addbook")
    public String addBook(
            @RequestParam("name") String name,
            @RequestParam("author") String author,
            @RequestParam("price") String price,
            @RequestParam("image") String image,
            @RequestParam("description") String description)
    {
        Double Price =Double.parseDouble(price);
        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setPrice(Price);
        book.setImage(image);
        book.setDescription(description);
        bookDao.addBook(book);
        return "addsuccess";
    }
    @RequestMapping("/lookbook")
    public String queryAllbook(Model model){
        List<Book> books =bookDao.selectAll();
        model.addAttribute("books" ,books);
        return "lookbook";
    }

    @RequestMapping("/userinfo/rend/final/")
    public String userRend(Model model, HttpServletRequest request){
        Object BOOK_ID_OB=request.getAttribute("BOOK_ID");
        String BOOK_ID=BOOK_ID_OB.toString();
        String []BOOK_ID_ARRAY=BOOK_ID.split(",");
        List<Book> books=new ArrayList<Book>();
        for(int i=0;i<BOOK_ID_ARRAY.length;i++)
        {
            System.out.println(BOOK_ID_ARRAY[i]+";");
            int id;
          try{
              id =Integer.parseInt(BOOK_ID_ARRAY[i]);
          }catch (Exception e)
          {
              continue;
          }
            Book book=bookDao.selectBookById(id);

            try {
                books.add(book);
                System.out.println("书籍"+i);
            }catch (Exception e)
            {
                System.out.println("异常"+i);
            }
        }
        model.addAttribute("books",books);
        return "myrend";

    }

}

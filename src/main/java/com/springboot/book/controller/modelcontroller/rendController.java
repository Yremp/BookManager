package com.springboot.book.controller.modelcontroller;


import com.springboot.book.dao.BookDao;
import com.springboot.book.dao.RendDao;
import com.springboot.book.model.Book;
import com.springboot.book.model.Rend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class rendController {
    @Autowired

    private RendDao rendDao;

    @RequestMapping("/rend/{bookId}/{userId}")
    public String AddRend(@PathVariable("bookId")String bookid,
                          @PathVariable("userId")String userid,
                          Model model
    ){
        System.out.println("userID:"+userid+"bookId"+bookid);
        int userId=Integer.parseInt(userid);
        int bookId=Integer.parseInt(bookid);
        try{
            Rend rend=rendDao.selectByUserId(userId);
            if(rend==null){
                rendDao.InsertNewUser(userId,bookid);
            }
            else{
                String BOOK_ID=rend.getBookId();
                String [] BOOK_ARRAY=BOOK_ID.split(",");
                for(int i=0;i<BOOK_ARRAY.length;i++)
                {
                    if(BOOK_ARRAY[i].equals(bookid))
                    {
                        model.addAttribute("rend","书籍已经存在，添加失败");
                        return "redirect:/index.html";
                    }
                }
                    BOOK_ID=BOOK_ID+","+bookid;

                rendDao.updateByUserId(BOOK_ID,userId);
                model.addAttribute("rend","添加成功");
                return "redirect:/index.html";
            }
        }catch (Exception e){
            model.addAttribute("rend","未知错误，添加失败");
            return "redirect:/index.html";
        }
        return "redirect:/index.html";

    }

    @RequestMapping("/userinfo/rend/{userId}")
    public String userRend(@PathVariable("userId")String userId, Model model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userID = Integer.parseInt(userId);
        Rend rend = rendDao.selectRendBookId(userID);
       try {
           if(rend.getBookId().length()==0)
           {
               model.addAttribute("ERROR","你的书架是空的！");
               return "errorpage";
           }
           String BOOK_ID = rend.getBookId();
           request.setAttribute("BOOK_ID", BOOK_ID);
           request.getRequestDispatcher("/userinfo/rend/final/").forward(request, response);
       }catch (Exception e){
           model.addAttribute("ERROR","你的书架是空的！");
       }
       return "errorpage";
    }

    @RequestMapping("/userinfo/del/{userid}/{bookid}")
    public String delrRendbook(@PathVariable("userid")String userid,@PathVariable("bookid")String bookid,
                               HttpServletRequest request){
        int userId=Integer.parseInt(userid);
        int bookId=Integer.parseInt(bookid);
        Rend rend = rendDao.selectRendBookId(userId);
        String Book=null;
        Book =rend.getBookId();
        String NEW_BOOKID="";
        String[]BOOK_ID_ARRAY=Book.split(",");
        for(int i=0;i<BOOK_ID_ARRAY.length;i++){
            if(!BOOK_ID_ARRAY[i].equals(bookid))
            {
                if(i==0){
                    NEW_BOOKID=NEW_BOOKID+BOOK_ID_ARRAY[i];
                }
                else
                {
                    NEW_BOOKID=NEW_BOOKID+","+BOOK_ID_ARRAY[i];
                }
            }
            else {
                continue;
            }
        }
        rendDao.updateByUserId(NEW_BOOKID,userId);

        return "redirect:/userinfo/rend/"+userid;

    }
}

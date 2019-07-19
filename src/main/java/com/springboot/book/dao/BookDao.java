package com.springboot.book.dao;

import com.springboot.book.model.Book;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.ibatis.annotations.*;
import org.springframework.context.annotation.Bean;

import java.util.List;

@Mapper
public interface BookDao {
    String table_name="book";
    String insert_filed="name,author,price,image,description";
    String select_field = " id, status, " + insert_filed;
//查询全部书籍
    @Select({"select * from",table_name})
    List<Book> selectAll();
//根据name查找id
    @Select({"select id from",table_name,"where name=#{name}"})
    Book selectBookByName(String Name);
//根据id查找全部信息
    @Select({"select * from",table_name,"where id=#{id}"})
    Book selectBookById(@Param("id") int id);
//根据id删除指定书籍
    @Delete({"delete ","from",table_name,"where id=#{id}"})
    void deleteBookById(int id);
//根据指定的书名删除书籍
    @Delete({"delete ","from",table_name,"where name=#{name}"})
    void deleteBookByName(String name);
//添加新的书籍
    @Insert({"insert into",table_name,"(",insert_filed,")","values(#{name},#{author},#{price},#{image},#{description})"})
    void addBook(Book book);
    @Update({"update ", table_name, " set name=#{name},author=#{author},price=#{price},image=#{image},description=#{description} where id=#{id}"})
    void updateBookById(@Param("id") int id,@Param("name") String name,@Param("author") String author,
                        @Param("price") Double price,@Param("image") String image,@Param("description") String description);
}


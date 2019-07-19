package com.springboot.book.dao;

import com.springboot.book.model.Rend;
import org.apache.ibatis.annotations.*;

import java.sql.Array;
import java.util.List;

@Mapper
public interface RendDao {
    @Select({"select * from rend"})
    List<Rend> allRend();

    @Select({"select * from rend where userId =#{id}"})
    Rend selectByUserId(@Param("id")int id);

    @Update({"update rend set bookId=#{bookId} where userId=#{userId}"})
    void  updateByUserId(@Param("bookId")String bookId,@Param("userId")int userId);

    @Insert({"insert into rend (userId,bookId) values(#{userId},#{bookId})"})
    void InsertNewUser(@Param("userId")int userId,@Param("bookId")String bookId);

    @Select({"select * from rend where userId=#{userId}"})
    Rend selectRendBookId(@Param("userId")int userId);

}

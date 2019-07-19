package com.springboot.book.dao;

import com.springboot.book.model.User;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserDao {
    String table_name = "user";
    String insert_filed = "name,email,password,role";
    String select_field = " id, " + insert_filed + ",role";

    @Select({"select * from ", table_name})
    List<User> selectAll();

    //    添加user


    @Insert({"insert into",table_name,"(",insert_filed,")","values(#{name},#{email},#{password},'user')"})
    void addUser(User user);
    //    根据id查询用户
    @Select({"select", select_field, "from", table_name, "where id=#{id}"})
    User selectById(int id);

    //    根据用户姓名查询用户信息
    @Select({"select", select_field, "from", table_name, "where name=#{name}"})
    User selectByName(String name);

    //    根据用户邮箱查询用户信息
    @Select({"select * from", table_name, "where email=#{email}"})
    User selectByEmail(String email);
    //根据id删除指定书籍
    @Delete({"delete ","from",table_name,"where id=#{id}"})
    void deleteUserById(int id);
    //    根据用户id更新用户信息
    @Update({"update  user set name=#{name},email=#{email},password=#{password} where id=#{id}"})
    void updateById(@Param("id")int id,@Param("name")String name,@Param("email")String email,@Param("password")String password);
}

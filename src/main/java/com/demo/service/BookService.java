package com.demo.service;


import com.demo.domain.Book;
import com.demo.domain.User;
import com.demo.entity.PageResult;

public interface BookService {
    /**
     *
     * @param pageNum //当前页码
     * @param pageSize //每页显示的数量
     * @return
     */
    PageResult selectNewBook(Integer pageNum, Integer pageSize);
    // 查询新上架图书
    PageResult search( Integer pageNum, Integer pageSize);
     Book findBookById(String id);
     //借阅

    Integer borrowBook(Book book);
    //编辑
    Integer editBook(Book book);
    //查询图书
    PageResult search(Book book, Integer pageNum, Integer pageSize);
    //查询借阅图书
    PageResult searchBorrowed(Book book, User user , Integer pageNum, Integer pageSize);
    //添加图书
    Integer addBook(Book book);
    // 归还图书复用BookMapper接口的editBook方法
    Boolean returnBook(String id,User user);

    // 确认归还复用BookMapper接口的editBook方法
    Integer returnConfirm(String id);
    //图书借阅记录

}

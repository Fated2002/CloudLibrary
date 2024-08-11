package com.demo.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.demo.domain.Book;
import com.demo.domain.Record;
import com.demo.domain.User;
import com.demo.entity.PageResult;
import com.demo.mapper.BookMapper;
import com.demo.service.BookService;
import com.demo.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Transactional//开启事务
public class BookServiceImpl implements BookService {
    // 注入BookMapper对象
    @Autowired(required = false)
    private BookMapper bookMapper;
    // 注入RecordService对象
    @Autowired
    private RecordService recordService;

    /**
     * 首页部分
     *
     * @param pageNum  //当前页码
     * @param pageSize //每页显示的数量
     * @return
     */
    @Override
    public PageResult selectNewBook(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<Book> books = bookMapper.selectNewBooks();
        return new PageResult(books.getTotal(), books.getResult());
    }

    @Override
    public PageResult search(Integer pageNum, Integer pageSize) {
        // 设置分页查询的参数 开始分页
        PageHelper.startPage(pageNum, pageSize);
        // 将查询到的结果集 给Page
        Page<Book> page = bookMapper.selectNewBooks();
        // 创建PageResult对象 将总和 和 查询到的结果集封装起来
        return new PageResult(page.getTotal(), page.getResult());
    }

    //id查询
    @Override
    public Book findBookById(String id) {
        return bookMapper.findBookById(id);
    }

    @Override
    public Integer borrowBook(Book book) {
        //设置日期格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // 获取时间并设置当天为借阅时间
        book.setBorrowTime(dateFormat.format(new Date()));

        // 设置所借阅的图书状态为借阅中
        book.setStatus("1");

        // 更新图书信息
        return bookMapper.editBook(book);
    }

    /**
     * 图书借阅部分
     *
     * @param book
     * @return
     */
//编辑图书
    @Override
    public Integer editBook(Book book) {
        return bookMapper.editBook(book);
    }

    //图书借阅页面图书查询
    @Override
    public PageResult search(Book book, Integer pageNum, Integer pageSize) {
        // 设置分页查询
        PageHelper.startPage(pageNum, pageSize);

        // 封装结果集
        Page<Book> books = bookMapper.searchBooks(book);

        // 返回结果集给分页插件
        return new PageResult(books.getTotal(), books.getResult());
    }

    //图书借阅查询
    @Override
    public PageResult searchBorrowed(Book book, User user, Integer pageNum, Integer pageSize) {
        // 设置分页查询的参数
        PageHelper.startPage(pageNum, pageSize);

        // 将当前登录的用户获取
        book.setBorrower(user.getName());

        Page<Book> page = null;
        // 判断是否为管理员
        if ("ADMIN".equals(user.getRole())) {

            // 管理员 --> 可以查询当前的借阅图书以及所有的归还中的图书
            page = bookMapper.selectBorrowed(book);
        } else {

            // 普通人 --> 可以查询当前的借阅图书以及当前的归还中的图书和未归还的图书
            page = bookMapper.selectMyBorrowed(book);

        }

        // 返回封装的结果集 后面交给前端处理
        return new PageResult(page.getTotal(), page.getResult());
    }

    //添加图书
    @Override
    public Integer addBook(Book book) {
        // 设置上架时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String uploadTime = format.format(new Date());
        book.setUploadTime(uploadTime);
        return bookMapper.addBook(book);
    }

    @Override
    public Boolean returnBook(String id, User user) {
        // 根据图书id查询图书的完整信息
        Book bookById = this.findBookById(id);

        // 检查当前登录人员和图书借阅者是否为同一个人
        boolean flag = bookById.getBorrower().equals(user.getName());

        // 判断是否为空同一个人
        if (flag) {

            // 等于true 就说明是同一个人 修改图书的状态 改成归还中 等待管理员确认
            bookById.setStatus("2");
            bookMapper.editBook(bookById);
        }

        return flag;
    }

    /**
     * 确认归还
     *
     * @param id 待归还的图书id
     */
    @Override
    public Integer returnConfirm(String id) {

        // 根据图书的id查询图书的完整信息
        Book bookById = this.findBookById(id);

        // 获取归还图书的借阅信息
        String borrower = bookById.getBorrower();
        String borrowTime = bookById.getBorrowTime();

        // 将图书的借阅状态修改成可借阅
        bookById.setStatus("0");

        // 清除当前图书的借阅人信息
        bookById.setBorrower("");

        // 清除当前图书的借阅时间信息
        bookById.setBorrowTime("");

        // 清除当前图书的预计归还时间信息
        bookById.setReturnTime("");

        Integer book = bookMapper.editBook(bookById);

        if (book > 0) {
            // 当管理员确认无误归还图书 就将图书记录添加到Record中
            Record record = new Record();

            // 设置借阅时间
            record.setId(bookById.getId());
            record.setBookIsbn(bookById.getIsbn());
            record.setBookname(bookById.getName());
            record.setBorrowTime(borrowTime);
            record.setBorrower(borrower);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String returnTime = format.format(new Date());
            record.setRemandTime(returnTime);
            recordService.addRecord(record);
        }
        return book;
    }
}

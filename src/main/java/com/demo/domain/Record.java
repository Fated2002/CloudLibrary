package com.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Record implements Serializable {
    // 图书借阅的id
    private Integer id;
    // 借阅的图书的名称
    private String bookname;
    // 借阅图书的ISBN
    private String bookIsbn;
    // 图书的借阅人
    private String borrower;
    // 图书借阅的时间
    private String borrowTime;
    // 图书的归还时间
    private String remandTime;
}

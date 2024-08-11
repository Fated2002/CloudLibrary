package com.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private Integer id;                      // 图书编号
    private String name;                  // 图书名称
    private String isbn, press;// 图书标准ISBN编号、图书出版
    private String author;                 // 图书作者
    private Integer pagination;        // 图书页数
    private Double price;                 // 图书价格
    private String uploadTime,  status; // 图书上架时间、图书状态
    private String borrower, borrowTime;// 图书借阅人、图书借阅时间
    private String returnTime; //图书预计归还时间
}

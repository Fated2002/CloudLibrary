package com.demo.controller;

import com.demo.domain.Record;
import com.demo.domain.User;
import com.demo.entity.PageResult;
import com.demo.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/record")
public class RecordController {
    @Autowired
    // 注入RecordService对象
    private RecordService recordService;
    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @RequestMapping(value = "/searchRecords")
    public ModelAndView searchRecords(Record record, HttpServletRequest request, Integer pageNum, Integer pageSize){

        ModelAndView modelAndView = new ModelAndView();

        // 判断页码以及页面长度是否为空 为空就初始化 (该页码的初始值处于前端)
        if (pageNum==null){
            pageNum=1;
        }
        if (pageSize==null){
            pageSize=10;
        }

        // 获取当前登录用户
        User user=(User) request.getSession().getAttribute("USER_SESSION");

        // 封装查询到的数据
        PageResult pageResult = recordService.searchRecords(record,user,pageNum,pageSize);

        // 设置跳转页面
        modelAndView.setViewName("record");

        // 查询到的数据
        modelAndView.addObject("pageResult",pageResult);

        // 页码数据回显到页面
        modelAndView.addObject("pageNum",pageNum);

        // 查询的数据参数回显到搜索框
        modelAndView.addObject("search",record);

        // 查询到数据之后 继续返回到该页面发送请求
        modelAndView.addObject("gourl",request.getRequestURI());

        return modelAndView;
    }

}

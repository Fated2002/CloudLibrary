package com.demo.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.demo.domain.Record;
import com.demo.domain.User;
import com.demo.entity.PageResult;
import com.demo.mapper.RecordMapper;
import com.demo.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordServiceImpl implements RecordService {
    @Autowired(required = false)
private RecordMapper recordMapper ;
    @Override
    public Integer addRecord(Record record) {

        return recordMapper.addRecord(record);
    }

    @Override
    public PageResult searchRecords(Record record, User user, Integer pageNum, Integer pageSize) {
        // 设置分页查询
        PageHelper.startPage(pageNum,pageSize);

        // 判断是否为管理员
        if (!"ADMIN".equals(user.getRole())){
            /**
             * 如果不是管理员 就将查询条件中的 借阅人设置为当前登录用户
             * 如果是管理员 那么就不会运行该if 反之 若为普通用户则运行该if
             * 将条件中的借阅人设置为当前登录人
             */
            record.setBorrower(user.getName());
        }
        Page<Record> page = recordMapper.searchRecords(record);
        return new PageResult(page.getTotal(),page.getResult());
    }
}

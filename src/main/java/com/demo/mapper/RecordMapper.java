package com.demo.mapper;

import com.github.pagehelper.Page;
import com.demo.domain.Record;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface RecordMapper {
    /**
     * 新增借阅记录
     *
     * @param record 记录
     */
    Integer addRecord(Record record);


    /**
     * 查询借阅记录
     * @param record 记
     */
    @Select("<script>\n" +
            "        select * from record\n" +
            "        <where>\n" +
            "            <if test=\"borrower!=null\">\n" +
            "                and record_borrower like concat('%',#{borrower},'%')\n" +
            "            </if>\n" +
            "            <if test=\"bookname!=null\">\n" +
            "                and record_bookname like concat('%',#{bookname},'%')\n" +
            "            </if>\n" +
            "        </where>\n" +
            "        order by record_remandtime desc\n" +
            "</script>")
    @Results(id="RecordMap",value = {
            @Result(id = true, column = "record-id", property = "id"),
            @Result(column = "record_bookname", property = "bookname"),
            @Result(column = "record_bookisbn", property = "bookIsbn"),
            @Result(column = "record_borrower", property = "borrower"),
            @Result(column = "record_borrowtime", property = "borrowTime"),
            @Result(column = "record_remandtime", property = "remandTime")

    })
    Page<Record> searchRecords(Record record);
}

package com.frye.trading.pojo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Complaint {

    private String complaintId;
    private String orderId;
    private String detail;
    private String createTime;
    private Date finishTime;
    private String state;
    private String Result;


    /**
     * 重载setFinishTime函数
     * @param finishTime String类型
     * @throws ParseException 解析异常
     */
    public void setFinishTime(String finishTime) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        this.finishTime = format.parse(finishTime);
    }
}

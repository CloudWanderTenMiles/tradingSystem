package com.frye.trading.utils;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.frye.trading.pojo.dto.ResultMessage;

//封装发送的消息内容
public class MessageUtils {
    public static String getMessage(boolean isSystemMessage,String fromUser,Object message){
        try{
            ResultMessage resultMessage = new ResultMessage();
            resultMessage.setSystem(isSystemMessage);
            resultMessage.setMessage(message);
            if(fromUser != null){
                resultMessage.setFromUser(fromUser);
            }
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(resultMessage);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return null;
    }
}

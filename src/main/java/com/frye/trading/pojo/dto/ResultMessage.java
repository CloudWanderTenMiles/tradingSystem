package com.frye.trading.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultMessage {

    private boolean isSystem;
    private String fromUser;
    private Object message;

}

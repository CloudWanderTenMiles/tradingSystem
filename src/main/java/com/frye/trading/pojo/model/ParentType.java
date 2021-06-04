package com.frye.trading.pojo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParentType {

    private int parentTypeId;
    private String parentTypeName;
    private String icon;
}

package com.frye.trading.pojo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Type {
    private int typeId;
    private String typeName;
    private int parentTypeId;
    private String parentTypeName;
    private int sort;
}

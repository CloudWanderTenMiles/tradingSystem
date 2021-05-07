package com.frye.trading;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CONSTANT {

    public static Date ORIGIN_TIME;

    static {
        try {
            ORIGIN_TIME = new SimpleDateFormat("yyyy-MM-dd").parse("2021-5-7");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}

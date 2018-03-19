package com.go.junitTest;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by eriz on 2018/3/17.
 */
public class test {


    @Test
    public void test() throws ParseException {

        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");

        Date date = myFormatter.parse("2018-03-17");

        Date mydate = myFormatter.parse("1970-01-01");

        long day = (date.getTime() - mydate.getTime());

        System.out.println(date.getTime() + "当前时间" + date);

        System.out.println(mydate.getTime() + "过去时间" + mydate);

        System.out.println(day);


//        Calendar cal = Calendar.getInstance();
//        cal.set(1970, 0, 01);
//
//        System.out.println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(cal.getTime()));
    }
}

package com.cxl.redis;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        LinkedList<String> list= new LinkedList<>();
//        list.add()
        Map<String,String> map=new HashMap<>();
        String put = map.put("1", "1");
        System.out.println(map.get("1"));

    }
}

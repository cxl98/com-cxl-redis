package com.cxl.redis;

import java.util.*;

public class BaseData {
    private static BaseData ourInstance = new BaseData();

    public static BaseData getInstance() {
        return ourInstance;
    }

    private BaseData() {
    }

    public Map<String,String> string= new HashMap<>();
    public Map<String, List<String>>  list=new HashMap<>();
    public Map<String,HashMap<String,String>> map=new HashMap<>();
    public Map<String, Set<String>>set=new HashMap<>();
//    private Map<String, LinkedList<String>> zset=

}

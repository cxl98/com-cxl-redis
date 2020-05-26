package com.cxl.redis.command;

import com.cxl.redis.BaseData;
import com.cxl.redis.procotol.RedisEncode;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GetCommand implements Command {
    private List<String> params;
    @Override
    public void params(List<String> args) {
        this.params=args;
    }

    @Override
    public void run(BufferedWriter os) throws IOException {
        if (1==params.size()){
            String key=params.remove(0);
            Map<String, String> string = BaseData.getInstance().string;
            String value = string.get(key);
            if (null!=value){
                RedisEncode.writeInteger(os,value);
            }
        }else{
            RedisEncode.writeError(os,"ERR wrong number of arguments for"+ "'"+"get"+"'" +"command");
        }
    }
}

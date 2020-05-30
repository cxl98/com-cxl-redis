package com.cxl.redis.command;

import com.cxl.redis.BaseData;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

public class LPOPCommand implements Command{
    private List<String> params;
    @Override
    public void params(List<String> args) {
        this.params=args;
    }

    @Override
    public void run(BufferedWriter os) throws IOException {
        if (1==params.size()){
            String key = params.remove(0);
            List<String> list = BaseData.getInstance().getList(key);
        }
    }
}

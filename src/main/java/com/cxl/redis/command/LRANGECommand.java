package com.cxl.redis.command;

import com.cxl.redis.BaseData;
import com.cxl.redis.procotol.RedisEncode;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LRANGECommand implements Command {
    private List<String> params;
    private int start;
    private int end;

    @Override
    public void params(List<String> args) {
        this.params = args;
    }

    @Override
    public void run(BufferedWriter os) {
        if (3 == params.size()) {
            String key = params.remove(0);
            List<String> baseData = BaseData.getInstance().getList(key);
            start = Integer.parseInt(params.remove(0));
            end = Integer.parseInt(params.remove(0));
            List<String> list = new ArrayList<>();
            try {
                if (start >= 0 && start < baseData.size()) {
                    if (end < 0) {
                        end = baseData.size() + end;
                        for (int i = end; i < baseData.size(); i++) {
                            list.add(baseData.get(i));
                        }
                        RedisEncode.writeArray(os, list);
                    } else {
                        if (end >= baseData.size()) {
                            RedisEncode.writeArray(os, baseData);
                        } else {
                            for (int i = start; i < end + 1; i++) {
                                list.add(baseData.get(i));
                            }
                            RedisEncode.writeArray(os, list);
                        }
                    }
                } else {
                    for (int i = baseData.size() + start; i < baseData.size(); i++) {
                        list.add(baseData.get(i));
                    }
                    RedisEncode.writeArray(os, baseData.subList(baseData.size() + start, baseData.size()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            try {
                RedisEncode.writeError(os, "ERR wrong number of arguments for 'lrange' command");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

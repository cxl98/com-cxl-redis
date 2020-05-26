package com.cxl.redis.procotol;

import com.cxl.redis.command.Command;
import com.cxl.redis.constant.Constant;
import com.cxl.redis.exception.RedisException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RedisDecode {
    private AnalysisInputStream analysis;
    private BufferedWriter out;

    public RedisDecode(BufferedReader is, BufferedWriter out) {
        this.analysis = new AnalysisInputStream(is);
        this.out = out;
    }

    public Command command() throws IOException {
        Object obj = analysis.process();
        List<String> list = (List<String>) obj;
        String params = list.remove(0);
        String className;
        Class<?> cls = null;
        Command command = null;
        if (null == params) {
            RedisEncode.writeError(out, "ERR unknown command" + "'" + params + "'");
            throw new RedisException("服务端异常");
        } else {
            className = String.format(Constant.COMMAND, params.substring(0,1).toUpperCase()+params.substring(1));
        }
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (null == cls || !Command.class.isAssignableFrom(cls)) {
            RedisEncode.writeError(out, "unknown command,Please try again!!");
        } else {
            try {
                command = (Command) cls.newInstance();
                command.params(list);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return command;
    }


    private class AnalysisInputStream {
        private BufferedReader is;

        public AnalysisInputStream(BufferedReader is) {
            this.is = is;
        }

        public Object process() throws IOException {
            String line = is.readLine();
            if (line.startsWith("*")) {
                String len = line.substring(1);
                return processArray(len);
            } else if (line.startsWith("$")) {
                return processBulkString();
            }
            return null;
        }

        private String processBulkString() throws IOException {
            String s = is.readLine();
            if (null == s || "".equals(s)) {
                return null;
            }
            return s;
        }

        //        public long readInteger() throws IOException {
//            int read = is.read();
//            if (-1==read){
//                throw new RedisException(">>>命令不能为空");
//            }
//            StringBuffer sb=new StringBuffer();
//            boolean falg=false;
//            if ('-'==read){
//                falg=true;
//            }else{
//                sb.append((char) read);
//            }
//            return 0;
//        }
        private List<String> processArray(String size) throws IOException {
            int len = Integer.parseInt(size);
            List<String> list = new ArrayList<>();
            for (int i = 0; i < len; i++) {
                String process = (String) process();
                list.add(process);
            }
            return list;
        }

    }
}

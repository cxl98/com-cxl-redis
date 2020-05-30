package com.cxl.redis.procotol;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

public class RedisEncode {
    public static void writeInteger(BufferedWriter writer, String len) throws IOException {
        writer.write(':');
        writer.write(len);
        writer.write("\r\n");
        writer.flush();
    }

    public static void writeString(BufferedWriter writer) throws IOException {
        writer.write('+');
        writer.write("OK");
        writer.write("\r\n");
        writer.flush();
    }

    public static void writeBulkString(BufferedWriter writer, String str) throws IOException {
        writer.write('$');
        writer.write(String.valueOf(str.length()));
        writer.write("\r\n");
        writer.write(str);
        writer.write("\r\n");
        writer.flush();
    }

    public static void writeArray(BufferedWriter writer, List<?> list) throws IOException {
        writer.write('*');
        writer.write(String.valueOf(list.size()));
        writer.write("\r\n");
        writer.flush();
        for (Object item : list)
            if (item instanceof Integer) {
                writeInteger(writer, (String) item);
            } else if (item instanceof Long) {
                writeInteger(writer, (String) item);
            } else if (item instanceof String) {
                writeBulkString(writer, (String) item);
            } else if (item instanceof List<?>) {
                writeArray(writer, (List<?>) item);
            }
    }

    public static void writeError(BufferedWriter writer, String message) throws IOException {
        writer.write('-');
        writer.write(message);
        writer.write("\r\n");
        writer.flush();
    }
}

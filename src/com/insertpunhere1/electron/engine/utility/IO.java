package com.insertpunhere1.electron.engine.utility;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IO {
    public static String string(String file) throws IOException {
        byte[] string_bytes = Files.readAllBytes(Paths.get(file));

        return new String(string_bytes, Charset.forName("UTF-8"));
    }

    public static List<String> list(String file) throws IOException {
        byte[] string_bytes = Files.readAllBytes(Paths.get(file));

        String string = new String(string_bytes, Charset.forName("UTF-8"));

        String[] string_array = string.split("\n");

        List<String> string_list = new ArrayList<>();

        Collections.addAll(string_list, string_array);

        return string_list;
    }
}

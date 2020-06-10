package com.ghl.lib_dirty.hook;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regular {

    public static void main(String args[]) {
        String str = "aaa";
         str = "顾红亮";
        String pattern = "/^[\\u4E00-\\u9FFF\\u3400-\\u4DBF\\uF900-\\uFAFF]+";

//        pattern = "^(WS[A-Z0-9]*)((\r\n|)[A-Z0-9]+){3}(\r\n|)(SIGMET).*";
//        pattern = "/^1((2[7])|(3[0-9])|(4[5-9])|(5[0-9])|(6[124567])|(7[0-8])|(8[0-9])|(9[0-9]))\\d{8}$/";
//        pattern="[a-zA-Z\\\\d]*";
//        pattern="[\\u4E00-\\u9FA5]+";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        System.out.println(m.matches());
    }

}

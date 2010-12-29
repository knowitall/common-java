package edu.washington.cs.knowitall.commonlib;

import com.google.common.base.Function;

public class StringFunctions {
    public static final Function<String, String> toLowerCase = 
        new Function<String, String>() {
            @Override
            public String apply(String string) {
                return string.toLowerCase();
            }
    };
}

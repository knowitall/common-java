package edu.washington.cs.knowitall.commonlib;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Joiner;

public class StringUtils {
    public static List<String> splitInto(String string, Pattern pattern) {
        Matcher matcher = pattern.matcher(string);
        
        List<String> parts = new ArrayList<String>();
        
        int i = 0;
        while (matcher.find()) {
            for (; i < matcher.start(); i++) {
                if (i < string.length() && string.charAt(i) != ' ') {
                    throw new IllegalArgumentException("Could not split string into specified pattern.  Found matches '" + Joiner.on(", ").join(parts) + "' and then '" + string.charAt(i) + "' found between matches.");
                }
            }
            
            parts.add(matcher.group(0));
            
            i = matcher.end();
        }
        
        return parts;
    }
}

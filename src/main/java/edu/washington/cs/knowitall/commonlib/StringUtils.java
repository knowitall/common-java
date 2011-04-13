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
    
    public static String indent(String string, String indent) {
        final Pattern pattern = Pattern.compile("^", Pattern.MULTILINE);
        return pattern.matcher(string).replaceAll(indent);
    }
    
    public static String indent(String string) {
        return indent(string, "    ");
    }
    
    public static List<String> tokenize(String string, Pattern[] patterns) {
        final Pattern whitespace = Pattern.compile("\\s+");
        return tokenize(string, patterns, whitespace);
    }
    
    public static List<String> tokenize(String string, Pattern[] patterns, Pattern ignore) {
        List<String> tokens = new ArrayList<String>(string.length());
        
        int start = 0;
        while (start < string.length()) {
            Matcher match = null;
            
            // ignore
            if ((match = ignore.matcher(string).region(start, string.length())).lookingAt()) {
                start = match.end();
                continue;
            }
            
            boolean matched = false;
            for (Pattern p : patterns) {
                if ((match = p.matcher(string).region(start, string.length())).lookingAt()) {
                    tokens.add(match.group(0));
                    start = match.end();
                    
                    matched = true;
                    break;
                }
            }
            
            // we did not find any matches, throw exception
            if (!matched) {
                throw new IllegalArgumentException("Un-tokenizable string: '" + string.substring(start) + "'");
            }
        }
        
        return tokens;
    }
    
    public static int indexOfClose(String string, int start, char open, char close) {
        start--;
        
        int count = 0;
        do {
            start++;
            
            // we hit the end
            if (start >= string.length()) {
                return -1;
            }
            
            char c = string.charAt(start);
            
            // we hit an open/close
            if (c == open) {
                count++;
            } else if (c == close) {
                count--;
            }
            
        } while (count > 0); 
        
        return start;
    }
}

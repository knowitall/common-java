package edu.washington.cs.knowit.commonlib.sql;

import java.util.regex.*;

public class TypeMeta {
    private String type;
    private Integer size;

    public TypeMeta(String type) {
        this(type, null);
    }

    public TypeMeta(String type, int size) {
        this(type, (Integer)size);
    }

    private TypeMeta(String type, Integer size) {
        this.type = type;
        this.size = size;
    }

    public String type() {
        return type;
    }

    public Integer size() {
        return size;
    }

    public String encode() {
        return this.toSqlString();
    }

    private static Pattern typePattern = Pattern.compile("(\\w*)\\((\\d*)\\)");
    public static TypeMeta decode(String string) {
        Matcher matcher = typePattern.matcher(string);
        if (matcher.matches()) {
            return new TypeMeta(matcher.group(1), 
                    Integer.valueOf(matcher.group(2)));
        }
        else {
            return new TypeMeta(string);
        }
    }

    public String toSqlString() {
        if (size != null) {
            return this.type + "(" + size + ")";
        }
        else {
            return this.type;
        }
    }

    public String toString() {
        return toSqlString();
    }
}

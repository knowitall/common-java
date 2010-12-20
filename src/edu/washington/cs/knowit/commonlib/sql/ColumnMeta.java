package edu.washington.cs.knowit.commonlib.sql;

public class ColumnMeta {
    private String name;
    private TypeMeta type;
    private String other;

    public ColumnMeta(String name, TypeMeta type) {
        this(name, type, "");
    }

    public ColumnMeta(String name, TypeMeta type, String other) {
        this.name = name;
        this.type = type;
        this.other = other;
    }

    public String name() {
        return this.name;
    }

    public TypeMeta type() {
        return this.type;
    }

    public String other() {
        return this.other;
    }

    public String encode() {
        return name + "," + type.encode() + "," + other;
    }

    public static ColumnMeta decode(String string) {
        String[] parts = string.split(",");
        if (parts.length == 3) {
            return new ColumnMeta(parts[0], TypeMeta.decode(parts[1]), 
                    parts[2]);
        }
        else {
            return new ColumnMeta(parts[0], TypeMeta.decode(parts[1]));
        }
    }

    public String toSqlString() {
        return name + " " + type.toSqlString() + " " + other;
    }

    public String toString() {
        return name + " " + type.toSqlString() + " " + other;
    }
}

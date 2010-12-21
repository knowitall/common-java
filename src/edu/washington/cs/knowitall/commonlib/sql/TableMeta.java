package edu.washington.cs.knowitall.commonlib.sql;

import java.sql.*;
import java.util.regex.*;

public class TableMeta {
    private String name;
    private ColumnMeta[] columns;

    public TableMeta(String name, ColumnMeta[] columns) {
        this.name = name;
        this.columns = columns;
    }

    public String name() {
        return this.name;
    }

    public ColumnMeta column(int i) {
        return columns[i];
    }

    public ColumnMeta column(String name) {
        for (ColumnMeta column : this.columns) {
            if (column.name().equals(name)) {
                return column;
            }
        }

        throw new IllegalArgumentException("Column not found: " + name);
    }

    public int columnCount() {
        return columns.length;
    }

    public String encode() {
        return this.name + "{" + this.encodeColumns() + "}";
    }

    public String toString() {
        return this.encode();
    }

    private static Pattern tablePattern = Pattern.compile("(\\w*)\\{(.*)\\}");
    public static TableMeta decode(String s) {
        Matcher m = tablePattern.matcher(s);
        if (m.matches()) {
            String name = m.group(1);
            String columnsString = m.group(2);
            return new TableMeta(name, decodeColumns(columnsString));
        }
        else {
            throw new IllegalArgumentException("Invalid table specified: " +
                    s);
        }
    }

    private static ColumnMeta[] decodeColumns(String string) {
        String[] parts = string.split(";");

        ColumnMeta[] columns = new ColumnMeta[parts.length];

        for (int i = 0; i < parts.length; i++) {
            columns[i] = ColumnMeta.decode(parts[i]);
        }

        return columns;
    }

    private String encodeColumns() {
        StringBuilder builder = new StringBuilder();
        if (columns.length > 0) {
            for (int i = 0; i < columns.length; i++) {
                if (i != 0) {
                    builder.append(";");
                }

                builder.append(columns[i].encode());
            }
        }

        return builder.toString();
    }

    public String sqlDropString() {
        return "DROP TABLE IF EXISTS " + this.name;
    }

    public String sqlCreateString() {
        StringBuilder builder = new StringBuilder("CREATE TABLE " + name);
        if (columns.length > 0) {
            builder.append(" (");

            for (int i = 0; i < columns.length; i++) {
                if (i != 0) {
                    builder.append(", ");
                }
                builder.append(columns[i].toSqlString());
            }

            builder.append(")");
        }

        return builder.toString();
    }

    public String sqlLoadDataString(String path, String terminator, int ignore) 
        throws SQLException {

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < columns.length; i++) {
            if (i != 0) {
                builder.append(", ");
            }
            builder.append(columns[i].name());
        }
        String columnNames = builder.toString();

        return "LOAD DATA LOCAL INFILE \"" 
            + path + "\"\n" +
            "REPLACE INTO TABLE " + this.name + "\n" +
            "FIELDS TERMINATED BY '" + terminator + "'\n" +
            (ignore > 0 ? "IGNORE " + ignore + " LINES\n" : "") +
            "(" + columnNames + ")";
    }

    private void execute(Connection conn, String query) throws SQLException {
        Statement state;

        // create the tables
        state = null;
        try {
            state = conn.createStatement();
            state.execute(query);
        }
        finally {
            if (state != null) {
                state.close();
            }
        }
    }

    public void create(Connection conn) throws SQLException {
        this.execute(conn, this.sqlCreateString());
    }

    public void drop(Connection conn) throws SQLException {
        this.execute(conn, this.sqlDropString());
    }

    public void loadData(Connection conn, String path, String terminator, 
            int ignore) 
        throws SQLException {
        this.execute(conn, this.sqlLoadDataString(path, terminator, ignore));
    }
}

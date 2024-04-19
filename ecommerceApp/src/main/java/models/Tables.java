package models;

public class Tables {
    private int id;
    private String table_name;

    public Tables(int id, String table_name) {
        this.id = id;
        this.table_name = table_name;
    }
    public int getId() {
        return id;
    }

    public String getTable_name() {
        return table_name;
    }
}

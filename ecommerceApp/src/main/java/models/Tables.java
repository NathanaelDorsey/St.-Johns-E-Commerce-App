package models;

public class Tables {
    private int id;
    private String tableName;

    public Tables(int id, String tableName) {
        this.id = id;
        this.tableName = tableName;
    }
    public int getId() {
        return id;
    }

    public String getTableName() {
        return tableName;
    }
}

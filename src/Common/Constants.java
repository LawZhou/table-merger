package Common;

public enum Constants {
    ID_COL("ID"),
    HTML_TABLE_ID("directory"),
    ROW_TAG("tr"),
    HEADER_TAG("th"),
    DATA_TAG("td");


    private final String colName;

    Constants(final String colName) {
        this.colName = colName;
    }

    @Override
    public String toString() {
        return this.colName;
    }
}

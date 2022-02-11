public enum Constants {
    ID_COL("ID"),
    DATA_PATH("./data/");

    private final String colName;

    Constants(final String colName) {
        this.colName = colName;
    }

    @Override
    public String toString() {
        return this.colName;
    }
}

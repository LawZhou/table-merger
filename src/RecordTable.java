import au.com.bytecode.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecordTable {
    private List<String> headers = null;
    private final List<RecordRow> rows;

    private final Set<String> ids;

    public RecordTable(List<String> headers, List<RecordRow> rows) {
        this.headers = headers;
        this.rows = rows;
        this.ids = new HashSet<>();
        for (RecordRow row: rows) {
            this.ids.add(row.getId());
        }
    }

    public RecordTable(List<String> headers) {
        this.headers = headers;
        this.rows = new ArrayList<>();
        this.ids = new HashSet<>();
    }


    public List<String> getHeaders() {
        return headers;
    }

    public List<RecordRow> getRows() {
        return rows;
    }

    public Set<String> getIds() {
        return ids;
    }


    public void addRow(RecordRow row) {
        String rowId = row.getId();
        if (rowId.length() > 0) {
            if (!this.ids.contains(rowId)) {
                this.rows.add(row);
                this.ids.add(rowId);
                return;
            }
            this.logWarning(row);
        }

    }

    public void sort() {
        Collections.sort(this.rows);
    }

    public void merge(RecordTable other) {
        this.mergeCols(other);
        for (RecordRow row: other.getRows()) {
            this.mergeRow(row);
        }
    }

    public void writeTable(String filePath) throws IOException {
        CSVWriter csvWriter = new CSVWriter(new FileWriter(filePath));
        List<String[]> tableRows = new ArrayList<>();
        String[] headers = this.headers.toArray(new String[0]);
        tableRows.add(headers);

        for (RecordRow row: this.rows) {
            String[] rowLst = row.convertToStrLst(this.headers);
            tableRows.add(rowLst);
        }
        csvWriter.writeAll(tableRows);
        csvWriter.close();
    }

    private void mergeRow(RecordRow row) {
        if (this.ids.contains(row.getId())) {
            this.logWarning(row);
            return;
        }
        Map<String, String> dataCol = row.getDataCols();
        for (String header: this.headers) {
            if (!dataCol.containsKey(header) && !header.equals(Constants.ID_COL.toString())) {
                dataCol.put(header, null);
            }
        }
        RecordRow newRow = new RecordRow(row.getId(), dataCol);
        this.rows.add(newRow);
        this.ids.add(row.getId());
    }

    private void logWarning(RecordRow row) {
        Logger logger = Logger.getLogger(RecordTable.class.getName());
        logger.setLevel(Level.WARNING);
        logger.warning("Row Id: " + row.getId() + " failed to add to table");
    }

    private void addCol(String header) {
        this.headers.add(header);
        for (RecordRow row: this.rows) {
            row.addCol(header);
        }
    }

    private void mergeCols(RecordTable other) {
        for (String header: other.getHeaders()) {
            if (!this.headers.contains(header)) {
                this.addCol(header);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecordTable that = (RecordTable) o;
        return Objects.equals(getHeaders(), that.getHeaders()) &&
                Objects.equals(getRows(), that.getRows()) &&
                Objects.equals(getIds(), that.getIds());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHeaders(), getRows(), getIds());
    }
}

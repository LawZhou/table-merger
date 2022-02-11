package Record;

import au.com.bytecode.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecordTable {
    private final List<String> headers;
    private final List<RecordRow> rows;
    // Keep the row ids in the table
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

    /**
     * Add a RecordRow to the table. Skip and issue a warning if the id is invalid or the table already contains the id.
     */
    public void addRow(RecordRow row) {
        String rowId = row.getId();
        if (rowId.length() > 0) {
            if (!this.ids.contains(rowId)) {
                this.rows.add(row);
                this.ids.add(rowId);
                return;
            }
        }
        this.logWarning(row);
    }

    /**
     * Sort the table in ids' ascending order
     */
    public void sort() {
        Collections.sort(this.rows);
    }

    /**
     * Merge this table with other table. Skip and issue a warning if a row in other table contains a duplicate id.
     */
    public void merge(RecordTable other) {
        this.mergeCols(other);
        for (RecordRow row: other.getRows()) {
            this.mergeRow(row);
        }
    }

    /**
     * Write the table to a csv file with filepath
     */
    public void writeTable(String filePath) throws IOException {
        CSVWriter csvWriter = new CSVWriter(new FileWriter(filePath));
        List<String[]> tableRows = new ArrayList<>();
        String[] headers = this.headers.toArray(new String[0]);
        tableRows.add(headers);

        for (RecordRow row: this.rows) {
            String[] rowLst = row.ToStringArray(this.headers);
            tableRows.add(rowLst);
        }
        csvWriter.writeAll(tableRows);
        csvWriter.close();
    }

    /**
     * Append the RecordRow to the table
     */
    private void mergeRow(RecordRow row) {
        if (this.ids.contains(row.getId())) {
            // Issue warning if the row's id is duplicate.
            this.logWarning(row);
            return;
        }

        RecordRow newRow = new RecordRow(row);
        for (String header: this.headers) {
            if (!newRow.getDataCols().containsKey(header) && !header.equals(Common.Constants.ID_COL.toString())) {
                // Add new empty col if the row doesn't contain the column and the column is not id
                newRow.addCol(header);
            }
        }
        this.rows.add(newRow);
        // store the row's id in the table
        this.ids.add(row.getId());
    }

    /**
     * Log warning in case a row is failed to add to the table
     */
    private void logWarning(RecordRow row) {
        Logger logger = Logger.getLogger(RecordTable.class.getName());
        logger.setLevel(Level.WARNING);
        logger.warning("Row Id: " + row.getId() + " failed to add to table");
    }

    /**
     * Add an empty column with the header
     */
    private void addCol(String header) {
        this.headers.add(header);
        for (RecordRow row: this.rows) {
            row.addCol(header);
        }
    }

    /**
     * Append other table's columns that are unique to the table
     */
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

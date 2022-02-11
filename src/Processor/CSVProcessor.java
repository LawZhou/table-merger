package Processor;

import Common.Constants;
import Record.RecordRow;
import Record.RecordTable;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVProcessor implements FileProcessor {
    private final String filePath;
    private CSVParser parser;

    public CSVProcessor(String filePath) {
        this.filePath = filePath;
    }

    private List<String> getHeaders() {
        return parser.getHeaderNames();
    }

    /**
     * Process the table in the file. Ignore the row contains invalid ID.
     * @return return a Record.RecordTable storing the processed data.
     */
    @Override
    public RecordTable processRecord() {
        //Retrieve headers
        List<String> headers = getHeaders();
        RecordTable table = new RecordTable(headers);
        for (final CSVRecord record: this.parser) {
            String id = "";
            Map<String, String> dataCols = new HashMap<>();
            RecordRow row;
            for (String header: headers) {
                if (header.equals(Constants.ID_COL.toString())) {
                    //Retrieve the id in the table row
                    id = record.get(header);
                    continue;
                }
                //Retrieve row values
                String data = record.get(header);
                //Trim spaces
                data = data.trim();
                dataCols.put(header, data);
            }
            row = new RecordRow(id, dataCols);
            table.addRow(row);
        }
        return table;
    }

    /**
     * Create a parser for the file
     */
    @Override
    public void parseFile() throws IOException {
        CSVFormat format = CSVFormat.RFC4180.builder().setHeader().build();
        this.parser = new CSVParser(new FileReader(this.filePath), format);
    }

}


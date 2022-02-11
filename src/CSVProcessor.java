import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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

    @Override
    public RecordTable processRecord() {
        List<String> headers = getHeaders();
        RecordTable table = new RecordTable(headers);
        for (final CSVRecord record: this.parser) {
            String id = "";
            Map<String, String> dataCols = new HashMap<>();
            RecordRow row;
            for (String header: headers) {
                if (header.equals(Constants.ID_COL.toString())) {
                    id = record.get(header);
                    continue;
                }
                String data = record.get(header);
                data = data.trim();
                dataCols.put(header, data);
            }
            row = new RecordRow(id, dataCols);
            table.addRow(row);
        }
        return table;
    }

    @Override
    public void parseFile() throws IOException {
        CSVFormat format = CSVFormat.RFC4180.builder().setHeader().build();
        CSVParser parser = new CSVParser(new FileReader(this.filePath), format);
        this.parser = parser;
    }

    @Override
    public String getFilePath() {
        return this.filePath;
    }
}


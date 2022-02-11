
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HTMLProcessor implements FileProcessor {

    private final String filePath;
    private Document doc;

    public HTMLProcessor(String filename) {
        this.filePath = filename;
    }

    private List<String> getHeaders() {
        Element directory = this.doc.getElementById("directory");
        Elements headerElmts = directory.children().first().children().first().children();
        List<String> headers = new ArrayList<>();
        for (Element headerElmt: headerElmts) {
            headers.add(headerElmt.text());
        }
        return headers;
    }

    @Override
    public RecordTable processRecord() {
        List<String> headers = getHeaders();
        RecordTable table = new RecordTable(headers);
        int idIdx = headers.indexOf(Constants.ID_COL.toString());

        Elements rowElmts = doc.getElementById("directory").children().first().children();

        for (int i = 1; i < rowElmts.size(); i++) {
            Elements headerElmts = rowElmts.get(i).children();
            String id = "";
            Map<String, String> dataCols = new HashMap<>();
            RecordRow row;
            for (int j = 0; j < headerElmts.size(); j++) {
                if (j == idIdx) {
                    id = headerElmts.get(j).text();
                    continue;
                }
                String data = headerElmts.get(j).text();
                data = data.replace('\u00A0',' ').trim();
                dataCols.put(headers.get(j), data);
            }
            row = new RecordRow(id, dataCols);
            table.addRow(row);
        }
        return table;
    }

    @Override
    public void parseFile() throws IOException {
        File input = new File(this.filePath);
        this.doc = Jsoup.parse(input, "UTF-8", "");
    }

    @Override
    public String getFilePath() {
        return this.filePath;
    }
}

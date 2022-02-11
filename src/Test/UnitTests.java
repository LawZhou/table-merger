package Test;

import Common.Constants;
import Processor.CSVProcessor;
import Processor.FileProcessor;
import Processor.HTMLProcessor;
import Record.RecordRow;
import Record.RecordTable;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static java.util.Map.entry;
import static org.junit.Assert.*;

public class UnitTests {
    static final String TEST_CSV_PATH = "./data/test_csv.csv";
    static final String TEST_HTML_PATH = "./data/test_html.html";
    static final String TEST_CSV_PATH_INVALID_ID = "./data/test_csv_invalid_id.csv";
    static final String TEST_HTML_PATH_INVALID_ID = "./data/test_html_invalid_id.html";

    static final String TEST_SECOND = "./data/second.csv";
    static final String TEST_FIRST = "./data/first.html";
    static final String TEST_SECOND_UNIQUE_ID = "./data/test_second_no_duplicate.csv";
    static final String TEST_SIMPLE = "./data/simple_csv.csv";


    @Test
    public void testCSVProcessRecord() throws IOException {
        FileProcessor csvProc = new CSVProcessor(TEST_CSV_PATH);
        csvProc.parseFile();
        RecordTable actualRes = csvProc.processRecord();

        List<String> expectedHeaders = new ArrayList<>(
                Arrays.asList(Constants.ID_COL.toString(), "testCol1", "testCol2", "testCol3", "testCol4"));
        RecordRow row1 = new RecordRow("1111",
                Map.ofEntries(
                    entry("testCol1", "row1"),
                    entry("testCol2", "row1"),
                    entry("testCol3", "row1"),
                    entry("testCol4", "row1")
                ));
        RecordRow row2 = new RecordRow("2222",
                Map.ofEntries(
                        entry("testCol1", "row2"),
                        entry("testCol2", "row2"),
                        entry("testCol3", "row2"),
                        entry("testCol4", "row2")
                ));
        RecordRow row3 = new RecordRow("3333",
                Map.ofEntries(
                        entry("testCol1", "row3"),
                        entry("testCol2", "row3"),
                        entry("testCol3", "row3"),
                        entry("testCol4", "row3")
                ));
        RecordRow row4 = new RecordRow("4444",
                Map.ofEntries(
                        entry("testCol1", "row4"),
                        entry("testCol2", "row4"),
                        entry("testCol3", "row4"),
                        entry("testCol4", "row4")
                ));
        RecordTable expectedTable = new RecordTable(expectedHeaders,
                new ArrayList<>(Arrays.asList(row1, row2, row3, row4)));
        assertEquals(actualRes, expectedTable);
    }

    @Test
    public void testCSVProcessRecordInvalidId() throws IOException {
        FileProcessor csvProc = new CSVProcessor(TEST_CSV_PATH_INVALID_ID);
        csvProc.parseFile();
        RecordTable actualRes = csvProc.processRecord();

        List<String> expectedHeaders = new ArrayList<>(
                Arrays.asList(Constants.ID_COL.toString(), "testCol1", "testCol2", "testCol3", "testCol4"));
        RecordRow row1 = new RecordRow("1111",
                Map.ofEntries(
                        entry("testCol1", "row1"),
                        entry("testCol2", "row1"),
                        entry("testCol3", "row1"),
                        entry("testCol4", "row1")
                ));
        RecordTable expectedTable = new RecordTable(expectedHeaders,
                new ArrayList<>(List.of(row1)));
        assertEquals(actualRes, expectedTable);
    }

    @Test
    public void testHTMLProcessRecord() throws IOException {
        FileProcessor htmlProc = new HTMLProcessor(TEST_HTML_PATH);
        htmlProc.parseFile();
        RecordTable actualRes = htmlProc.processRecord();

        List<String> expectedHeaders = new ArrayList<>(
                Arrays.asList(Constants.ID_COL.toString(), "testCol1", "testCol2", "testCol3", "testCol4"));
        RecordRow row1 = new RecordRow("1111",
                Map.ofEntries(
                        entry("testCol1", "row1"),
                        entry("testCol2", "row1"),
                        entry("testCol3", "row1"),
                        entry("testCol4", "row1")
                ));
        RecordRow row2 = new RecordRow("2222",
                Map.ofEntries(
                        entry("testCol1", "row2"),
                        entry("testCol2", "row2"),
                        entry("testCol3", "row2"),
                        entry("testCol4", "row2")
                ));
        RecordRow row3 = new RecordRow("3333",
                Map.ofEntries(
                        entry("testCol1", "row3"),
                        entry("testCol2", "row3"),
                        entry("testCol3", "row3"),
                        entry("testCol4", "row3")
                ));
        RecordRow row4 = new RecordRow("4444",
                Map.ofEntries(
                        entry("testCol1", "row4"),
                        entry("testCol2", "row4"),
                        entry("testCol3", "row4"),
                        entry("testCol4", "row4")
                ));
        RecordTable expectedTable = new RecordTable(expectedHeaders,
                new ArrayList<>(Arrays.asList(row1, row2, row3, row4)));
        assertEquals(actualRes, expectedTable);
    }

    @Test
    public void testHTMLProcessRecord2() throws IOException {
        FileProcessor htmlProc = new HTMLProcessor(TEST_FIRST);
        htmlProc.parseFile();
        RecordTable actualRes = htmlProc.processRecord();

        List<String> expectedHeaders = new ArrayList<>(
                Arrays.asList(Constants.ID_COL.toString(), "Name", "Address", "PhoneNum"));
        RecordRow row1 = new RecordRow("1111",
                Map.ofEntries(
                        entry("Name", "John Smith"),
                        entry("Address", "123   Apple Street"),
                        entry("PhoneNum", "555-1234")
                ));
        RecordRow row2 = new RecordRow("5555",
                Map.ofEntries(
                        entry("Name", "Jane Doe"),
                        entry("Address", "456 Orange Street"),
                        entry("PhoneNum", "555-5678")
                ));
        RecordTable expectedTable = new RecordTable(expectedHeaders,
                new ArrayList<>(Arrays.asList(row1, row2)));
        assertEquals(actualRes, expectedTable);
    }

    @Test
    public void testHTMLProcessRecordInvalidId() throws IOException {
        FileProcessor htmlProc = new HTMLProcessor(TEST_HTML_PATH_INVALID_ID);
        htmlProc.parseFile();
        RecordTable actualRes = htmlProc.processRecord();

        List<String> expectedHeaders = new ArrayList<>(
                Arrays.asList(Constants.ID_COL.toString(), "testCol1", "testCol2", "testCol3", "testCol4"));
        RecordRow row1 = new RecordRow("1111",
                Map.ofEntries(
                        entry("testCol1", "row1"),
                        entry("testCol2", "row1"),
                        entry("testCol3", "row1"),
                        entry("testCol4", "row1")
                ));
        RecordTable expectedTable = new RecordTable(expectedHeaders,
                new ArrayList<>(List.of(row1)));
        assertEquals(actualRes, expectedTable);
    }

    @Test
    public void testMerge() throws IOException {
        FileProcessor htmlProc = new HTMLProcessor(TEST_FIRST);
        htmlProc.parseFile();
        FileProcessor csvProc = new CSVProcessor(TEST_SECOND);
        csvProc.parseFile();
        RecordTable htmlTable = htmlProc.processRecord();
        RecordTable csvTable = csvProc.processRecord();

        htmlTable.merge(csvTable);


        List<String> expectedHeaders = new ArrayList<>(
                Arrays.asList(Constants.ID_COL.toString(), "Name", "Address", "PhoneNum", "Occupation", "Gender"));
        Map<String, String> row1Map = new HashMap<>();
        row1Map.put("Name", "John Smith");
        row1Map.put("Address", "123   Apple Street");
        row1Map.put("PhoneNum", "555-1234");
        row1Map.put("Occupation", null);
        row1Map.put("Gender", null);
        RecordRow row1 = new RecordRow("1111", row1Map);

        Map<String, String> row2Map = new HashMap<>();
        row2Map.put("Name", "Jane Doe");
        row2Map.put("Address", "456 Orange Street");
        row2Map.put("PhoneNum", "555-5678");
        row2Map.put("Occupation", null);
        row2Map.put("Gender", null);
        RecordRow row2 = new RecordRow("5555", row2Map);

        Map<String, String> row3Map = new HashMap<>();
        row3Map.put("Name", "Jerry Springfield");
        row3Map.put("Address", null);
        row3Map.put("PhoneNum", null);
        row3Map.put("Occupation", "Pilot");
        row3Map.put("Gender", "Male");
        RecordRow row3 = new RecordRow("6666", row3Map);

        Map<String, String> row4Map = new HashMap<>();
        row4Map.put("Name", "Mary Phil");
        row4Map.put("Address", null);
        row4Map.put("PhoneNum", null);
        row4Map.put("Occupation", "Doctor");
        row4Map.put("Gender", "Female");
        RecordRow row4 = new RecordRow("3333", row4Map);
        RecordTable expectedTable = new RecordTable(expectedHeaders,
                new ArrayList<>(Arrays.asList(row1, row2, row3, row4)));
        assertEquals(htmlTable, expectedTable);
    }

    @Test
    public void testMergeUniqueId() throws IOException {
        FileProcessor htmlProc = new HTMLProcessor(TEST_FIRST);
        htmlProc.parseFile();
        FileProcessor csvProc = new CSVProcessor(TEST_SECOND_UNIQUE_ID);
        csvProc.parseFile();
        RecordTable htmlTable = htmlProc.processRecord();
        RecordTable csvTable = csvProc.processRecord();

        htmlTable.merge(csvTable);


        List<String> expectedHeaders = new ArrayList<>(
                Arrays.asList(Constants.ID_COL.toString(), "Name", "Address", "PhoneNum", "Occupation", "Gender"));
        Map<String, String> row1Map = new HashMap<>();
        row1Map.put("Name", "John Smith");
        row1Map.put("Address", "123   Apple Street");
        row1Map.put("PhoneNum", "555-1234");
        row1Map.put("Occupation", null);
        row1Map.put("Gender", null);
        RecordRow row1 = new RecordRow("1111", row1Map);

        Map<String, String> row2Map = new HashMap<>();
        row2Map.put("Name", "Jane Doe");
        row2Map.put("Address", "456 Orange Street");
        row2Map.put("PhoneNum", "555-5678");
        row2Map.put("Occupation", null);
        row2Map.put("Gender", null);
        RecordRow row2 = new RecordRow("5555", row2Map);

        Map<String, String> row3Map = new HashMap<>();
        row3Map.put("Name", "Jerry Springfield");
        row3Map.put("Address", null);
        row3Map.put("PhoneNum", null);
        row3Map.put("Occupation", "Pilot");
        row3Map.put("Gender", "Male");
        RecordRow row3 = new RecordRow("6666", row3Map);

        Map<String, String> row4Map = new HashMap<>();
        row4Map.put("Name", "Jane Doe");
        row4Map.put("Address", null);
        row4Map.put("PhoneNum", null);
        row4Map.put("Occupation", "Teacher");
        row4Map.put("Gender", "Female");
        RecordRow row4 = new RecordRow("4444", row4Map);

        Map<String, String> row5Map = new HashMap<>();
        row5Map.put("Name", "Mary Phil");
        row5Map.put("Address", null);
        row5Map.put("PhoneNum", null);
        row5Map.put("Occupation", "Doctor");
        row5Map.put("Gender", "Female");
        RecordRow row5 = new RecordRow("3333", row5Map);
        RecordTable expectedTable = new RecordTable(expectedHeaders,
                new ArrayList<>(Arrays.asList(row1, row2, row3, row4, row5)));
        assertEquals(htmlTable, expectedTable);
    }

    @Test
    public void tesMultiMerges() throws IOException {
        FileProcessor htmlProc = new HTMLProcessor(TEST_FIRST);
        htmlProc.parseFile();
        FileProcessor csvProc = new CSVProcessor(TEST_SECOND_UNIQUE_ID);
        csvProc.parseFile();
        FileProcessor csvProc2 = new CSVProcessor(TEST_SIMPLE);
        csvProc2.parseFile();
        RecordTable htmlTable = htmlProc.processRecord();
        RecordTable csvTable = csvProc.processRecord();
        RecordTable csvTable2 = csvProc2.processRecord();

        htmlTable.merge(csvTable);
        htmlTable.merge(csvTable2);


        List<String> expectedHeaders = new ArrayList<>(
                Arrays.asList(
                        Constants.ID_COL.toString(), "Name",
                        "Address", "PhoneNum",
                        "Occupation", "Gender",
                        "testCol1", "testCol2"
                ));
        Map<String, String> row1Map = new HashMap<>();
        row1Map.put("Name", "John Smith");
        row1Map.put("Address", "123   Apple Street");
        row1Map.put("PhoneNum", "555-1234");
        row1Map.put("Occupation", null);
        row1Map.put("Gender", null);
        row1Map.put("testCol1", null);
        row1Map.put("testCol2", null);
        RecordRow row1 = new RecordRow("1111", row1Map);

        Map<String, String> row2Map = new HashMap<>();
        row2Map.put("Name", "Jane Doe");
        row2Map.put("Address", "456 Orange Street");
        row2Map.put("PhoneNum", "555-5678");
        row2Map.put("Occupation", null);
        row2Map.put("Gender", null);
        row2Map.put("testCol1", null);
        row2Map.put("testCol2", null);
        RecordRow row2 = new RecordRow("5555", row2Map);

        Map<String, String> row3Map = new HashMap<>();
        row3Map.put("Name", "Jerry Springfield");
        row3Map.put("Address", null);
        row3Map.put("PhoneNum", null);
        row3Map.put("Occupation", "Pilot");
        row3Map.put("Gender", "Male");
        row3Map.put("testCol1", null);
        row3Map.put("testCol2", null);
        RecordRow row3 = new RecordRow("6666", row3Map);

        Map<String, String> row4Map = new HashMap<>();
        row4Map.put("Name", "Jane Doe");
        row4Map.put("Address", null);
        row4Map.put("PhoneNum", null);
        row4Map.put("Occupation", "Teacher");
        row4Map.put("Gender", "Female");
        row4Map.put("testCol1", null);
        row4Map.put("testCol2", null);
        RecordRow row4 = new RecordRow("4444", row4Map);

        Map<String, String> row5Map = new HashMap<>();
        row5Map.put("Name", "Mary Phil");
        row5Map.put("Address", null);
        row5Map.put("PhoneNum", null);
        row5Map.put("Occupation", "Doctor");
        row5Map.put("Gender", "Female");
        row5Map.put("testCol1", null);
        row5Map.put("testCol2", null);
        RecordRow row5 = new RecordRow("3333", row5Map);

        Map<String, String> row6Map = new HashMap<>();
        row6Map.put("Name", null);
        row6Map.put("Address", null);
        row6Map.put("PhoneNum", null);
        row6Map.put("Occupation", null);
        row6Map.put("Gender", null);
        row6Map.put("testCol1", "row1");
        row6Map.put("testCol2", "row1");
        RecordRow row6 = new RecordRow("7777", row6Map);

        Map<String, String> row7Map = new HashMap<>();
        row7Map.put("Name", null);
        row7Map.put("Address", null);
        row7Map.put("PhoneNum", null);
        row7Map.put("Occupation", null);
        row7Map.put("Gender", null);
        row7Map.put("testCol1", "row2");
        row7Map.put("testCol2", "row2");
        RecordRow row7 = new RecordRow("8888", row7Map);
        RecordTable expectedTable = new RecordTable(expectedHeaders,
                new ArrayList<>(Arrays.asList(row1, row2, row3, row4, row5, row6, row7)));
        assertEquals(htmlTable, expectedTable);
    }

    @Test
    public void tesSort() throws IOException {
        FileProcessor htmlProc = new HTMLProcessor(TEST_FIRST);
        htmlProc.parseFile();
        FileProcessor csvProc = new CSVProcessor(TEST_SECOND_UNIQUE_ID);
        csvProc.parseFile();
        FileProcessor csvProc2 = new CSVProcessor(TEST_SIMPLE);
        csvProc2.parseFile();
        RecordTable htmlTable = htmlProc.processRecord();
        RecordTable csvTable = csvProc.processRecord();
        RecordTable csvTable2 = csvProc2.processRecord();

        htmlTable.merge(csvTable);
        htmlTable.merge(csvTable2);

        htmlTable.sort();


        List<String> expectedHeaders = new ArrayList<>(
                Arrays.asList(
                        Constants.ID_COL.toString(), "Name",
                        "Address", "PhoneNum",
                        "Occupation", "Gender",
                        "testCol1", "testCol2"
                ));
        Map<String, String> row1Map = new HashMap<>();
        row1Map.put("Name", "John Smith");
        row1Map.put("Address", "123   Apple Street");
        row1Map.put("PhoneNum", "555-1234");
        row1Map.put("Occupation", null);
        row1Map.put("Gender", null);
        row1Map.put("testCol1", null);
        row1Map.put("testCol2", null);
        RecordRow row1 = new RecordRow("1111", row1Map);

        Map<String, String> row2Map = new HashMap<>();
        row2Map.put("Name", "Jane Doe");
        row2Map.put("Address", "456 Orange Street");
        row2Map.put("PhoneNum", "555-5678");
        row2Map.put("Occupation", null);
        row2Map.put("Gender", null);
        row2Map.put("testCol1", null);
        row2Map.put("testCol2", null);
        RecordRow row2 = new RecordRow("5555", row2Map);

        Map<String, String> row3Map = new HashMap<>();
        row3Map.put("Name", "Jerry Springfield");
        row3Map.put("Address", null);
        row3Map.put("PhoneNum", null);
        row3Map.put("Occupation", "Pilot");
        row3Map.put("Gender", "Male");
        row3Map.put("testCol1", null);
        row3Map.put("testCol2", null);
        RecordRow row3 = new RecordRow("6666", row3Map);

        Map<String, String> row4Map = new HashMap<>();
        row4Map.put("Name", "Jane Doe");
        row4Map.put("Address", null);
        row4Map.put("PhoneNum", null);
        row4Map.put("Occupation", "Teacher");
        row4Map.put("Gender", "Female");
        row4Map.put("testCol1", null);
        row4Map.put("testCol2", null);
        RecordRow row4 = new RecordRow("4444", row4Map);

        Map<String, String> row5Map = new HashMap<>();
        row5Map.put("Name", "Mary Phil");
        row5Map.put("Address", null);
        row5Map.put("PhoneNum", null);
        row5Map.put("Occupation", "Doctor");
        row5Map.put("Gender", "Female");
        row5Map.put("testCol1", null);
        row5Map.put("testCol2", null);
        RecordRow row5 = new RecordRow("3333", row5Map);

        Map<String, String> row6Map = new HashMap<>();
        row6Map.put("Name", null);
        row6Map.put("Address", null);
        row6Map.put("PhoneNum", null);
        row6Map.put("Occupation", null);
        row6Map.put("Gender", null);
        row6Map.put("testCol1", "row1");
        row6Map.put("testCol2", "row1");
        RecordRow row6 = new RecordRow("7777", row6Map);

        Map<String, String> row7Map = new HashMap<>();
        row7Map.put("Name", null);
        row7Map.put("Address", null);
        row7Map.put("PhoneNum", null);
        row7Map.put("Occupation", null);
        row7Map.put("Gender", null);
        row7Map.put("testCol1", "row2");
        row7Map.put("testCol2", "row2");
        RecordRow row7 = new RecordRow("8888", row7Map);
        RecordTable expectedTable = new RecordTable(expectedHeaders,
                new ArrayList<>(Arrays.asList(row1, row5, row4, row2, row3, row6, row7)));
        assertEquals(htmlTable, expectedTable);
    }
}

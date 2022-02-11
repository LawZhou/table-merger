import java.io.IOException;

public class RecordMerger {

	public static final String FILENAME_COMBINED = "combined.csv";


	private RecordTable readFile(String filePath) throws IOException {
		RecordTable res = null;
		if (filePath.contains(".html")) {
			FileProcessor processor = new HTMLProcessor(filePath);
			processor.parseFile();
			res = processor.processRecord();
		} else if (filePath.contains(".csv")){
			FileProcessor processor = new CSVProcessor(filePath);
			processor.parseFile();
			res = processor.processRecord();
		} else {
//			Add support to other formats here
			System.err.println("File format is unsupported yet");
			System.exit(1);
		}
		return res;
	}

	/**
	 * Entry point of this test.
	 *
	 * @param args command line arguments: first.html and second.csv.
	 * @throws Exception bad things had happened.
	 */
	public static void main(final String[] args) throws Exception {

		if (args.length == 0) {
			System.err.println("Usage: java RecordMerger file1 [ file2 [...] ]");
			System.exit(1);
		}

		// your code starts here.
		RecordMerger merger = new RecordMerger();
		RecordTable res = merger.readFile(args[0]);
		for (int i = 1; i < args.length; i++) {
			RecordTable otherTable = merger.readFile(args[i]);
			res.merge(otherTable);
		}
		// Sort IDs in ascending order
		res.sort();
		res.writeTable(FILENAME_COMBINED);
		System.out.println("finish");
	}
}

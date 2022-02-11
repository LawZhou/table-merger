package Processor;

import Record.RecordTable;

import java.io.IOException;

public interface FileProcessor {
     /**
      * Process the table in the file
      * @return return a Record.RecordTable storing the processed data.
      */
     RecordTable processRecord();

     /**
      * Create a parser for the file
      */
     void parseFile() throws IOException;


}

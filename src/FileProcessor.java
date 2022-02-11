import java.io.IOException;

public interface FileProcessor {

     RecordTable processRecord();
     void parseFile() throws IOException;
     String getFilePath();


}

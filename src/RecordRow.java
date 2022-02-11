import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RecordRow implements Comparable<RecordRow>{
    private final String id;
    private final Map<String, String> dataCols;

    public RecordRow(String id, Map<String, String> dataCols) {
        this.id = id;
        this.dataCols = dataCols;
    }

    public void addCol(String header) {
        this.dataCols.put(header, null);
    }

    public String getId() {
        return id;
    }

    public Map<String, String> getDataCols() {
        return dataCols;
    }

    public String[] convertToStrLst(List<String> headers) {
        String[] res = new String[headers.size()];
        for (int i = 0; i < headers.size(); i++) {
            if (headers.get(i).equals(Constants.ID_COL.toString())) {
                res[i] = this.id;
                continue;
            }
            res[i] = this.dataCols.get(headers.get(i));
        }
        return res;
    }


    @Override
    public int compareTo(RecordRow other) {
        return Integer.parseInt(this.id) - Integer.parseInt(other.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecordRow recordRow = (RecordRow) o;
        boolean equals = getId().equals(recordRow.getId()) && getDataCols().equals(recordRow.getDataCols());
        return equals;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}

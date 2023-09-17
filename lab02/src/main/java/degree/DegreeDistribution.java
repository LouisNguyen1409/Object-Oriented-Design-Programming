package degree;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

import org.json.JSONArray;
import org.json.JSONObject;

public class DegreeDistribution {
    public JSONObject distribute(String degreesFilename, String studentsFilename) {
        return null;
    }

    /**
     * Read in a JSON file from the data directory.
     * @param filename Name of the file (e.g., "degreesBonusNoEvictions.json")
     * @precondition filename is a valid file in the data directory
     * @return JSONArray of the data in the file
     */
    public static JSONArray readInData(String filename) {
        try {
            URI path = DegreeDistribution.class.getResource("/data/" + filename).toURI();
            return new JSONArray(new String(Files.readAllBytes(Path.of(path))));
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }
}

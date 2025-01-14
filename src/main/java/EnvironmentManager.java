import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class EnvironmentManager {
    private static Properties properties = new Properties();

    // Load environment-specific properties
    public static void loadConfig(String env) {
        try {
            FileInputStream fileInput = new FileInputStream("src/test/resources/" + env + "/env.properties");
            properties.load(fileInput);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config for environment: " + env);
        }
    }

    // Get a property value
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    // Load environment-specific test data from JSON
    public static <T> T loadTestData(String env, Class<T> clazz) {
        try {
            // Update the file path to look in src/test/resources
            String filePath = "src/test/resources/" + env + "/testData.json";
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(new File(filePath), clazz);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load test data for environment: " + env, e);
        }
    }

    public static <T> List<T> loadTestDataList(String env, Class<T[]> clazz) {
        try {
            String filePath = "src/test/resources/" + env + "/dataDriven.json";
            ObjectMapper mapper = new ObjectMapper();
            T[] dataArray = mapper.readValue(new File(filePath), clazz);
            return Arrays.asList(dataArray);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load test data for environment: " + env, e);
        }
    }

}




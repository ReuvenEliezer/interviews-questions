import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.junit.Test;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.util.StopWatch;

import java.util.stream.Collectors;

public class ObjectMapperTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void convertJsonToPOJOTest() throws Exception {
        String s = Boolean.TRUE.toString();
        ParameterType parameterType = ParameterType.Boolean;
        Boolean aTrue = convertJsonToPOJO("true", Boolean.class);
        Boolean aTrue1 = convertJsonToPOJO1("true", ParameterType.Boolean.getClazz());
        Double value = convertJsonToPOJO1("5.5", ParameterType.Double.getClazz());
        String str = convertJsonToPOJO1("dsjfskjdhjkesf", ParameterType.Text.getClazz());
    }


    private static <T> T convertJsonToPOJO1(String input, Class<?> target) throws Exception {
        if (String.class.equals(target)) {
            return (T) input;
        }
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("constructType");
        JavaType javaType = objectMapper.constructType(target);
//        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(target, target);
        stopWatch.stop();
        stopWatch.start("readValue");
        T t = objectMapper.readValue(input, javaType);
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());

        return t;
    }

    private static <T> T convertJsonToPOJO(String input, Class<T> target) throws Exception {
        if (String.class.equals(target)) {
            return (T) input;
        }
        T t1 = objectMapper.readValue(input, target);
        Class<?> aClass = Class.forName(target.getName());
        return convertJsonToPOJO1(input, aClass);
    }

    @Getter
    @AllArgsConstructor
    public enum ParameterType {
        Boolean(Boolean.class),
        Text(String.class),
        Double(Double.class),
        Integer(Integer.class);

        private Class<?> clazz;
    }

    @Test
    public void test() throws Exception {
        int retryCounter = 5;

        do {
            System.out.println(retryCounter);
        } while (--retryCounter > 0);

    }

    @Test
    public void readFileTest() throws IOException {
        String basePath = "/Users/eliezer/Downloads/";
        Set<String> allServicesToUpdate = getAllServiceToUpdate(basePath);
        String joinedString = allServicesToUpdate.stream().collect(Collectors.joining(",", "(", ")"));
        System.out.println(joinedString);
        writeOutput(allServicesToUpdate, basePath + "allServiceToUpdate.csv");
    }

    private void writeOutput(Set<String> allServicesToUpdate, String fullPath) throws IOException {
        FileWriter out = new FileWriter(fullPath);
        try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT
                .withHeader("service_id"))) {
            for (String serviceId : allServicesToUpdate) {
                printer.printRecord(serviceId);
            }
        }
    }

    private static Set<String> getAllServiceToUpdate(String basePath) throws IOException {
        Set<String> allRunningServices = readValues(basePath + "all_running_services.csv", "service_id");
        Set<String> alreadyUpdated = readValues(basePath + "already_updated.csv", "service_id");
        allRunningServices.removeAll(alreadyUpdated);
        return allRunningServices;
    }

    private static Set<String> readValues(String fullPath, String colName) throws IOException {
        try (Reader reader = new FileReader(fullPath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            return csvParser.getRecords().stream().map(e -> e.get(colName)).collect(Collectors.toSet());
        }
    }

    @Test
    public void treeMapTest() {
        Map<String, String> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        map.put("B", "a");
        map.put("b", "a");
        map.put("a", "a");
        map.put("A", "a");

        System.out.println(map);

    }


    @Test
    public void fieldToJsonTest() {
        //https://www.baeldung.com/java-org-json
        Map<String, String> map = new HashMap<>();
        map.put("name", "jon doe");
        map.put("age", "22");
        map.put("city", "chicago");

        JSONObject jo = new JSONObject(map);
        System.out.println(jo);
        JSONObject jo1 = new JSONObject(
                "{\"city\":\"chicago\",\"name\":\"jon doe\",\"age\":\"22\"}"
        );

        System.out.println(jo1);


        JSONArray ja = new JSONArray();
        ja.put(Boolean.TRUE);
        ja.put("lorem ipsum");
        System.out.println(ja);

        JSONObject jo2 = new JSONObject();
        jo2.put("name", "jon doe");
        jo2.put("age", "22");
        jo2.put("city", "chicago");
        System.out.println(jo2);

        ja.put(jo2);
        System.out.println(ja);

    }

}

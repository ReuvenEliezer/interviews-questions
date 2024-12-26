package com.interviews.questions;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.util.StopWatch;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
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

    @Test
    public void test1() throws Exception {
        Data data = new Data(Duration.ofDays(1), LocalDateTime.now(), LocalDate.now());
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String s = objectMapper.writeValueAsString(data);
        System.out.println(s);
        Data data1 = objectMapper.readValue(s, Data.class);
        System.out.println(data1);

        String s1 = objectMapper().writeValueAsString(data);
        System.out.println(s1);
        Data data2 = objectMapper.readValue(s1, Data.class);
        System.out.println(data2);
    }

    record Data(Duration duration, LocalDateTime localDateTime, LocalDate localDate) {

    }

    public ObjectMapper objectMapper() {
        return Jackson2ObjectMapperBuilder.json()
                .modules(new Jdk8Module(), new JavaTimeModule())
                .featuresToDisable(
                        SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
                        DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
                )
                .build();
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
    @Ignore
    public void readFileTest() throws IOException {
        String basePath = "/Users/eliezer/Downloads/";
        Set<String> allServicesToUpdate = getAllServiceToUpdate(basePath);
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

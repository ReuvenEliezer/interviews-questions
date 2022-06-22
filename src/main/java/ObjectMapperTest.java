import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.Test;

public class ObjectMapperTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void convertJsonToPOJOTest() throws Exception {
        String s = Boolean.TRUE.toString();
        ParameterType parameterType = ParameterType.Boolean;
        Boolean aTrue = convertJsonToPOJO("true", Boolean.class);
        Boolean aTrue1 = convertJsonToPOJO1("true", ParameterType.Boolean.getClazz());
        Double value = convertJsonToPOJO1("5.5", ParameterType.Double.getClazz());
    }


    private static <T> T convertJsonToPOJO1(String input, Class<?> target) throws Exception {
        JavaType javaType = objectMapper.constructType(target);
//        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(target, target);
        return objectMapper.readValue(input, javaType);
    }

    private static <T> T convertJsonToPOJO(String input, Class<T> target) throws Exception {
        T t1 = objectMapper.readValue(input, target);
        Class<?> aClass = Class.forName(target.getName());
        return convertJsonToPOJO1(input,aClass);
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

}

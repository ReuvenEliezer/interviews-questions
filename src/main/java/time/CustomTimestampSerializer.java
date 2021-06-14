package time;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

public class CustomTimestampSerializer extends StdSerializer<Timestamp> {

    private static final SimpleDateFormat[] SIMPLE_TIMESTAMP_FORMATS = {
            new SimpleDateFormat("MMM dd, yyyy H:mm:ss aaa", Locale.ENGLISH),
            new SimpleDateFormat("MMM dd, yyyy H:mm:ssaaa", Locale.ENGLISH)
    };

    public CustomTimestampSerializer() {
        this(null);
    }

    public CustomTimestampSerializer(Class<Timestamp> t) {
        super(t);
    }

    @Override
    public void serialize(Timestamp timestamp, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) {
        for (SimpleDateFormat dateFormat : SIMPLE_TIMESTAMP_FORMATS) {
            try {
                jsonGenerator.writeString(dateFormat.format(timestamp));
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        throw new IllegalArgumentException(String.format("Invalid input for timestamp. Given %s, expecting format %s", timestamp.toString(), Arrays.stream(SIMPLE_TIMESTAMP_FORMATS).map(s -> s.toPattern()).collect(Collectors.toList())));

//        jsonGenerator.writeStartObject();
//        jsonGenerator.writeObject();
//        jsonGenerator.writeEndObject();
    }
}

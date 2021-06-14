package time;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.stream.Collectors;

public class CustomTimestampDeserializer extends StdDeserializer<Timestamp> {
    private static final SimpleDateFormat[] SIMPLE_TIMESTAMP_FORMATS = {
            new SimpleDateFormat("MMM dd, yyyy H:mm:ss aaa", Locale.ENGLISH),
            new SimpleDateFormat("MMM dd, yyyy H:mm:ssaaa", Locale.ENGLISH)
    };

    public CustomTimestampDeserializer() {
        this(null);
    }

    public CustomTimestampDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Timestamp deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        for (SimpleDateFormat dateFormat : SIMPLE_TIMESTAMP_FORMATS) {
            try {
                Date date = dateFormat.parse(node.asText());
                return new Timestamp(date.getTime());
            } catch (ParseException e) {
            }
        }
        throw new IllegalArgumentException(String.format("Invalid input for timestamp. Given %s, expecting format %s", node.asText(), Arrays.stream(SIMPLE_TIMESTAMP_FORMATS).map(s -> s.toPattern()).collect(Collectors.toList())));
    }
}
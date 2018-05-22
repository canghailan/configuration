package cc.whohow.configuration.provider;

import cc.whohow.configuration.FileBasedConfigurationSource;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;

public class JsonConfiguration extends AbstractFileBasedConfiguration<JsonNode> {
    private static final ObjectMapper DEFAULT_OBJECT_MAPPER = new ObjectMapper();

    private final ObjectMapper objectMapper;

    public JsonConfiguration(FileBasedConfigurationSource configurationSource) {
        this(configurationSource, DEFAULT_OBJECT_MAPPER);
    }

    public JsonConfiguration(FileBasedConfigurationSource configurationSource, ObjectMapper objectMapper) {
        super(configurationSource);
        this.objectMapper = objectMapper;
        this.accept(configurationSource);
    }

    @Override
    public JsonNode parse() throws Exception {
        try (InputStream stream = configurationSource.getInputStream()) {
            return objectMapper.readTree(stream);
        }
    }
}

package cc.whohow.configuration.provider;

import cc.whohow.configuration.FileBasedConfigurationSource;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;

public class JsonConfiguration<T> extends AbstractFileBasedConfiguration<T> {
    private static final ObjectMapper DEFAULT_OBJECT_MAPPER = new ObjectMapper();

    private final ObjectMapper objectMapper;
    private final JavaType type;

    public JsonConfiguration(FileBasedConfigurationSource configurationSource, Class<T> type) {
        this(configurationSource, type, DEFAULT_OBJECT_MAPPER);
    }

    public JsonConfiguration(FileBasedConfigurationSource configurationSource, TypeReference<T> type) {
        this(configurationSource, type, DEFAULT_OBJECT_MAPPER);
    }

    public JsonConfiguration(FileBasedConfigurationSource configurationSource, String typeCanonicalName) {
        this(configurationSource, typeCanonicalName, DEFAULT_OBJECT_MAPPER);
    }

    public JsonConfiguration(FileBasedConfigurationSource configurationSource, JavaType type) {
        this(configurationSource, type, DEFAULT_OBJECT_MAPPER);
    }

    public JsonConfiguration(FileBasedConfigurationSource configurationSource, Class<T> type, ObjectMapper objectMapper) {
        this(configurationSource, objectMapper.getTypeFactory().constructType(type), objectMapper);
    }

    public JsonConfiguration(FileBasedConfigurationSource configurationSource, TypeReference<T> type, ObjectMapper objectMapper) {
        this(configurationSource, objectMapper.getTypeFactory().constructType(type), objectMapper);
    }

    public JsonConfiguration(FileBasedConfigurationSource configurationSource, String typeCanonicalName, ObjectMapper objectMapper) {
        this(configurationSource, objectMapper.getTypeFactory().constructFromCanonical(typeCanonicalName), objectMapper);
    }

    public JsonConfiguration(FileBasedConfigurationSource configurationSource, JavaType type, ObjectMapper objectMapper) {
        super(configurationSource);
        this.objectMapper = objectMapper;
        this.type = type;
    }

    @Override
    public T parse() throws Exception {
        try (InputStream stream = configurationSource.getInputStream()) {
            return objectMapper.readValue(stream, type);
        }
    }
}

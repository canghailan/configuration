package cc.whohow.configuration.provider;

import cc.whohow.configuration.FileBasedConfigurationSource;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class YamlConfiguration<T> extends JsonConfiguration<T> {
    private static final ObjectMapper DEFAULT_YAML_OBJECT_MAPPER = new ObjectMapper(new YAMLFactory());

    public YamlConfiguration(FileBasedConfigurationSource configurationSource, Class<T> type) {
        this(configurationSource, type, DEFAULT_YAML_OBJECT_MAPPER);
    }

    public YamlConfiguration(FileBasedConfigurationSource configurationSource, Class<T> type, ObjectMapper objectMapper) {
        super(configurationSource, type, objectMapper);
    }
}

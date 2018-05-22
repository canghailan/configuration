package cc.whohow.configuration.provider;

import cc.whohow.configuration.FileBasedConfigurationSource;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class YamlConfiguration extends JsonConfiguration {
    private static final ObjectMapper DEFAULT_YAML_OBJECT_MAPPER = new ObjectMapper(new YAMLFactory());

    public YamlConfiguration(FileBasedConfigurationSource configurationSource) {
        this(configurationSource, DEFAULT_YAML_OBJECT_MAPPER);
    }

    public YamlConfiguration(FileBasedConfigurationSource configurationSource, ObjectMapper objectMapper) {
        super(configurationSource, objectMapper);
    }
}

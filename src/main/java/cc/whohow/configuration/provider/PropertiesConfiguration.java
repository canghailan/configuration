package cc.whohow.configuration.provider;

import cc.whohow.configuration.FileBasedConfigurationSource;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesConfiguration extends AbstractFileBasedConfiguration<Properties> {
    public PropertiesConfiguration(FileBasedConfigurationSource configurationSource) {
        super(configurationSource);
    }

    @Override
    public Properties parse() throws Exception {
        try (InputStream stream = configurationSource.getInputStream()) {
            Properties properties = new Properties();
            properties.load(stream);
            return properties;
        }
    }
}

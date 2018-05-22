package cc.whohow.configuration;

public interface FileBasedConfigurationManager extends ConfigurationManager {
    FileBasedConfigurationSource get(String key);
}

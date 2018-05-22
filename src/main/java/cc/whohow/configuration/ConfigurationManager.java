package cc.whohow.configuration;

import java.io.Closeable;

public interface ConfigurationManager extends Closeable {
    ConfigurationSource get(String key);
}

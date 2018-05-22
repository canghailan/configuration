package cc.whohow.configuration;

import java.io.Closeable;
import java.util.function.Consumer;

public interface ConfigurationSource extends Closeable {
    void reload();

    void addListener(Consumer<ConfigurationSource> listener);

    void removeListener(Consumer<ConfigurationSource> listener);
}

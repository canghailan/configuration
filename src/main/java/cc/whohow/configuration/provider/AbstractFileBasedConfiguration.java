package cc.whohow.configuration.provider;

import cc.whohow.configuration.Configuration;
import cc.whohow.configuration.ConfigurationException;
import cc.whohow.configuration.ConfigurationSource;
import cc.whohow.configuration.FileBasedConfigurationSource;

import java.util.Objects;
import java.util.function.Consumer;

public abstract class AbstractFileBasedConfiguration<T>
        extends EventSupport<T>
        implements Configuration<T>, Consumer<ConfigurationSource> {
    protected final FileBasedConfigurationSource configurationSource;
    protected volatile T value;

    public AbstractFileBasedConfiguration(FileBasedConfigurationSource configurationSource) {
        this.configurationSource = configurationSource;
        this.configurationSource.addListener(this);
    }

    @Override
    public synchronized void accept(ConfigurationSource source) {
        try {
            T oldValue = value;
            T newValue = parse();
            if (Objects.equals(oldValue, newValue)) {
                return;
            }

            value = newValue;
            notifyListeners(value);
        } catch (Exception e) {
            throw new ConfigurationException(e);
        }
    }

    @Override
    public void watch(Consumer<T> listener) {
        addListener(listener);
    }

    @Override
    public void unwatch(Consumer<T> listener) {
        removeListener(listener);
    }

    @Override
    public T get() {
        try {
            if (value == null) {
                synchronized (this) {
                    if (value == null) {
                        value = parse();
                    }
                }
            }
            return value;
        } catch (Exception e) {
            throw new ConfigurationException(e);
        }
    }

    public abstract T parse() throws Exception;

    @Override
    public void close() {
        removeListeners();
        configurationSource.removeListener(this);
    }

    @Override
    public String toString() {
        return String.valueOf(get());
    }
}

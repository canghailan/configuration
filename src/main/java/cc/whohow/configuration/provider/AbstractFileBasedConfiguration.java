package cc.whohow.configuration.provider;

import cc.whohow.configuration.Configuration;
import cc.whohow.configuration.ConfigurationException;
import cc.whohow.configuration.ConfigurationSource;
import cc.whohow.configuration.FileBasedConfigurationSource;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public abstract class AbstractFileBasedConfiguration<T> implements Configuration<T>, Consumer<ConfigurationSource> {
    protected final List<Consumer<T>> listeners = new CopyOnWriteArrayList<>();
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
            value = newValue;

            if (Objects.equals(oldValue, newValue)) {
                return;
            }
            for (Consumer<T> listener : listeners) {
                try {
                    listener.accept(newValue);
                } catch (Exception ignore) {
                }
            }
        } catch (Exception e) {
            throw new ConfigurationException(e);
        }
    }

    @Override
    public void getAndWatch(Consumer<T> listener) {
        listeners.add(listener);
        listener.accept(get());
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
        configurationSource.removeListener(this);
    }

    @Override
    public String toString() {
        return String.valueOf(get());
    }
}

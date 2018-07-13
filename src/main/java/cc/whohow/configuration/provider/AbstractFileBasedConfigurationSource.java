package cc.whohow.configuration.provider;

import cc.whohow.configuration.ConfigurationSource;
import cc.whohow.configuration.FileBasedConfigurationSource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public abstract class AbstractFileBasedConfigurationSource implements FileBasedConfigurationSource {
    protected final List<Consumer<ConfigurationSource>> listeners = new CopyOnWriteArrayList<>();
    protected volatile byte[] source;

    @Override
    public synchronized void reload() {
        try {
            byte[] oldSource = source;
            byte[] newSource = load();
            source = newSource;

            if (Arrays.equals(oldSource, newSource)) {
                return;
            }

            for (Consumer<ConfigurationSource> listener : listeners) {
                try {
                    listener.accept(this);
                } catch (Exception ignore) {
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void addListener(Consumer<ConfigurationSource> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(Consumer<ConfigurationSource> listener) {
        listeners.remove(listener);
    }

    @Override
    public ByteBuffer getBytes() {
        return ByteBuffer.wrap(getOrLoadBytes());
    }

    @Override
    public byte[] getByteArray() {
        byte[] source = getOrLoadBytes();
        return Arrays.copyOf(source, source.length);
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(getOrLoadBytes());
    }

    protected byte[] getOrLoadBytes() {
        try {
            if (source == null) {
                synchronized (this) {
                    if (source == null) {
                        source = load();
                    }
                }
            }
            return source;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public abstract byte[] load() throws IOException;
}

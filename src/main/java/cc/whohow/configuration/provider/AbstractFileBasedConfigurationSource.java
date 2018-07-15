package cc.whohow.configuration.provider;

import cc.whohow.configuration.ConfigurationSource;
import cc.whohow.configuration.FileBasedConfigurationSource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public abstract class AbstractFileBasedConfigurationSource
        extends EventSupport<ConfigurationSource>
        implements FileBasedConfigurationSource {
    protected volatile byte[] source;

    @Override
    public synchronized void reload() {
        try {
            byte[] oldSource = source;
            byte[] newSource = load();
            if (Arrays.equals(oldSource, newSource)) {
                return;
            }

            source = newSource;
            notifyListeners(this);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
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

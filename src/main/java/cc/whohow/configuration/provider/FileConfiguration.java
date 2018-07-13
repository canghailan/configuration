package cc.whohow.configuration.provider;

import cc.whohow.configuration.FileBasedConfigurationSource;

import java.io.InputStream;
import java.nio.ByteBuffer;

public class FileConfiguration extends AbstractFileBasedConfiguration<byte[]> {
    public FileConfiguration(FileBasedConfigurationSource configurationSource) {
        super(configurationSource);
    }

    public ByteBuffer getBytes() {
        return configurationSource.getBytes();
    }

    public byte[] getByteArray() {
        return configurationSource.getByteArray();
    }

    public InputStream getInputStream() {
        return configurationSource.getInputStream();
    }

    @Override
    public byte[] parse() throws Exception {
        return getByteArray();
    }
}

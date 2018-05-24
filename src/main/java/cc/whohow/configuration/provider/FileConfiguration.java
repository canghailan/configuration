package cc.whohow.configuration.provider;

import cc.whohow.configuration.FileBasedConfigurationSource;

import java.io.InputStream;
import java.nio.ByteBuffer;

public class FileConfiguration extends AbstractFileBasedConfiguration<byte[]> {
    public FileConfiguration(FileBasedConfigurationSource configurationSource) {
        super(configurationSource);
    }

    public InputStream getInputStream() {
        return configurationSource.getInputStream();
    }

    public ByteBuffer getBytes() {
        return configurationSource.getBytes();
    }

    @Override
    public byte[] parse() throws Exception {
        ByteBuffer bytes = configurationSource.getBytes();
        byte[] copy = new byte[bytes.remaining()];
        bytes.get(copy);
        return copy;
    }
}

package cc.whohow.configuration;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

public interface FileBasedConfigurationSource extends ConfigurationSource {
    ByteBuffer getBytes();

    default byte[] getByteArray() {
        ByteBuffer buffer = getBytes();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        return bytes;
    }

    default InputStream getInputStream() {
        return new ByteArrayInputStream(getByteArray());
    }
}

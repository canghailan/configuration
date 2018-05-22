package cc.whohow.configuration;

import java.io.InputStream;
import java.nio.ByteBuffer;

public interface FileBasedConfigurationSource extends ConfigurationSource {
    ByteBuffer getBytes();

    InputStream getInputStream();
}

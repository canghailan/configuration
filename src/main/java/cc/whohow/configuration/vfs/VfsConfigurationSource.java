package cc.whohow.configuration.vfs;

import cc.whohow.configuration.provider.AbstractFileBasedConfigurationSource;
import org.apache.commons.vfs2.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class VfsConfigurationSource extends AbstractFileBasedConfigurationSource {
    private final FileObject file;

    public VfsConfigurationSource(FileObject file) {
        try {
            if (!file.isFile()) {
                throw new IllegalArgumentException();
            }
        } catch (FileSystemException e) {
            throw new IllegalArgumentException(e);
        }
        this.file = file;
    }

    @Override
    public byte[] load() throws IOException {
        try (FileContent fileContent = file.getContent()) {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            fileContent.write(buffer);
            return buffer.toByteArray();
        }
    }

    @Override
    public void close() throws IOException {
        file.close();
    }
}

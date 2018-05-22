package cc.whohow.configuration.vfs;

import cc.whohow.configuration.ConfigurationException;
import cc.whohow.configuration.ConfigurationManager;
import cc.whohow.configuration.ConfigurationSource;
import cc.whohow.configuration.FileBasedConfigurationManager;
import org.apache.commons.vfs2.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class VfsConfigurationManager implements FileBasedConfigurationManager, FileListener {
    private final FileObject file;
    private final Map<FileObject, VfsConfigurationSource> configurationSources = new ConcurrentHashMap<>();

    public VfsConfigurationManager(FileObject file) {
        this.file = file;
        this.file.getFileSystem().addListener(file, this);
    }

    public void fileCreated(FileChangeEvent event) throws Exception {
        FileObject fileObject = event.getFile();
        if (fileObject.isFile()) {
            get(fileObject).reload();
        }
    }

    public void fileDeleted(FileChangeEvent event) throws Exception {
        // ignore
    }

    public void fileChanged(FileChangeEvent event) throws Exception {
        FileObject fileObject = event.getFile();
        if (fileObject.isFile()) {
            get(fileObject).reload();
        }
    }

    @Override
    public VfsConfigurationSource get(String key) {
        try {
            return get(file.resolveFile(key, NameScope.DESCENDENT));
        } catch (FileSystemException e) {
            throw new ConfigurationException(e);
        }
    }

    public VfsConfigurationSource get(FileObject file) {
        return configurationSources.computeIfAbsent(file, fileObject -> {
            VfsConfigurationSource vfsConfigurationSource = new VfsConfigurationSource(fileObject);
            vfsConfigurationSource.reload();
            return vfsConfigurationSource;
        });
    }

    @Override
    public void close() throws IOException {
        file.getFileSystem().removeListener(file, this);
        for (ConfigurationSource configurationSource : configurationSources.values()) {
            configurationSource.close();
        }
        file.close();
    }
}

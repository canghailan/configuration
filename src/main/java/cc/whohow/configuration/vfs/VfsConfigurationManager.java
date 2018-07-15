package cc.whohow.configuration.vfs;

import cc.whohow.configuration.ConfigurationException;
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
        reload(event.getFile());
    }

    public void fileDeleted(FileChangeEvent event) throws Exception {
        // ignore
    }

    public void fileChanged(FileChangeEvent event) throws Exception {
        reload(event.getFile());
    }

    protected void reload(FileObject fileObject) throws Exception {
        if (fileObject.isFile()) {
            get(fileObject).reload();
        }
    }

    @Override
    public VfsConfigurationSource get(String key) {
        if (key.startsWith("/") || key.endsWith("/")) {
            throw new IllegalArgumentException();
        }
        try {
            return get(file.resolveFile(key, NameScope.DESCENDENT));
        } catch (FileSystemException e) {
            throw new ConfigurationException(e);
        }
    }

    public VfsConfigurationSource get(FileObject file) {
        return configurationSources.computeIfAbsent(file, this::newConfigurationSource);
    }

    private VfsConfigurationSource newConfigurationSource(FileObject file) {
        return new VfsConfigurationSource(file);
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

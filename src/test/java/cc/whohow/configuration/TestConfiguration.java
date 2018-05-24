package cc.whohow.configuration;

import cc.whohow.configuration.provider.TextConfiguration;
import cc.whohow.configuration.vfs.VfsConfigurationManager;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.VFS;
import org.junit.Test;

import java.io.File;

public class TestConfiguration {
    @Test
    public void test() throws Exception {
        FileObject fileObject = VFS.getManager().resolveFile(new File("."), ".");
        FileBasedConfigurationManager configurationManager = new VfsConfigurationManager(fileObject);

        try (TextConfiguration configuration = new TextConfiguration(configurationManager.get("pom.xml"))) {
            System.out.println(configuration.get());
        }
    }
}

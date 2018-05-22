package cc.whohow.configuration.provider;

import cc.whohow.configuration.FileBasedConfigurationSource;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

public class XmlConfiguration extends AbstractFileBasedConfiguration<Document> {
    private static final DocumentBuilderFactory BUILDER_FACTORY = DocumentBuilderFactory.newInstance();

    public XmlConfiguration(FileBasedConfigurationSource configurationSource) {
        super(configurationSource);
        this.accept(configurationSource);
    }

    @Override
    public Document parse() throws Exception {
        try (InputStream stream = configurationSource.getInputStream()) {
            return BUILDER_FACTORY.newDocumentBuilder().parse(stream);
        }
    }
}

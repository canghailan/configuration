package cc.whohow.configuration.provider;

import cc.whohow.configuration.FileBasedConfigurationSource;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class TextConfiguration extends AbstractFileBasedConfiguration<String> {
    private final Charset charset;

    public TextConfiguration(FileBasedConfigurationSource configurationSource) {
        this(configurationSource, StandardCharsets.UTF_8);
    }

    public TextConfiguration(FileBasedConfigurationSource configurationSource, Charset charset) {
        super(configurationSource);
        this.charset = charset;
    }

    @Override
    public String parse() throws Exception {
        return charset.decode(configurationSource.getBytes()).toString();
    }
}

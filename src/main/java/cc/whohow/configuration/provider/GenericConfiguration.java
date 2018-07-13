package cc.whohow.configuration.provider;

import cc.whohow.configuration.FileBasedConfigurationSource;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.function.Function;

public class GenericConfiguration<T> extends AbstractFileBasedConfiguration<T> {
    private Function<FileBasedConfigurationSource, T> parser;

    public GenericConfiguration(FileBasedConfigurationSource configurationSource) {
        super(configurationSource);
    }

    public GenericConfiguration(FileBasedConfigurationSource configurationSource,
                                Function<FileBasedConfigurationSource, T> parser) {
        super(configurationSource);
        this.parser = parser;
    }

    public GenericConfiguration setParser(Function<FileBasedConfigurationSource, T> parser) {
        this.parser = parser;
        return this;
    }

    public GenericConfiguration setBufferParser(Function<ByteBuffer, T> parser) {
        return setParser(parser.compose(FileBasedConfigurationSource::getBytes));
    }

    public GenericConfiguration setByteArrayParser(Function<byte[], T> parser) {
        return setParser(parser.compose(FileBasedConfigurationSource::getByteArray));
    }

    public GenericConfiguration setStreamParser(Function<InputStream, T> parser) {
        return setParser(parser.compose(FileBasedConfigurationSource::getInputStream));
    }

    @Override
    public T parse() throws Exception {
        return parser.apply(configurationSource);
    }
}

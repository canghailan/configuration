package cc.whohow.configuration.provider;

import cc.whohow.configuration.Configuration;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Function;

public class ComposeConfiguration<T, R>
        extends EventSupport<R>
        implements Configuration<R>, Consumer<T> {
    protected final Configuration<T> configuration;
    protected final Function<T, R> function;
    protected volatile R value;

    public ComposeConfiguration(Configuration<T> configuration, Function<T, R> function) {
        this.configuration = configuration;
        this.function = function;
        configuration.getAndWatch(this);
    }

    @Override
    public void close() throws IOException {
        removeListeners();
        configuration.removeListener(this);
    }

    @Override
    public R get() {
        return value;
    }

    @Override
    public void accept(T value) {
        this.value = function.apply(value);
        notifyListeners(this.value);
    }

    @Override
    public String toString() {
        return String.valueOf(get());
    }
}

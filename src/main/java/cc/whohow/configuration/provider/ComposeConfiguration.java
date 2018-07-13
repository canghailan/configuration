package cc.whohow.configuration.provider;

import cc.whohow.configuration.Configuration;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Function;

public class ComposeConfiguration<T, R> implements Configuration<R>, Consumer<T> {
    protected final List<Consumer<R>> listeners = new CopyOnWriteArrayList<>();
    protected final Configuration<T> configuration;
    protected final Function<T, R> function;
    protected volatile R value;

    public ComposeConfiguration(Configuration<T> configuration, Function<T, R> function) {
        this.configuration = configuration;
        this.function = function;
        configuration.getAndWatch(this);
    }

    @Override
    public void getAndWatch(Consumer<R> listener) {
        listeners.add(listener);
        listener.accept(get());
    }

    @Override
    public void watch(Consumer<R> listener) {
        listeners.add(listener);
    }

    @Override
    public void unwatch(Consumer<R> listener) {
        listeners.remove(listener);
    }

    @Override
    public void close() throws IOException {
        listeners.clear();
        configuration.unwatch(this);
    }

    @Override
    public R get() {
        return value;
    }

    @Override
    public void accept(T value) {
        this.value = function.apply(value);
    }

    @Override
    public String toString() {
        return String.valueOf(get());
    }
}

package cc.whohow.configuration;

import cc.whohow.configuration.provider.ComposeConfiguration;

import java.io.Closeable;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Configuration<T> extends Supplier<T>, Closeable {
    void watch(Consumer<T> listener);

    void unwatch(Consumer<T> listener);

    default void getAndWatch(Consumer<T> listener) {
        watch(listener);
        listener.accept(get());
    }

    default <R> Configuration<R> thenApply(Function<T, R> function) {
        return new ComposeConfiguration<>(this, function);
    }
}

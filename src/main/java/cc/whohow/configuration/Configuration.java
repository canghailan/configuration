package cc.whohow.configuration;

import java.io.Closeable;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface Configuration<T> extends Supplier<T>, Closeable {
    void getAndWatch(Consumer<T> listener);
}

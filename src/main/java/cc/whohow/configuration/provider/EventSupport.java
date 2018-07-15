package cc.whohow.configuration.provider;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class EventSupport<E> {
    protected final List<Consumer<E>> listeners = new CopyOnWriteArrayList<>();

    public void addListener(Consumer<E> listener) {
        listeners.add(listener);
    }

    public void removeListener(Consumer<E> listener) {
        listeners.remove(listener);
    }

    public void removeListeners() {
        listeners.clear();
    }

    public void notifyListeners(E event) {
        for (Consumer<E> listener : listeners) {
            try {
                listener.accept(event);
            } catch (Exception ignore) {
            }
        }
    }
}

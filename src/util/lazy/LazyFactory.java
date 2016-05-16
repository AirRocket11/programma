package util.lazy;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Gi
 * @param <T> type
 */
public final class LazyFactory<T> {

    private final LazyLoader<T> loader;
    private final Set<LazyListener<T>> listeners;

    LazyFactory(final LazyLoader<T> loader) {
        this.loader = loader;
        this.listeners = new HashSet<LazyListener<T>>();
    }

    public void addListener(LazyListener<T> listener) {
        synchronized (this.listeners) {
            this.listeners.add(listener);
        }
        listener.loaderChanged(this.get());
    }

    public void removeListener(final LazyListener<T> listener) {
        synchronized (this.listeners) {
            this.listeners.remove(listener);
        }
    }

    public T get() {
        return this.loader.get();
    }

    public T reload() {
        final T s = this.loader.reload();
        for (LazyListener<T> listener : this.listeners) {
            listener.loaderChanged(s);
        }
        return s;
    }
}

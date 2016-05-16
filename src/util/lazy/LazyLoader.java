package util.lazy;

/**
 *
 * Simple lazy loading utility.
 * 
 * @author Gi
 * @param <T> type
 */
public abstract class LazyLoader<T> {

    private final Object lock = new Object();
    private final LazyInitializer<T> initialzer;

    private T result;
    private boolean loaded;

    public LazyLoader() {
        this(null);
    }

    public LazyLoader(final LazyInitializer<T> postInitializer) {
        this.initialzer = postInitializer;
        this.loaded = false;
    }

    protected abstract T create();

    public T get() {
        if (!this.loaded) {
            synchronized (this.lock) { // Check if it has already been loaded in a synchronized context already
                if (!this.loaded) {
                    this.result = this.create();
                    if (this.initialzer != null) {
                        this.initialzer.postInit(this.result);
                    }
                }
                this.loaded = true;
            }
        }
        return this.result;
    }

    public T reload() {
        this.loaded = false;
        return this.get();
    }
}

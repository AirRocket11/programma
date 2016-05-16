package util.lazy;

/**
 *
 * Use the lazy loaded object (e.g. add it as a listener)
 *
 * @author Gi
 * @param <T>
 */
public interface LazyInitializer<T> {

    void postInit(final T t);
}

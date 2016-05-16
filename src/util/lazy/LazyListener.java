package util.lazy;

/**
 *
 * @author Gi
 * @param <T> type
 */
public interface LazyListener<T> {
    
    public void loaderChanged(T type);
}

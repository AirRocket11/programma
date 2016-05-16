package util;

/**
 *
 * @author Gi
 * @param <E>
 */
public interface StringSupplier<E> {
    
    public String format(E e);
}

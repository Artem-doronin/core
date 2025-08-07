import java.lang.reflect.Array;

public class ArrayFilter {

    public static <T> T[] filter(T[] array, Filter<T> filter) {
        @SuppressWarnings("unchecked")
        T[] result = (T[]) Array.newInstance(array.getClass().getComponentType(), array.length);
        for (int i = 0; i < array.length; i++) {
            result[i] = filter.apply(array[i]);
        }
        return result;
    }
}

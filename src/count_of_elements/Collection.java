package count_of_elements;

import java.util.HashMap;
import java.util.Map;

public class Collection {
    public static <T> Map<T, Integer> count_of_elements(T[] array) {
        Map<T, Integer> map_elements = new HashMap<>();
        for (T element : array) {
            map_elements.put(element, map_elements.getOrDefault(element, 0) + 1);
        }
        return map_elements;
    }
}

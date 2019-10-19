package utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<String, Integer> extends LinkedHashMap<Object, Integer> {
    private static final long serialVersionUID = 1L;
    protected int maxElements;

    public LRUCache(int maxSize) {
        super(maxSize, 0.75F, true);
        this.maxElements = maxSize;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.LinkedHashMap#removeEldestEntry(java.util.Map.Entry)
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry<Object, Integer> eldest) {
        return (size() > this.maxElements);
    }
}

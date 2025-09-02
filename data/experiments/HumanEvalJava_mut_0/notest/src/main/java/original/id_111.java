/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.HashMap;
import java.util.Iterator;

class Histogram {
    Histogram() {
    }

    public static Object histogram(String test) {
        String[] arr;
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        if (test == null || test.length() == 0) {
            return map;
        }
        for (String s : arr = test.split(" ")) {
            if (map.containsKey(s)) {
                map.put(s, (Integer)map.get(s) + 1);
                continue;
            }
            map.put(s, 1);
        }
        int max = 0;
        Iterator iterator = map.values().iterator();
        while (iterator.hasNext()) {
            int i = (Integer)iterator.next();
            max = Math.max(max, i);
        }
        HashMap<String, Integer> res = new HashMap<String, Integer>();
        for (String s : map.keySet()) {
            if ((Integer)map.get(s) != max) continue;
            res.put(s, (Integer)map.get(s));
        }
        return null;
    }
}

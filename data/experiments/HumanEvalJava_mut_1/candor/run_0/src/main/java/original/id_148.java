/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.List;

class Bf {
    Bf() {
    }

    public static List<Object> bf(String planet1, String planet2) {
        ArrayList<Object> result;
        block4: {
            int index2;
            ArrayList<String> planets = new ArrayList<String>();
            planets.add("Mercury");
            planets.add("Venus");
            planets.add("Earth");
            planets.add("Mars");
            planets.add("Jupiter");
            planets.add("Saturn");
            planets.add("Uranus");
            planets.add("Neptune");
            result = new ArrayList<Object>();
            if (!planets.contains(planet1) || !planets.contains(planet2)) break block4;
            int index1 = planets.indexOf(planet1);
            if (index1 < (index2 = planets.indexOf(planet2))) {
                for (int i = index1 + 1; i <= index2; ++i) {
                    result.add(planets.get(i));
                }
            } else {
                for (int i = index2 + 1; i < index1; ++i) {
                    result.add(planets.get(i));
                }
            }
        }
        return result;
    }
}

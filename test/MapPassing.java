import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MapPassing {

    private static long calcPow(int number, int exp) {
        long result = 1;
        while (exp > 0) {
            if ((exp & 1) == 1)
                result *= number;
            number *= number;
            exp >>>= 1;
        }
        return result;
    }

    private static long calcPow(Map<String, Integer> argumentMap) {
        return calcPow(argumentMap.get("number"), argumentMap.getOrDefault("exp", 2));
    }

    private static long calcPow(int value) {
        return (long) value * value;
    }

    public static void main(String[] args) {

        System.out.println("Calculate the sqr value of 21: " + calcPow(21));
        System.out.println("Calculate the sqr value of 21 by map: ");
        System.out.println("Create a map with number=21");
        Map<String, Integer> argumentMap = new Map<String, Integer>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean containsKey(Object key) {
                if ("number".equals(key))
                    return number != null;
                if ("exp".equals(key))
                    return exp != null;
                return false;
            }

            @Override
            public boolean containsValue(Object value) {
                return false;
            }

            @Override
            public Integer get(Object key) {
                if ("number".equals(key))
                    return number;
                if ("exp".equals(key))
                    return exp;
                return null;
            }

            private Integer number = null;

            // Version 2:
            private Integer exp = null;

            @Override
            public Integer put(String key, Integer value) {
                if ("number".equals(key))
                    number = value;
                if ("exp".equals(key))
                    exp = value;
                return value;
            }

            @Override
            public Integer remove(Object key) {
                if ("number".equals(key))
                    number = null;
                if ("exp".equals(key))
                    exp = null;
                return null;
            }

            @Override
            public void putAll(Map<? extends String, ? extends Integer> m) {

            }

            @Override
            public void clear() {
                number = null;
            }

            @Override
            public Set<String> keySet() {
                return null;
            }

            @Override
            public Collection<Integer> values() {
                return null;
            }

            @Override
            public Set<Entry<String, Integer>> entrySet() {
                return null;
            }
        };
        argumentMap.put("number", 21);
        System.out.println("Calculate: " + calcPow(argumentMap));

        // Version goes on.
        // Now attempt to calculate the value of the cubic of 21.
        System.out.println("Calculate the cubic value of 21: " + calcPow(21, 3));
        argumentMap.put("exp", 3);
        System.out.println("Calculate of cubic with map: " + calcPow(argumentMap));

        // Using map:



    }

}

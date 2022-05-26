/*
jsonio package
 */
package jsonio;

/**
 * JSONable interface provides methods to serialize objects to JSON
 *
 * @version 1.0.0 28 April 2022
 * @author Alexander Kononov
 */
public interface JSONable {
    /**
     * Shapes json record
     *
     * @return JSON record
     */
    String toJSON();

    /**
     * Takes key-value pairs from an instance
     *
     * @return StringBuilder of JSON content
     */
    default StringBuilder getJSONContent() {
        return null;
    }

    /**
     * This static method shields some characters in JSON
     *
     * @param s String to be shielded
     * @return shielded String
     */
    static String shield(String s) {
        if (s == null) return null;
        StringBuilder sb = new StringBuilder(s);
        for (int i = 0; i < sb.length(); i++) {
            switch (sb.charAt(i)) {
                case '\\', '"', '/' -> {
                    sb.insert(i, '\\');
                    i++;
                }
                case '\b' -> {
                    sb.delete(i, i + 1);
                    sb.insert(i, "\\b");
                    i++;
                }
                case '\f' -> {
                    sb.delete(i, i + 1);
                    sb.insert(i, "\\f");
                    i++;
                }
                case '\n' -> {
                    sb.delete(i, i + 1);
                    sb.insert(i, "\\n");
                    i++;
                }
                case '\r' -> {
                    sb.delete(i, i + 1);
                    sb.insert(i, "\\r");
                    i++;
                }
                case '\t' -> {
                    sb.delete(i, i + 1);
                    sb.insert(i, "\\t");
                    i++;
                }
            }
        }
        sb.insert(sb.length(), '"');
        sb.insert(0, '"');
        return sb.toString();
    }
}
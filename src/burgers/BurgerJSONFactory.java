/*
    package of burgers
 */
package burgers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Vector;

/**
 * BurgerJSONFactory provides static method getInstanceFromJSON() that creates instance of burger from JSON Record.
 * @version 1.2.0 26 May 2022
 * @author Alexander Kononov
 */
public abstract class BurgerJSONFactory {

    /**
     * Creates nad returns instance of burger from JSON record.
     *
     * @param JSONRecord String that must contain strictly one JSON record
     * @return instance of burger
     * @throws ClassNotFoundException if there is no class in the app
     * @throws InvalidParameterException if some data appears to be invalid
     * @throws NoSuchMethodException if burger instance contains no constructor with HashMap argument
     * @throws InstantiationException if burger cannot be created
     * @throws IllegalAccessException ---
     * @throws InvocationTargetException ---
     */
    public static AbstractBurger getInstanceFromJSON(String JSONRecord)
            throws ClassNotFoundException, InvalidParameterException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Vector<String> JSONTokens = getTokens(JSONRecord);
        if ((JSONTokens == null) || (JSONTokens.size() <= 5))
            throw new InvalidParameterException("Set of tokens cannot be empty");
        var map = getHashMap(JSONTokens);
        String className = map.get("class").get(0);
        Class<?> burgerClass = Class.forName(className);
        Constructor<?> mapConstructor = burgerClass.getDeclaredConstructor(HashMap.class);
        Object burger = mapConstructor.newInstance(getHashMap(JSONTokens));
        return (AbstractBurger) burger;
    }

    /**
     * Shapes HashMap from tokens vector.
     *
     * @param tokens vector of tokens
     * @return HashMap that represents JSON content
     * @throws InvalidParameterException if some data appears to be invalid
     */
    private static HashMap<String, Vector<String>> getHashMap(Vector<String> tokens) throws InvalidParameterException {
        if (tokens == null) return null;
        HashMap<String, Vector<String>> map = new HashMap<>();
        int pos = 1;
        int size = tokens.size();
        while (pos < size) {
            String token = tokens.get(pos);
            char firstCharToken = token.charAt(0);
            if (isLetter(firstCharToken)) {
                String key = token;
                pos += 2; //miss :
                if (pos >= size) {
                    throw new InvalidParameterException("Invalid JSON record. No value after :");
                }
                String valueToken = tokens.get(pos);
                Vector<String> values = new Vector<>();
                char firstCharValue = valueToken.charAt(0);
                if (isNumber(firstCharValue) || isLetter(firstCharValue) || firstCharValue == '+' || firstCharValue == '-') { //if primitive
                    values.add(valueToken);
                } else if (firstCharValue == '{') { //if another object
                    values.add(valueToken);
                    int bracersLevel = 1;
                    while ((pos < size) && (bracersLevel > 0)) {
                        String inheritToken = tokens.get(pos++);
                        values.add(inheritToken);
                        if (inheritToken.equals("{")) {
                            bracersLevel++;
                        } else if (inheritToken.equals("}")) {
                            bracersLevel--;
                        }
                    }
                    if ((bracersLevel > 0) && (pos >= size)){
                        throw new InvalidParameterException("Invalid JSON record. Error in {} placement");
                    }
                } else if (firstCharValue == '[') {
                        int bracketsLevel = 1;
                        while ((pos < size) && (bracketsLevel > 0)) {
                            String inheritToken = tokens.get(pos++);
                            values.add(inheritToken);
                            if (inheritToken.equals("[")) {
                                bracketsLevel++;
                            } else if (inheritToken.equals("]")) {
                                bracketsLevel--;
                            }
                        }
                        if ((bracketsLevel > 0) && (pos >= size)){
                            throw new InvalidParameterException("Invalid JSON record. Error in () placement");
                        }
                }
                map.put(key, values);
                pos++;
            } else if (isDelimiter(firstCharToken) || isWhitespace(firstCharToken)) {
                pos++;
            } else {
                throw new InvalidParameterException("Invalid JSON record. Unexpected token " + token);
            }
        }
        return map;
    }

    /**
     * Checks whether the character is number
     *
     * @param c any character
     * @return boolean result
     */
    private static boolean isNumber(char c) {
        return (c >= '0') && (c <= '9');
    }

    /**
     * Checks whether the character is delimiter
     *
     * @param c any character
     * @return boolean result
     */
    private static boolean isDelimiter(char c) {
        return (c == ';') || (c == ':') || (c == ',') || (c == '{') || (c == '}') || (c == '(') || (c == ')') || (c == '[') || (c == ']');
    }

    /**
     * Checks whether the character is whitespace
     *
     * @param c any character
     * @return boolean result
     */
    private static boolean isWhitespace(char c) {
        return (c == ' ') || (c == '\n') || (c == '\r') || (c == '\f') || (c == '\t') || (c == '\b');
    }

    /**
     * Checks whether the character is letter
     *
     * @param c any character
     * @return boolean result
     */
    private static boolean isLetter(char c) {
        return ((c >= 'a') && (c <= 'z') || (c >= 'A') && (c <= 'Z'));
    }

    /**
     * This method transforms JSON record from strings to a vector of words
     *
     * @param record JSON record
     * @return vector of words
     * @throws InvalidParameterException if some data appears to be invalid
     */
    private static Vector<String> getTokens(String record) throws InvalidParameterException {
        if (record == null) return null;
        Vector<String> tokens = new Vector<>(8);
        int pos = 0;
        int anchor;
        int size = record.length();
        while (pos < size) {
            char c = record.charAt(pos);
            if (c == '"') { //beginning of the string literal
                StringBuilder token = new StringBuilder();
                pos++;
                while ((pos < size) && (record.charAt(pos) != '"')) {
                    if (record.charAt(pos) == '\\') {
                        pos++;
                        if (pos < size) {
                            switch (record.charAt(pos)) {
                                case 'n' -> token.append("\n");
                                case 't' -> token.append("\t");
                                case 'r' -> token.append("\r");
                                case 'b' -> token.append("\b");
                                case 'f' -> token.append("\f");
                                case '\\' -> token.append("\\");
                                case '/' -> token.append("/");
                            }
                        }
                    } else {
                        token.append(record.charAt(pos));
                    }
                    pos++;
                }
                pos++;
                tokens.add(token.toString());
            } else if (isLetter(c) || isNumber(c) || (c == '-') || (c == '+')) {  //beginning of the numeric literal
                anchor = pos;
                while ((pos < size) && !(isWhitespace(record.charAt(pos))) && !(isDelimiter(record.charAt(pos)))) {
                    pos++;
                }
                String token = record.substring(anchor, pos);
                tokens.add(token);
            } else if (isDelimiter(c)) {
                String token = "";
                token += c;
                tokens.add(token);
                pos++;
            } else if (isWhitespace(c)) {
                pos++;
            } else {
                throw new InvalidParameterException("Invalid lexical content: " + record.charAt(pos));
            }
        }
        return tokens;
    }
}
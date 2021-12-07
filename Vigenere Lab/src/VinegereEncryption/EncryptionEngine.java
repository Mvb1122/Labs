package VinegereEncryption;

public class EncryptionEngine {
    private String str, key;

    // constructor
    public EncryptionEngine(String initialStr, String initialKey) {
        this.str = initialStr;
        this.key = initialKey;
    }

    // methods
    public String encrypt() throws VigenereException {
        if (str.length() != key.length()) throw new VigenereException("Your key and value don't have the same length!");

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            char k = key.charAt(i);
            result.append((char) ((int) c ^ (int) k));
        }
        return result.toString();
    }

    public void setStr(String str) { this.str = str; }

    public void setKey(String key) { this.key = key; }

    public static String toASCII(String encryptedText) {
        String result = "";
        for (int i = 0; i < encryptedText.length(); i++) {
            result += ((int) encryptedText.charAt(i)) + " ";
        }
        return result.substring(0, result.length() - 1);
    }

    public char[] toCharArray() throws VigenereException {
        return stringToCharArray(encrypt());
    }

    public static char[] stringToCharArray(String s) {
        char[] output = new char[s.length()];
        for (int i = 0; i < s.length(); i++) {
            output[i] = s.charAt(i);
        }
        return output;
    }

    /**
     * By the way, you can allow Java's System.out.print() methods to print out your EncryptionEngine by simply overriding the Object.toString()
     * method-- which is called whenever java attempts to print it out.
     */
    @Override
    public String toString() {
        try {
            return toASCII(encrypt());
        } catch (VigenereException e) {
            return "";
        }
    }
}

package VinegereEncryption;

import org.junit.jupiter.engine.execution.InvocationInterceptorChain;

public class EncryptionEngine {
    private String str, key;

    // constructor
    public EncryptionEngine(String initialStr, String initialKey) {
        this.str = initialStr;
        this.key = initialKey;
    }

    // methods
    public String encrypt() throws VigenereException {
        if (str.length() != key.length()) throw new VigenereException("Your key and string-to-be-encrypted don't have the same length!");
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
}

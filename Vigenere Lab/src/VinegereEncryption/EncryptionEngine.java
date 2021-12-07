package VinegereEncryption;

public class EncryptionEngine {
    private String str, key;

    // constructor

    /**
     * Creates a new XOR encryptor.
     * @implNote In order to actually get the encrypted text, just run the .encrypt() method.
     * @param initialStr
     * @param initialKey
     */
    public EncryptionEngine(String initialStr, String initialKey) {
        this.str = initialStr;
        this.key = initialKey;
    }

    // methods

    /**
     * Gets the XOR-encrypted text.
     * @return The text, post-encryption.
     * @throws VigenereException if the key and value aren't the same length.
     */
    public String encrypt() throws VigenereException {
        // Throw the exception if the key and value aren't the right length.
        if (str.length() != key.length()) throw new VigenereException("Your key and value don't have the same length!");

        // Note, I've used a StringBuilder here instead of a String because IntelliJ complained about it.
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            // Fetch the key/value characters to encrypt.
            char c = str.charAt(i);
            char k = key.charAt(i);

            // Append the result of the encryption to the end of the already-encrypted text.
            result.append((char) ((int) c ^ (int) k));
        }
        return result.toString();
    }

    /**
     * Sets the string to be encrypted.
     * @param str The string to be encrypted.
     */
    public void setStr(String str) { this.str = str; }

    /**
     * Sets the key to encrypt by.
     * @param key The key to encrypt by.
     */
    public void setKey(String key) { this.key = key; }

    /**
     * Returns the inputted string, converted to ASCII numerals.
     * @param encryptedText The string to convert.
     * @return The text, converted, with spaces between each character.
     */
    public static String toASCII(String encryptedText) {
        // Note: IntelliJ complained about string concatenation, so I had to use StringBuilder
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < encryptedText.length(); i++) {
            // Converts the character to the ASCII numeral and then appends it to the end.
            result.append((int) encryptedText.charAt(i)).append(" ");
        }
        return result.substring(0, result.length() - 1);
    }

    /**
     * Returns the encrypted text, in a char[].
     * @return The encrypted text, in a char[].
     * @throws VigenereException if the string to encrypt and the key to encrypt by aren't the same length.
     */
    public char[] toCharArray() throws VigenereException {
        return stringToCharArray(encrypt());
    }

    /**
     * Converts an inputted string to a char[].
     * @param s The string to convert.
     * @return The returned char[].
     */
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
     * @returns The encrypted text, converted to ASCII, or an empty string if there's an error.
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

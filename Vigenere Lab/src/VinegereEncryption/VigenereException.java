package VinegereEncryption;

public class VigenereException extends Exception {
    public String error;
    public VigenereException(String error) {
        super(error);
        this.error = error;
    }

    @Override
    public String toString() {
        // Auto gen'd by IntelliJ
        return "VinegereEncryption.VinegereException{" +
                "error='" + error + '\'' +
                '}';
    }
}

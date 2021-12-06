package com.company;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import VinegereEncryption.*;

public class EncryptionTests {
    @Test
    void test1() {
        EncryptionEngine e = new EncryptionEngine("potatoes", "irishveg");
        try {
            String result = EncryptionEngine.toASCII(e.encrypt());

            assertEquals("25 29 29 18 28 25 0 20", result);
        } catch (VigenereException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    void test2() {
        EncryptionEngine e = new EncryptionEngine("circumstances", "_hemispheres_");
        try {
            String result = EncryptionEngine.toASCII(e.encrypt());

            assertEquals(new String("60 1 23 14 28 30 3 28 4 28 6 22 44"), result);
        } catch (VigenereException ex) {
            ex.printStackTrace();
        }
    }
}
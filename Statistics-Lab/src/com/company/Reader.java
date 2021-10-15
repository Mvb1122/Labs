// I was too lazy to learn how the Scanner file reading worked, so I just pulled form an old Android app I worked on.
// Pulled from my Github at https://github.com/Mvb1122/Java-Projects/blob/main/FilesApp/Strigoi/app/src/main/java/com/main/strigoi/ui/Reader.java
package com.company;

        import java.io.File;
        import java.io.FileInputStream;
        import java.io.FileOutputStream;
        import java.io.IOException;

public class Reader {

    /**
     * Writes a file to the disc.
     * @param data The data, in string format, that you want to write.
     * @param fileName The name of the file to write.
     */
    public static void writeFile(String data, String fileName) {
        // Create a file object to hold the stuff to be written.
        File file = new File(fileName);

        // Stream the file out of memory and onto the disc.
        try (FileOutputStream stream = new FileOutputStream(file)) {
            try {
                stream.write(data.getBytes());
            } finally {
                stream.close();
            }

            // On a successful write, log it in the console.e
            System.out.printf("%s written.%n", fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads a file from the disc.
     * @param inputPath The path you want to read.
     * @return The file, in string format.
     * @throws IOException if the file isn't found or if something else goes wrong.
     */
    public static String readFile(String inputPath) throws IOException {
        // Create a file object to hold the file.
        File path = new File(inputPath);

        // Create arrays to define how long the output will be and to hold the output itself.
        int length = (int) path.length();
        byte[] bytes = new byte[length];

        // Stream the file from disc into memory.
        try (FileInputStream in = new FileInputStream(path)) {
            try {
                in.read(bytes);
            } catch (IOException e) {
                in.close();
                return e.toString();
            }
        } catch (IOException e) {
            throw e;
        }

        // Decode the read bytes into characters and then return that.
        return new String(bytes);
    }


    /**
     * Tests if a file's extant by trying to read it.
     * @param path The path of the file to be read.
     * @return True, if the file's found, false if it isn't.
     */
    public static boolean fileExists(String path) {
        try {
            readFile(path);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}


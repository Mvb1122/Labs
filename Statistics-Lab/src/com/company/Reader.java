// Pulled from my Github at https://github.com/Mvb1122/Java-Projects/blob/main/FilesApp/Strigoi/app/src/main/java/com/main/strigoi/ui/Reader.java
package com.company;

        import java.io.File;
        import java.io.FileInputStream;
        import java.io.FileOutputStream;
        import java.io.IOException;

public class Reader {

    public static void writeFile(String data, String fileName) {
        // Context context = getApplicationContext();
        File file = new File(fileName);

        System.out.println(file.toString());

        try (FileOutputStream stream = new FileOutputStream(file)) {
            try {
                stream.write(data.getBytes());
            } finally {
                stream.close();
            }
            System.out.println("File written.");
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public static String readFile(String inputPath) throws IOException {

        File path = new File(inputPath);
        int length = (int) path.length();
        byte[] bytes = new byte[length];

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
        return new String(bytes);
    }

    public static boolean fileExists(String path, String type) {
        String file = null;
        try {
            file = readFile(path + type);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}


package helpers;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Giota on 16/1/2017.
 */
public class FileHelper {
    public static void saveFile(String name, String content) {
        BufferedWriter writer = null;

        File file = new File("src/files/" + name + ".txt");
        try {
            writer = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
            writer.write(content);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean getLogin(String username, String password) throws IOException {
        InputStream input = new BufferedInputStream(new FileInputStream("src/files/" + username + "_account.txt"));
        byte[] buffer = new byte[8192];

        try {
            int i = 0;
            for (int length = 0; (length = input.read(buffer)) != -1; ) {
                String[] split = new String(buffer).split(" ");
                if (split[3].equals(password)) {
                    return true;
                }
            }
        } finally {
            input.close();
        }

        return false;
    }
}



import java.io.*;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        FileInputStream fis = null;
        byte[] b = new byte[1024];
        int len = 0;

        try {
            //读入并开始
            fis = new FileInputStream(args[0]);
            while((len = fis.read(b)) != -1) {
                System.out.write(b, 0, len);
            }

            DataOutputStream output = new DataOutputStream(new FileOutputStream(new File(args[1])));
            System.exit(-1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

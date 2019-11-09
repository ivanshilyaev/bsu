// variant 2

import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String temp = line.replaceAll("\\D", "/");
            StringTokenizer token = new StringTokenizer(temp, "/");
            while (token.hasMoreTokens()) {
                String elem = token.nextToken();
                if (elem.length() > 2) {
                    if (line.indexOf(elem) > 0 && line.charAt(line.indexOf(elem)-1) == '.') {
                        line = line.replaceFirst(elem, elem.substring(0, 2));
                    }
                }
            }
            System.out.println(line);
        }
        sc.close();
    }
}

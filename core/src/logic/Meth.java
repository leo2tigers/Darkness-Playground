package logic;

public class Meth {
    public static String zero_string(int n) {
        String str = "";
        for (int i = 0; i < 2 - Math.log10(n); i++) {
            str += "0";
        }
        str += n;
        return str;
    }
}

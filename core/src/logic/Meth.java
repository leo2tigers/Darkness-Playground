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

	public static double random(double left_boundary, double right_boundary) {
		return Math.random() * (right_boundary - left_boundary) + left_boundary;
	}

	public static double center_random(double center, double width) {
		return random(center - width / 2, center + width / 2);
	}
}

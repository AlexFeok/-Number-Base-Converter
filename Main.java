package converter;
import java.math.*;
import java.util.Scanner;


public class Main {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        while (true) {
            System.out.println("Enter two numbers in format: {source base} {target base} (To quit type /exit)");
            String source = scanner.next();
            if (source.equals("/exit")) {
                exit();
                return;
            } else {
                from(source);
            }
        }
    }

    public static void from (String source) {
        int base = scanner.nextInt();
        String result = "";
        String resultForTen = "";
        String number = "";
        while (true) {
            if (base >= 2 && base <= 36) {
                System.out.println("Enter number in base " + source + " to convert to base " + base + " (To go back type /back) ");
                number = scanner.next();
                if (number.equals("/back")) {
                    return;
                }
                if (Integer.parseInt(source) != 10) {
                    try {
                        BigInteger ten = new BigInteger(number, Integer.parseInt(source));
                        result = ten.toString((int) base);

                    } catch (NumberFormatException ex) {
                        if (number.contains(".")) {
                            int len = number.length();
                            int point = number.indexOf('.');
                            if (point == -1)
                                point = len;

                            double intDecimal = 0,
                                    fracDecimal = 0,
                                    twos = 1;
                            for (int i = point - 1; i >= 0; i--) {
                                intDecimal += (number.charAt(i) - '0') * twos;
                                twos *= 2;
                            }
                            twos = 2;
                            for (int i = point + 1; i < len; i++) {
                                fracDecimal += (number.charAt(i) - '0') / twos;
                                twos *= 2.00000;
                            }
                            double d = intDecimal + fracDecimal;
                            int index = number.indexOf(".");
                            String end = number.substring(index + 1, number.length());
                            String start = number.substring(0, index);
                            BigInteger ten = new BigInteger(start, Integer.parseInt(source));
                            result = ten.toString(base);



                            BigDecimal divide = new BigDecimal(source);
                            BigDecimal multiplier = BigDecimal.ONE.divide(divide, 5, RoundingMode.HALF_UP);
                            BigDecimal resa = BigDecimal.ZERO;
                            for (char ch : end.toCharArray()) {
                                resa = resa.add(new BigDecimal(getValue(ch)).multiply(multiplier));
                                multiplier = multiplier.divide(divide, 5, RoundingMode.HALF_UP);
                            }
                            StringBuilder decPart = new StringBuilder();
                            multiplier = new BigDecimal(base);
                            for (int i = 0; i < 5; i++) {
                                resa = resa.multiply(multiplier);
                                int all = resa.toBigInteger().intValue();
                                resa = resa.subtract(new BigDecimal(all));
                                decPart.append(getChar(all));
                            }

                            String gf = new StringBuilder(result).append(".") + decPart.toString();
                            System.out.println("Conversion result: " + gf);

                        }


                    }
                }



                try{
                    resultForTen = new BigInteger(number).toString((int) base);
                } catch(NumberFormatException ex) {
                }
            }
            if (Integer.parseInt(source) == 10) {
                System.out.println("Conversion result: " + resultForTen);
            } if (!number.contains(".")){
                System.out.println("Conversion result: " + result);
            }
        }
    }






    private static char getChar(int all) {
        if (all < 10) {
            return (char) (all + '0');
        } else {
            return (char) ('a' + all - 10);

        }
    }
    private static int getValue(char ch) {
        if (ch >= '0' && ch <= '9') {
            return ch - '0';
        } else {
            return Character.toUpperCase(ch) - 'A' + 10;
        }
    }


    public static void exit () {
        System.exit(0);
    }
}

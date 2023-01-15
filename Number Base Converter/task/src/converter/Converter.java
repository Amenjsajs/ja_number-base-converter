package converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;

public class Converter {
    private static final int SCALE = 5;
    private static final List<Character> RANGE = List.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');

    public static String toDecimal(String number, int radix) {
        char[] chars = number.toUpperCase().toCharArray();
        char c;
        int index;
        BigInteger re = BigInteger.ZERO;
        int n = 0;
        for (int i = 0, len = number.length(); i < len; i++) {
            c = chars[len - i - 1];
            index = RANGE.indexOf(c);
            re = BigInteger.valueOf(radix).pow(i).multiply(BigInteger.valueOf(index)).add(re);
        }
        return re.toString();
    }

    public static String fractionPartToDecimal(String fraction, int radix) {
        char[] chars = fraction.toUpperCase().toCharArray();
        char c;
        int index;
        BigDecimal re = BigDecimal.ZERO;
        BigDecimal add;

        for (int i = 2, len = chars.length; i < len; i++) {
            c = chars[i];
            index = RANGE.indexOf(c);
            add = BigDecimal.valueOf(index).divide(BigDecimal.valueOf(radix).pow(i - 1), 5, RoundingMode.HALF_EVEN);

            re = add.add(re);
        }
        System.out.println(re);

        return re.toString();
    }

    public static String fromDecimal(int number, int radix) {
        return fromDecimal(BigInteger.valueOf(number), radix);
    }

    private static String fractionPartFromDecimal(BigDecimal fraction, int radix) {
        StringBuilder re = new StringBuilder();
        BigDecimal base = BigDecimal.valueOf(radix);
        BigDecimal[] divideAndRemainder;
        BigDecimal entirePart;

        int scale = SCALE;
        do {
            divideAndRemainder = fraction.multiply(base).divideAndRemainder(BigDecimal.ONE);
            entirePart = divideAndRemainder[0].setScale(0, RoundingMode.UNNECESSARY);

            re.append(RANGE.get(Integer.parseInt(entirePart.toBigInteger().toString())));
            fraction = divideAndRemainder[1].setScale(SCALE+1, RoundingMode.HALF_EVEN);

            if (--scale == 0) {
                break;
            }

        } while (!fraction.toString().equals("0"));
        return re.toString();
    }

    public static String fromDecimal(BigInteger number, int radix) {
        BigInteger base = BigInteger.valueOf(radix);
        StringBuilder rests = new StringBuilder();
        int mod;
        BigInteger[] quotientAndRemainder;
        do {
            quotientAndRemainder = number.divideAndRemainder(base);

            mod = Integer.parseInt(quotientAndRemainder[1].toString());
            rests.append(RANGE.get(mod));
            number = quotientAndRemainder[0];
        } while (!number.toString().equals("0"));

        return rests.reverse().toString();
    }

    public static String fromTo(String number, int from, int to) {
        String[] numberSplit = number.split("\\.");
        String entirePart;
        String decimalPart = "0";
        entirePart = numberSplit[0];

        if (numberSplit.length == 2) {
            decimalPart = "0." + numberSplit[1];
        }

        if (!decimalPart.equals("0")) {
            String entirePartResult = fromDecimal(new BigInteger(toDecimal(entirePart, from)), to);
            String decimalPartResult = fractionPartFromDecimal(new BigDecimal(fractionPartToDecimal(decimalPart, from)), to);

            return entirePartResult + "." + decimalPartResult;
        }
        return fromDecimal(new BigInteger(toDecimal(entirePart, from)), to);
    }
}

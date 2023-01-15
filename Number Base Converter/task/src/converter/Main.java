package converter;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
//        System.out.println(Converter.fractionPartToDecimal("0.2b2b2b2b2b2b2b50ad45c38a5d", 14));
//        System.exit(-1);
        Scanner input = new Scanner(System.in);

        String query;
        String[] querySplit;
        String number;
        int sourceBase;
        int targetBase;

        do {
            System.out.print("Enter two numbers in format: {source base} {target base} (To quit type /exit) ");
            query = input.nextLine();
            if (query.contains(" ")) {
                querySplit = query.split(" ");
                sourceBase = Integer.parseInt(querySplit[0]);
                targetBase = Integer.parseInt(querySplit[1]);
                do {
                    System.out.printf("Enter number in base %d to convert to base %d (To go back type /back) ", sourceBase, targetBase);
                    number = input.nextLine();

                    if(number.equals("/back")){
                        break;
                    }

                    System.out.printf("Conversion result: %s\n\n", Converter.fromTo(number, sourceBase, targetBase));
                } while (true);
            }
        } while (!query.equals("/exit"));
    }

}

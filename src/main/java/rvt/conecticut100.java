package rvt;
import java.util.Scanner;

public class conecticut100 {
    public static void main(String[] args) {
        System.out.println("Hello it's DivisionPractice program!");
        System.out.println("if you want to stop the program, please enter q or Q");
        System.out.println("");

        String end = "";
        Scanner scanner = new Scanner(System.in);
        while (!end.equals("q") && !end.equals("Q")) {
            System.out.print("Enter the numerator: ");
            int num = 0;
            if (scanner.hasNextInt()) {
                num = scanner.nextInt();
            } else {
                end = scanner.nextLine(); // consume the bad input
                if ("q".equals(end) || "Q".equals(end)) {
                    break;
                } else {
                    System.out.println("Please enter a valid number");
                    end = "notEnterInIf";
                }
            }
            if (!end.equals("notEnterInIf")) {
                int div = 0;
                System.out.print("Enter the divisor: ");
                if (scanner.hasNextInt()) {
                    div = scanner.nextInt();
                    if (div == 0) {
                        System.out.println("Divisor cannot be zero");
                    } else {
                        System.out.println(num + " / " + div + " is " + (double)num / div);
                    }
                } else {
                    System.out.println("You entered bad data");
                    System.out.println("Please try again");
                }
                String dummy = scanner.nextLine(); // consume the rest of the line
                System.out.println();
            }
            end = ""; // reset end for the next iteration
        }
        scanner.close();
        System.out.println("Thank you for using the program!");
    }
}

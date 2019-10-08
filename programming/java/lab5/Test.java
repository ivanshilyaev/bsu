package src;

import java.util.Arrays;

public class Test {
    private static void sortAndPrint(Payment[] payments) {
        System.out.println("\nSorted by " + Payment.getSortByName() + ":");
        Arrays.sort(payments);
        System.out.print(src.Payment.format());
        System.out.println();
        for(Payment cnt : payments)
            System.out.println(Payment.format(cnt));
    }

    public static void main (String[] args) {
        try {
            Payment[] payments = new Payment[6];
            payments[0] = new Payment("Беркович Павел|2019|300|24|24|2|2|350|50");
            payments[1] = new Payment("Бочков Илья|2015|250|24|15|3|2|300|45");
            payments[2] = new Payment("Заломов Данил|2017|500|24|20|5|1|660|100");
            payments[3] = new Payment("Нестёркина Алёна|2018|300|24|19|30|2|300|50");
            payments[4] = new Payment("Шиляев Иван|2019|100|20|20|40|4|300|100");
            payments[5] = new Payment("||||||||"); // empty test object
            Payment.setSortBy(0);
            sortAndPrint(payments);
            Payment.setSortBy(1);
            sortAndPrint(payments);
            Payment.setSortBy(2);
            sortAndPrint(payments);
            Payment.setSortBy(3);
            sortAndPrint(payments);
            Payment.setSortBy(4);
            sortAndPrint(payments);
            Payment.setSortBy(5);
            sortAndPrint(payments);
            Payment.setSortBy(6);
            sortAndPrint(payments);
            Payment.setSortBy(7);
            sortAndPrint(payments);
            Payment.setSortBy(8);
            sortAndPrint(payments);

            Payment n = new Payment("src.Test exception object");
            n.getMoneyOnHand();
            System.out.print(n);
        }
        catch (Exception e) {
            System.out.println("\nException: " + e);
        }
    }
}
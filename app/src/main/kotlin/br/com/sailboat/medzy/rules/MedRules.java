package br.com.sailboat.medzy.rules;

public class MedRules {

    public static boolean isOutOfStock(double totalAmount) {
        if (totalAmount == 0) {
            return true;
        }

        return false;
    }

    public static boolean isOutOfStock(double totalAmount, double alarmAmount) {
        if (isOutOfStock(totalAmount)) {
            return true;
        }

        if (totalAmount - alarmAmount < 0) {
            return true;
        }

        return false;
    }

}

public class Transaction {
    public static double calculateCashback(Customer customer, double amount) {
        double cashback = 0;

        if (amount > 1000000) {
            switch (customer.getCustomerId().substring(0, 2)) {
                case "38": // Silver
                    cashback = amount * 0.05;
                    break;
                case "56": // Gold
                    cashback = amount * 0.07;
                    break;
                case "74": // Platinum
                    cashback = amount * 0.10;
                    break;
            }
        }

        // Cashback tambahan untuk Gold dan Platinum
        if (customer.getCustomerId().startsWith("56")) { // Gold
            cashback += amount * 0.02;
        } else if (customer.getCustomerId().startsWith("74")) { // Platinum
            cashback += amount * 0.05;
        }

        return cashback;
    }
}
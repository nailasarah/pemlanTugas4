public class Customer {
    private String customerId; // Nomor pelanggan
    private String name; // Nama pelanggan
    private String pin; // PIN pelanggan
    private double balance; // Saldo pelanggan
    private boolean isBlocked; // Status akun pelanggan

    public Customer(String customerId, String name, double initialBalance, String pin) {
        this.customerId = customerId;
        this.name = name;
        this.pin = pin;
        this.balance = initialBalance;
        this.isBlocked = false;
    }


    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void blockAccount() {
        this.isBlocked = true;
    }

    public boolean authenticate(String inputPin) {
        return this.pin.equals(inputPin);
    }

    public void topUp(double amount) {
        if (!isBlocked) {
            this.balance += amount;
        } else {
            System.out.println("Akun diblokir. Tidak bisa melakukan top up.");
        }
    }

    public boolean purchase(double amount) {
        if (isBlocked) {
            System.out.println("Akun diblokir. Tidak bisa melakukan pembelian.");
            return false;
        }
        if (balance - amount < 10000) {
            System.out.println("Transaksi gagal. Saldo minimal Rp10.000 harus dipenuhi.");
            return false;
        }
        double cashback = calculateCashback(amount);
        this.balance -= amount - cashback;
        return true;
    }

    private double calculateCashback(double amount) {
        double cashback = 0;
        if (amount > 1000000) {
            switch (customerId.substring(0, 2)) {
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
        // Cashback tambahan
        switch (customerId.substring(0, 2)) {
            case "56": // Gold
                cashback += amount * 0.02;
                break;
            case "74": // Platinum
                cashback += amount * 0.05;
                break;
        }
        return cashback;
    }
}
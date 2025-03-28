import java.util.HashMap;
import java.util.Map;

public class Swalayan {
    public static final String customer = null;
    Map<String, Customer> customers = new HashMap<>();

    public void addCustomer(Customer customer) {
        customers.put(customer.getCustomerId(), customer);
    }

    public boolean authenticateCustomer(String customerId, String pin) {
        Customer customer = customers.get(customerId);
        if (customer != null && !customer.isBlocked()) {
            return customer.authenticate(pin);
        }
        return false;
    }

    public boolean purchase(String customerId, String pin, double amount) {
        Customer customer = customers.get(customerId);
        if (customer != null && !customer.isBlocked()) {
            if (customer.authenticate(pin)) {
                if (customer.purchase(amount)) {
                    double cashback = Transaction.calculateCashback(customer, amount);
                    customer.topUp(cashback); // Menambahkan cashback ke saldo
                    return true; // Transaksi berhasil
                }
            }
        }
        return false; // Transaksi gagal
    }

    public void topUp(String customerId, String pin, double amount) {
        Customer customer = customers.get(customerId);
        if (customer != null && !customer.isBlocked()) {
            if (customer.authenticate(pin)) {
                customer.topUp(amount);
            }
        }
    }

    public void blockCustomer(String customerId) {
        Customer customer = customers.get(customerId);
        if (customer != null) {
            customer.blockAccount();
        }
    }
}
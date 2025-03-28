import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        
        // Membuat objek Store
        Swalayan swalayan = new Swalayan();

        // Menambahkan pelanggan
        swalayan.addCustomer(new Customer("3800000001", "Naila", 500000, "1234"));
        swalayan.addCustomer(new Customer("5600000002", "Sarah", 1500000, "5678"));
        swalayan.addCustomer(new Customer("7400000003", "Mela", 2000000, "9101"));

        Scanner scanner = new Scanner(System.in);
        int attempts = 0; // Untuk menghitung kesalahan autentikasi

        while (true) {
            System.out.println("\n==============================");
            System.out.println("        Swalayan TINY          ");
            System.out.print("Masukkan Nomor Pelanggan: ");
            String inputCustomerId = scanner.nextLine().trim(); // Menghapus spasi

            System.out.print("Masukkan PIN: ");
            String inputPin = scanner.nextLine().trim(); // Menghapus spasi

            // Mencari pelanggan
            Customer customer = swalayan.customers.get(inputCustomerId);
            if (customer != null) {
                if (customer.isBlocked()) {
                    System.out.println("Akun Anda diblokir. Silakan hubungi layanan pelanggan.");
                    break;
                }

                if (customer.authenticate(inputPin)) {
                    System.out.println("Selamat datang, " + customer.getName() + "!");
                    System.out.println("Saldo Anda saat ini: Rp" + customer.getBalance());

                    // Menawarkan pilihan
                    System.out.print("Apakah Anda ingin (1) Top Up atau (2) Pembelian? Masukkan pilihan (1/2): ");
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Membersihkan newline

                    if (choice == 1) {
                        System.out.print("Masukkan jumlah top up: ");
                        double topUpAmount = scanner.nextDouble();
                        scanner.nextLine(); // Membersihkan newline
                        customer.topUp(topUpAmount);
                        System.out.printf("Top up berhasil! Saldo sekarang: Rp%.2f\n", customer.getBalance());
                    } else if (choice == 2) {
                        System.out.print("Masukkan jumlah transaksi: ");
                        double purchaseAmount = scanner.nextDouble();
                        scanner.nextLine(); // Membersihkan newline

                        if (swalayan.purchase(inputCustomerId, inputPin, purchaseAmount)) {
                            System.out.printf("Transaksi berhasil! Saldo sekarang: Rp%.2f\n", customer.getBalance());
                        } else {
                            System.out.println("Transaksi gagal! Saldo tidak mencukupi.");
                        }
                    } else {
                        System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                    }
                    attempts = 0; // Reset jumlah kesalahan
                } else {
                    attempts++;
                    System.out.println("PIN salah!");

                    if (attempts >= 3) {
                        swalayan.blockCustomer(inputCustomerId);
                        System.out.println("Akun Anda telah diblokir setelah 3 kesalahan autentikasi.");
                        break;
                    }
                }
            } else {
                System.out.println("Pelanggan tidak ditemukan!");
            }

            System.out.print("Apakah Anda ingin melakukan transaksi lagi? (yes/no): ");
            String continueTransaction = scanner.nextLine();
            if (!continueTransaction.equalsIgnoreCase("yes")) {
                break;
            }
        }

        System.out.println("==============================");
        System.out.println("Terima kasih telah menggunakan layanan kami!");
        scanner.close();
    }
}
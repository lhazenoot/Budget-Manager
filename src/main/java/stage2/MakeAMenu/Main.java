package stage2.MakeAMenu;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Print print = new Print();
        print.print();
    }
}

class Print {
    ActionMenu actionMenu = new ActionMenu();
    Income income = new Income();
    Purchases purchases = new Purchases();

    public void print() {
        Scanner scanner = new Scanner(System.in);
        Map<String, Double> purchaseMap = new TreeMap<>();
        double balance = 0;

        while (true) {
            actionMenu.printMenu();
            int action = scanner.nextInt();
            System.out.println();

            switch (action) {
                case 0:
                    System.out.println("Bye!");
                    return;
                case 1:
                    balance += income.addIncome();
                    break;
                case 2:
                    NewPurchase purchase = purchases.getNewPurchase();
                    purchases.addPurchase(purchaseMap, purchase);
                    balance -= purchase.price;
                    break;
                case 3:
                    purchases.printPurchases(purchaseMap);
                    break;
                case 4:
                    System.out.printf("Balance: $%.2f\n\n", balance);
                    break;
                default:
                    break;
            }
        }
    }
}

class Income {
    public Double addIncome() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter income:");
        double income  = scanner.nextDouble();
        System.out.println("Income was added!\n");
        return income;
    }
}

class NewPurchase {
    String item;
    double price;

    NewPurchase(String item, double price) {
        this.item = item;
        this.price = price;
    }
}

class Purchases {
    public NewPurchase getNewPurchase() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter purchase name:");
        String item = scanner.nextLine();
        System.out.println("Enter its price:");
        double price = scanner.nextDouble();
        return new NewPurchase(item, price);
    }

    public void addPurchase(Map<String, Double> purchaseMap, NewPurchase purchase) {
        purchaseMap.put(purchase.item, purchase.price);
        System.out.println("Purchase was added!\n");
    }

    public void printPurchases(Map<String, Double> purchaseMap) {
        if (purchaseMap.isEmpty()) {
            System.out.println("The purchase list is empty\n");
        }
        else {
            double sum = 0;
            for (String item : purchaseMap.keySet()) {
                double price = purchaseMap.get(item);
                System.out.printf("%s $%.2f\n", item, price);
                sum += price;
            }
            System.out.printf("Total sum: $%.2f\n\n", sum);
        }
    }
}

class ActionMenu {
    public void printMenu() {
        System.out.println("Choose your action:\n" +
                "1) Add income\n" +
                "2) Add purchase\n" +
                "3) Show list of purchases\n" +
                "4) Balance\n" +
                "0) Exit");
    }
}


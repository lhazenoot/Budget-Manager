package stage3.OhTheThingsYouCanBuy;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Print print = new Print();
        print.print();
    }
}

class Print {
    IncomeAndBalance incomeAndBalance = new IncomeAndBalance();
    Purchase purchase = new Purchase();
    Menu menu = new Menu();

    public void print() {
        int action;
        double balance = 0;

        while (true) {
            action = menu.printMenu(menu.actionMenu);

            switch (action) {
                case 0:
                    System.out.println("Bye!");
                    return;
                case 1:
                    balance += incomeAndBalance.addIncome();
                    break;
                case 2:
                    purchase.addPurchase();
                    break;
                case 3:
                    purchase.showPurchases();
                    break;
                case 4:
                    balance = incomeAndBalance.getBalance(balance, purchase.allPurchases);
                    break;
                default:
                    break;
            }
        }
    }
}

class IncomeAndBalance {
    public Double addIncome() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter income:");
        double income = scanner.nextDouble();
        System.out.println("Income was added!\n");
        return income;
    }

    public Double getBalance(double balance, Map<String, Double> purchases) {
        double costs = 0;
        for (double price : purchases.values()) {
            costs += price;
        }
        balance -= costs;

        if (balance < 0) {
            balance = 0.00;
        }

        System.out.printf("Balance: $%.2f\n\n", balance);
        return balance;
    }
}

class Purchase {
    Menu menu = new Menu();

    Map<String, Double> allPurchases = new HashMap<>();
    Map<String, Double> food = new HashMap<>();
    Map<String, Double> clothes = new HashMap<>();
    Map<String, Double> entertainment = new HashMap<>();
    Map<String, Double> other = new HashMap<>();

    public void addPurchase() {
        while (true) {
            int action = menu.printMenu(menu.addPurchaseMenu);

            switch (action) {
                case 1:
                    getPurchase(food, allPurchases);
                    break;
                case 2:
                    getPurchase(clothes, allPurchases);
                    break;
                case 3:
                    getPurchase(entertainment, allPurchases);
                    break;
                case 4:
                    getPurchase(other, allPurchases);
                    break;
                case 5:
                    return;
                default:
                    break;
            }
        }
    }

    public void getPurchase(Map<String, Double> typeMap, Map<String, Double> allMap) {
        String item = getItem();
        double price = getPrice();
        typeMap.put(item, price);
        System.out.println("Purchase was added!\n");
        allMap.put(item, price);
    }

    public String getItem() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter purchase name:");
        return scanner.nextLine();
    }

    public Double getPrice() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter its price:");
        return scanner.nextDouble();
    }

    public void showPurchases() {
        while (true) {
            int action = menu.printMenu(menu.showPurchasesMenu);

            switch (action) {
                case 1:
                    System.out.println("Food:");
                    printPurchases(food);
                    break;
                case 2:
                    System.out.println("Clothes:");
                    printPurchases(clothes);
                    break;
                case 3:
                    System.out.println("Entertainment:");
                    printPurchases(entertainment);
                    break;
                case 4:
                    System.out.println("Other:");
                    printPurchases(other);
                    break;
                case 5:
                    System.out.println("All:");
                    printPurchases(allPurchases);
                    break;
                case 6:
                    return;
                default:
                    break;
            }
        }
    }

    public void printPurchases(Map<String, Double> map) {
        if (map.isEmpty()) {
            System.out.println("The purchase list is empty!\n");
        }
        else {
            double sum = 0;
            for (String item : map.keySet()) {
                double price = map.get(item);
                System.out.printf("%s $%.2f\n", item, price);
                sum += price;
            }
            System.out.printf("Total sum: $%.2f\n\n", sum);
        }
    }
}

class Menu {
    public int printMenu(String menu) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(menu);
        int action = scanner.nextInt();
        System.out.println();
        return action;
    }

    String actionMenu = """
            Choose your action:
            1) Add income
            2) Add purchase
            3) Show list of purchases
            4) Balance
            0) Exit""";

    String addPurchaseMenu = """
            Choose the type of purchase
            1) Food
            2) Clothes
            3) Entertainment
            4) Other
            5) Back""";

    String showPurchasesMenu = """
            Choose the type of purchases
            1) Food
            2) Clothes
            3) Entertainment
            4) Other
            5) All
            6) Back""";
}

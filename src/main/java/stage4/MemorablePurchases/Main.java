package stage4.MemorablePurchases;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Print print = new Print();
        print.print();
    }
}

class Print {
    IncomeAndBalance incomeAndBalance = new IncomeAndBalance();
    Purchases purchases = new Purchases();
    Menu menu = new Menu();
    Files files = new Files();

    public void print() throws IOException {
        File file = new File("./src/main/java/stage4/MemorablePurchases/purchases.txt");
        double income = 0;
        double balance = 0;

        while (true) {
            int action = menu.printMenu(menu.actionMenu);

            switch (action) {
                case 0:
                    System.out.println("Bye!");
                    return;
                case 1:
                    income += incomeAndBalance.addIncome();
                    break;
                case 2:
                    purchases.addPurchase();
                    break;
                case 3:
                    purchases.showPurchases();
                    break;
                case 4:
                    balance = incomeAndBalance.getBalance(balance, income, purchases.purchasesList);
                    break;
                case 5:
                    files.saveFile(purchases.filesList, income);
                    break;
                case 6:
                    income = purchases.loadPurchases(file);
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

    public Double getBalance(double balance, double income, List<String> list) {
        double costs = 0;
        for (String purchases : list) {
            Purchase purchase = new Purchase(purchases);
            costs += purchase.price;
        }
        balance = income - costs;
        if (balance < 0) {
            balance = 0.00;
        }
        System.out.printf("Balance: $%.2f\n\n", balance);
        return balance;
    }
}

class Purchases {
    Menu menu = new Menu();

    List<String> purchasesList = new ArrayList<>();
    List<String> foodList = new ArrayList<>();
    List<String> clothesList = new ArrayList<>();
    List<String> entertainmentList = new ArrayList<>();
    List<String> otherList = new ArrayList<>();
    List<String> filesList = new ArrayList<>();

    private String all = "All:";
    private String food = "Food:";
    private String clothes = "Clothes:";
    private String entertainment = "Entertainment:";
    private String other = "Other:";

    public void addPurchase() {
        while (true) {
            int action = menu.printMenu(menu.addPurchaseMenu);

            switch (action) {
                case 1:
                    getPurchase(foodList, purchasesList, food, filesList);
                    break;
                case 2:
                    getPurchase(clothesList, purchasesList, clothes, filesList);
                    break;
                case 3:
                    getPurchase(entertainmentList, purchasesList, entertainment, filesList);
                    break;
                case 4:
                    getPurchase(otherList, purchasesList, other, filesList);
                    break;
                case 5:
                    return;
                default:
                    break;
            }
        }
    }

    public void getPurchase(List<String> typeList, List<String> purchasesList, String type, List<String> file) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter purchase name:");
        String item = scanner.nextLine();
        System.out.println("Enter its price:");
        double price = scanner.nextDouble();

        String purchase = String.format("%s $%.2f", item, price);
        typeList.add(purchase);
        purchasesList.add(purchase);
        file.add(type + purchase);
        System.out.println("Purchase was added!\n");
    }

    public void showPurchases() {
        while (true) {
            int action = menu.printMenu(menu.showPurchasesMenu);

            switch (action) {
                case 1:
                    printPurchases(food, foodList);
                    break;
                case 2:
                    printPurchases(clothes, clothesList);
                    break;
                case 3:
                    printPurchases(entertainment, entertainmentList);
                    break;
                case 4:
                    printPurchases(other, otherList);
                    break;
                case 5:
                    printPurchases(all, purchasesList);
                    break;
                case 6:
                    return;
                default:
                    break;
            }
        }
    }

    public void printPurchases(String type, List<String> list) {
        System.out.println(type);
        if (list.isEmpty()) {
            System.out.println("The purchase list is empty!\n");
        }
        else {
            double sum = 0;
            for (String purchases : list) {
                System.out.println(purchases);
                Purchase purchase = new Purchase(purchases);
                sum += purchase.price;
            }
            System.out.printf("Total sum: $%.2f\n\n", sum);
        }
    }

    public Double loadPurchases(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        String empty = "";
        double income = Double.parseDouble(scanner.nextLine().replace(",", "."));
        while (scanner.hasNextLine()) {
            String purchase = scanner.nextLine();
            filesList.add(purchase);

            if (purchase.startsWith(food)) {
                purchase = purchase.replace(food, empty);
                foodList.add(purchase);
            }
            else if (purchase.startsWith(clothes)) {
                purchase = purchase.replace(clothes, empty);
                clothesList.add(purchase);
            }
            else if (purchase.startsWith(entertainment)) {
                purchase = purchase.replace(entertainment, empty);
                entertainmentList.add(purchase);
            }
            else if (purchase.startsWith(other)) {
                purchase = purchase.replace(other, empty);
                otherList.add(purchase);
            }
            purchasesList.add(purchase);
        }
        System.out.println("Purchases were loaded!\n");
        return income;
    }
}

class Purchase {
    double price;

    public Purchase(String purchase) {
        int index = purchase.lastIndexOf("$");
        String price = purchase.substring(index+1);
        this.price = Double.parseDouble(price);
    }
}

class Files {
    public void saveFile(List<String> list, double income) throws IOException {
        File file = new File("./src/main/java/stage4/MemorablePurchases/purchases.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(String.format("%.2f\n", income));
        for (String purchase : list) {
            fileWriter.write(purchase + "\n");
        }
        fileWriter.close();
        System.out.println("Purchases were saved!\n");
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
            5) Save
            6) Load
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

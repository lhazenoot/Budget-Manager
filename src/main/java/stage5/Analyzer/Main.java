package stage5.Analyzer;

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
    Menu menu = new Menu();
    IncomeAndBalance incomeAndBalance = new IncomeAndBalance();
    Purchases purchases = new Purchases();
    Analyze analyze = new Analyze();
    SaveAndLoad saveAndLoad = new SaveAndLoad();

    public void print() throws IOException {
        File file = new File("./src/main/java/stage5/Analyzer/purchases.txt");
        double income = 0;

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
                    purchases.addNewPurchase();
                    break;
                case 3:
                    purchases.showPurchases();
                    break;
                case 4:
                    incomeAndBalance.getBalance(income, purchases.purchasesMap);
                    break;
                case 5:
                    saveAndLoad.saveToFile(income, purchases.purchasesMap);
                    break;
                case 6:
                    income = purchases.loadPurchases(file);
                    break;
                case 7:
                    analyze.printAnalyze(purchases.purchasesMap);
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

    public void getBalance(double income, Map<String, Map<String, Double>> purchaseMap) {
        double costs = 0;
        for (String key : purchaseMap.keySet()) {
            Map<String, Double> typeMap = purchaseMap.get(key);
            for (String item : typeMap.keySet()) {
                costs += typeMap.get(item);
            }
        }
        double balance = income - costs;
        if (balance < 0) {
            balance = 0.00;
        }
        System.out.printf("Balance: $%.2f\n\n", balance);
    }
}

class Purchase {
    String type;
    String item;
    double price;

    public Purchase() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter purchase name:");
        this.item = scanner.nextLine();
        System.out.println("Enter its price:");
        this.price = scanner.nextDouble();
    }
}

class PurchaseStorage {
    Map<String, Map<String, Double>> purchasesMap = new HashMap<>();
    Map<String, Double> foodMap = new HashMap<>();
    Map<String, Double> clothesMap = new HashMap<>();
    Map<String, Double> entertainmentMap = new HashMap<>();
    Map<String, Double> otherMap = new HashMap<>();

    String all = "All";
    String food = "Food";
    String clothes = "Clothes";
    String entertainment = "Entertainment";
    String other = "Other";
}

class Purchases extends PurchaseStorage {
    Menu menu = new Menu();

    public void addNewPurchase() {
        while (true) {
            int action = menu.printMenu(menu.addPurchaseMenu);
            switch (action) {
                case 1:
                    getNewPurchase(food, foodMap, purchasesMap);
                    break;
                case 2:
                    getNewPurchase(clothes, clothesMap, purchasesMap);
                    break;
                case 3:
                    getNewPurchase(entertainment, entertainmentMap, purchasesMap);
                    break;
                case 4:
                    getNewPurchase(other, otherMap, purchasesMap);
                    break;
                case 5:
                    return;
                default:
                    break;
            }
        }
    }

    public void getNewPurchase(String type, Map<String, Double> typeMap, Map<String, Map<String, Double>> purchaseMap) {
        Purchase purchase = new Purchase();
        typeMap.put(purchase.item, purchase.price);
        purchaseMap.put(type, typeMap);
        System.out.println("Purchase was added!\n");
    }

    public void showPurchases() {
        while (true) {
            int action = menu.printMenu(menu.showPurchasesMenu);
            switch (action) {
                case 1:
                    printPurchases(food, purchasesMap);
                    break;
                case 2:
                    printPurchases(clothes, purchasesMap);
                    break;
                case 3:
                    printPurchases(entertainment, purchasesMap);
                    break;
                case 4:
                    printPurchases(other, purchasesMap);
                    break;
                case 5:
                    printPurchases(all, purchasesMap);
                    break;
                case 6:
                    return;
                default:
                    break;
            }
        }
    }

    public void printPurchases(String type, Map<String, Map<String, Double>> purchaseMap) {
        if (type.equals(all)) {
            if (purchaseMap.isEmpty()) {
                System.out.println("The purchase list is empty!\n");
            }
            else {
                double sum = 0;
                System.out.printf("%s:\n", type);
                for (String key : purchaseMap.keySet()) {
                    Map<String, Double> typeMap = purchaseMap.get(key);
                    for (String item : typeMap.keySet()) {
                        double price = typeMap.get(item);
                        System.out.printf("%s $%.2f\n", item, price);
                        sum += price;
                    }
                }
                System.out.printf("Total sum: $%.2f\n\n", sum);
            }
        }
        else {
            Map<String, Double> typeMap = purchaseMap.get(type);
            if (typeMap.isEmpty()) {
                System.out.println("The purchase list is empty!\n");
            }
            else {
                double sum = 0;
                System.out.printf("%s:\n", type);
                for (String item : typeMap.keySet()) {
                    double price = typeMap.get(item);
                    System.out.printf("%s $%.2f\n", item, price);
                    sum += price;
                }
                System.out.printf("Total sum: $%.2f\n\n", sum);
            }
        }
    }

    public double loadPurchases(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        double income = Double.parseDouble(scanner.nextLine());

        while (scanner.hasNextLine()) {
            String type = scanner.next();
            double price = Double.parseDouble(scanner.next());
            String item = scanner.nextLine().strip();

            switch (type) {
                case "Food":
                    foodMap.put(item, price);
                    purchasesMap.put(food, foodMap);
                    break;
                case "Clothes":
                    clothesMap.put(item, price);
                    purchasesMap.put(clothes, clothesMap);
                    break;
                case "Entertainment":
                    entertainmentMap.put(item, price);
                    purchasesMap.put(entertainment, entertainmentMap);
                    break;
                case "Other":
                    otherMap.put(item, price);
                    purchasesMap.put(other, otherMap);
                    break;
                default:
                    break;
            }
        }
        System.out.println("Purchases were loaded!\n");
        return income;
    }
}

class Analyze extends PurchaseStorage {
    Menu menu = new Menu();

    public void printAnalyze(Map<String, Map<String, Double>> purchaseMap) {
        while (true) {
            int action = menu.printMenu(menu.analyzeMenu);

            switch (action) {
                case 1:
                    sortPurchases(all, purchaseMap);
                    break;
                case 2:
                    sortByTypePurchases(purchaseMap);
                    break;
                case 3:
                    sortCertainPurchases(purchaseMap);
                    break;
                case 4:
                    return;
                default:
                    break;
            }
        }
    }

    public void sortPurchases(String type, Map<String, Map<String, Double>> purchaseMap) {
        if (purchaseMap.isEmpty()) {
            System.out.println("The purchase list is empty!\n");
        }
        else {
            Map<String, Double> unsorted = new HashMap<>();
            if (type.equals(all)) {
                for (String key : purchaseMap.keySet()) {
                    unsorted.putAll(purchaseMap.get(key));
                }
            }
            else {
                unsorted.putAll(purchaseMap.get(type));
            }
            // sort purchases in descending order
            LinkedHashMap<String, Double> sorted = new LinkedHashMap<>();
            unsorted.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .forEachOrdered(x -> sorted.put(x.getKey(), x.getValue()));

            double total = 0;
            System.out.printf("%s:\n", type);
            for (String item : sorted.keySet()) {
                double price = sorted.get(item);
                System.out.printf("%s $%.2f\n", item, price);
                total += price;
            }
            System.out.printf("Total: $%.2f\n\n", total);
        }
    }

    public void sortByTypePurchases(Map<String, Map<String, Double>> purchaseMap) {
        double total = 0;

        LinkedHashMap<String, Double> unsorted = new LinkedHashMap<>();

        for (String key : purchaseMap.keySet()) {
            Map<String, Double> typeMap = purchaseMap.get(key);
            double price = 0;
            for (String item : typeMap.keySet()) {
                price += typeMap.get(item);
            }
            unsorted.put(key, price);
            total += price;
        }
        LinkedHashMap<String, Double> sorted = new LinkedHashMap<>();
        unsorted.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sorted.put(x.getKey(), x.getValue()));

        for (String type : sorted.keySet()) {
            double price = sorted.get(type);
            System.out.printf("%s - $%.2f\n", type, price);
        }

        System.out.printf("Total sum: $%.2f\n\n", total);
    }

    public void sortCertainPurchases(Map<String, Map<String, Double>> purchaseMap) {
        int action = menu.printMenu(menu.sortCertainMenu);
        switch (action) {
            case 1:
                sortPurchases(food, purchaseMap);
                break;
            case 2:
                sortPurchases(clothes, purchaseMap);
                break;
            case 3:
                sortPurchases(entertainment, purchaseMap);
                break;
            case 4:
                sortPurchases(other, purchaseMap);
                break;
            default:
                break;
        }
    }
}

class SaveAndLoad extends Purchases {

    public void saveToFile(double income, Map<String, Map<String, Double>> purchaseMap) throws IOException {
        File file = new File("./src/main/java/stage5/Analyzer/purchases.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(String.format("%.2f\n", income).replace(",", "."));
        for (String key : purchaseMap.keySet()) {
            Map<String, Double> typeMap = purchaseMap.get(key);
            for (String item : typeMap.keySet()) {
                double price = typeMap.get(item);
                fileWriter.write(String.format("%s %.2f %s\n", key, price, item).replaceFirst(",", "."));
            }
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
            7) Analyze (Sort)
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

    String analyzeMenu = """
            1) Sort all purchases
            2) Sort by type
            3) Sort certain type
            4) Back""";

    String sortCertainMenu = """
            1) Food
            2) Clothes
            3) Entertainment
            4) Other""";
}

package stage1.CountMyMoney;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Print print = new Print();
        print.print();
    }
}

class Input {
    public List<String> getInput() {
        Scanner scanner = new Scanner(System.in);
        List<String> list = new ArrayList<>();
        while (scanner.hasNextLine()) {
            list.add(scanner.nextLine());
        }
        return list;
    }
}

class Print {
    Input input = new Input();

    public void print() {
        List<String> purchasesList = input.getInput();
        double total= 0;

        for (String purchases : purchasesList) {
            String[] array = purchases.split("\\$");
            double price = Double.parseDouble(array[1]);
            total += price;
            System.out.println(purchases);
        }
        System.out.println();
        System.out.printf("Total: $%s", total);
    }
}


import enums.ActionLetter;
import model.*;
import util.UniversalArray;
import util.UniversalArrayImpl;

import java.util.Scanner;

public class AppRunner {
    private CardAcceptor cardAcceptor;
    private final UniversalArray<Product> products = new UniversalArrayImpl<>();
    private final CoinAcceptor coinAcceptor;
    private static boolean isExit = false;

    private AppRunner() {
        products.addAll(new Product[]{
                new Water(ActionLetter.B, 20),
                new CocaCola(ActionLetter.C, 50),
                new Soda(ActionLetter.D, 30),
                new Snickers(ActionLetter.E, 80),
                new Mars(ActionLetter.F, 80),
                new Pistachios(ActionLetter.G, 130)
        });
        coinAcceptor = new CoinAcceptor(100);
        cardAcceptor = new CardAcceptor(500);
    }

    public static void run() {
        AppRunner app = new AppRunner();
        while (!isExit) {
            app.startSimulation();
        }
    }

    private void startSimulation() {
        print("---------- ---------- ----------");
        print("В автомате доступны:");
        showProducts(products);
        print("Баланс карты: " + cardAcceptor.getCardBalance());
        print("Монет на сумму: " + coinAcceptor.getAmount());

        UniversalArray<Product> allowProducts = getAllowedProducts();
        chooseAction(allowProducts);
    }

    private UniversalArray<Product> getAllowedProducts() {
        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        for (int i = 0; i < products.size(); i++) {
            // Товар доступен, если хватает монет ИЛИ денег на карте
            if (coinAcceptor.getAmount() >= products.get(i).getPrice() ||
                    cardAcceptor.getCardBalance() >= products.get(i).getPrice()) {
                allowProducts.add(products.get(i));
            }
        }
        return allowProducts;
    }

    private void chooseAction(UniversalArray<Product> allowProducts) {
        showActions(allowProducts);
        print(" h - Выйти");
        String action = fromConsole().substring(0, 1);

        if ("h".equalsIgnoreCase(action)) {
            isExit = true;
            return;
        }

        try {
            boolean found = false;
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getActionLetter().getValue().equalsIgnoreCase(action)) {
                    int price = products.get(i).getPrice();

                    if (coinAcceptor.getAmount() >= price) {
                        coinAcceptor.setAmount(coinAcceptor.getAmount() - price);
                        print("Вы купили " + products.get(i).getName() + " (оплата монетами)");
                    } else if (cardAcceptor.getCardBalance() >= price) {
                        cardAcceptor.setCardBalance(cardAcceptor.getCardBalance() - price);
                        print("Вы купили " + products.get(i).getName() + " (оплата картой)");
                    } else {
                        print("Недостаточно средств!");
                    }
                    found = true;
                    break;
                }
            }
            if (!found) {
                print("Недопустимая буква. Попробуйте еще раз.");
            }
        } catch (Exception e) {
            print("Ошибка при выборе действия. Попробуйте еще раз.");
        }
    }

    private void showActions(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(String.format(" %s - %s", products.get(i).getActionLetter().getValue(), products.get(i).getName()));
        }
    }

    private String fromConsole() {
        return new Scanner(System.in).nextLine();
    }

    private void showProducts(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(products.get(i).toString());
        }
    }

    private void print(String msg) {
        System.out.println(msg);
    }
}

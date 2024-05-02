import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        calc (guestNum(), goodCart());
    }

    private static void calc(int numOfGuests, ArrayList<Goods> goodsArrayList) {
        System.out.println("Список товаров:");
        double sum = 0.0;
        for (Goods good : goodsArrayList) {
            System.out.println(good.name);
            sum += good.price;
        }
        double eachSum=Math.round(sum*100/numOfGuests);
        System.out.printf("Итого каждый гость должен заплатить %.2f %s\n", eachSum/100, rubl(eachSum/100));
    }
    private static int guestNum() {
        System.out.println("На сколько человек разделить счет?");
        while (true) {
            if (scan.hasNextInt()) {
                int numOfGuests = scan.nextInt();
                if (numOfGuests == 1) {
                    System.out.println("Ваш ответ: \'1\'. Отсутствует необходимость делить счет." +
                            "\nВведите корректное количество гостей.");
                } else if (numOfGuests < 1) {
                    System.out.println("Ваш ответ: \'" + numOfGuests +
                            "\'. Нужно целое число > 1. " +
                            "\nВведите корректное количество гостей.");
                } else {
                    scan.nextLine();
                    return numOfGuests;
                }
            } else {
                String str = scan.nextLine().toString();
                System.out.println("Ваш ответ: \'" + str + "\'. Необходимо ввести целое число > 1." +
                        "\nВведите корректное количество гостей.");
            }
        }
    }
    private static ArrayList<Goods> goodCart() {
        System.out.println("Введите название товара и его стоимость в формате: " +
                "\'Название рубли.копейки\' Например: суп 6.00. Также можно ввести: суп 6,00");
        ArrayList<Goods> goodsArrayList = new ArrayList<>();
        String strGoodInfo;
        double sum = 0.0;
        while (true) {
            strGoodInfo = scan.nextLine().trim();
            int lastSpaseIndex = strGoodInfo.lastIndexOf(' ');
            if (lastSpaseIndex == -1)//| strGoodInfo.lastIndexOf(' ')>=strGoodInfo.length()-1)
            {
                System.out.println("Не полная информация о товаре.\nВведите корректные данные");
            }
            else {
                int lastPointIndex = strGoodInfo.replace(',', '.').substring(lastSpaseIndex).indexOf('.');
                if (strGoodInfo.replace(',', '.').lastIndexOf('.') < strGoodInfo.length() - 3 & lastPointIndex != -1)
                { System.out.println("Ошибка при вводе стоимости.\nВведите корректные данные");
                }
                else {
                    String strPrice = new String(strGoodInfo.substring(strGoodInfo.lastIndexOf(' ')));
                    try {
                        double price = Double.parseDouble(strPrice.replace(',', '.').trim());
                        sum += price;
                        String name = strGoodInfo.substring(0, strGoodInfo.lastIndexOf(" "));
                        Goods good = new Goods(name, price);
                        goodsArrayList.add(good);
                        System.out.printf("Товар %s по цене %.2f добавлен успешно!\nПромежуточный итог: %.2f %s", name, price, sum, rubl(sum));
                        System.out.println("\nХотите ли добавить ещё один товар?");
                        if (scan.nextLine().toString().trim().toLowerCase().equals("завершить")) {
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Ошибка при вводе стоимости товара. Введите корректные данные");
                    }
                }
            }
        }
        return goodsArrayList;
    }
    private static String rubl (double sum)
        {
        String strSum = sum + "";
        int k = strSum.indexOf('.') - 1;
        if (k > 0) {
            if (strSum.charAt(k - 1) == '1') {
                return "рублей";
            }
        }
        switch (strSum.charAt(k)) {
            case '1': return ("рубль");
            case '2':
            case '3':
            case '4': return ("рубля");
            default: return ("рублей");
            }
        }
    }
 class Goods {
     String name;
     double price;
     Goods(String name, double price) {
         this.name = name;
         this.price = price;
     }
 }

 /* Предполагаю, что изначальная задача была проще, можно было
  дать запрос название товаров и цену на разных строках (и проверять  hasNextDouble()
 и затем считать методом nextDouble(). Можно было не обрабатывать запятые,
 и забытые копейки в шаблоне "0.00". Но мне показалась, что так задача
 приближена интеренее и приближена к жизни.
  */
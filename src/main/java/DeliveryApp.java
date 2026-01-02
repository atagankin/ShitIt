import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class DeliveryApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Parcel> allParcels = new ArrayList<>();
    private static final ParcelBox<StandardParcel> standartBox = new ParcelBox<>(100, 1);
    private static final ParcelBox<FragileParcel> fragileBox = new ParcelBox<>(100, 2);
    private static final ParcelBox<PerishableParcel> perishableBox = new ParcelBox<>(100, 3);

    private static final List<Trackable> trackableParcelsForStatus = new ArrayList<>();

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            showMenu();
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addParcel();
                    break;
                case 2:
                    sendParcels();
                    break;
                case 3:
                    calculateCosts();
                    break;
                case 4:
                    sendTrackMessage();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("Выберите действие:");
        System.out.println("1 — Добавить посылку");
        System.out.println("2 — Отправить все посылки");
        System.out.println("3 — Посчитать стоимость доставки");
        System.out.println("4 - Отправить сообщение мониторинга");
        System.out.println("0 — Завершить");
    }

    // реализуйте методы ниже
    private static void addParcel() {
        // Подсказка: спросите тип посылки и необходимые поля, создайте объект и добавьте в allParcels
        System.out.println("Введите тип посылки:");
        System.out.println("1 - Стандартная посылка");
        System.out.println("2 - Хрупкая посылка");
        System.out.println("3 - Скоропортящаяся посылка");
        int parcelType = scanner.nextInt();
        scanner.nextLine();

        if (!(parcelType == 1 || parcelType == 2 || parcelType ==3)) {
            System.out.println("Введен неверный тип посылки.");
        }

        // Проверяем, что посылки с таким описанием не существует, иначе выдаем ошибку
        String description = null;
        boolean needUniqueDescription = true;

        while (needUniqueDescription) {
            System.out.println("Введите описание посылки");
            String inputDscription = scanner.nextLine();

            if (inputDscription.isEmpty()) {
                System.out.println("Описание не может быть пустым");
                continue;
            }

            boolean isDescriptionExists = allParcels.stream()
                    .anyMatch(p -> p.getDescription().equals(inputDscription));

            if (isDescriptionExists) {
                System.out.println("Посылка с таким описанием уже существует.");
            } else {
                description = inputDscription;
                needUniqueDescription = false;
            }
        }

        System.out.print("Введите вес посылки: ");
        int weight = scanner.nextInt();
        scanner.nextLine();
        System.out.println();

        System.out.println("Введите адрес доставки:");
        String deliveryAddress = scanner.nextLine();

        System.out.print("Введите дату отправки: ");
        int sendDay = scanner.nextInt();
        scanner.nextLine();

        switch (parcelType) {
            case 1:
                StandardParcel p1 = new StandardParcel(description, weight, deliveryAddress, sendDay);
                allParcels.add(p1);
                break;
            case 2:
                FragileParcel p2 = new FragileParcel(description, weight, deliveryAddress, sendDay);
                allParcels.add(p2);
                trackableParcelsForStatus.add(p2);
                break;
            case 3:
                System.out.print("Введите срок годности: ");
                int timeToLive = scanner.nextInt();
                System.out.println();
                PerishableParcel p3 = new PerishableParcel(description, weight, deliveryAddress, sendDay, timeToLive);
                allParcels.add(p3);
                break;
        }
    }

    private static void sendParcels() {
        // Пройти по allParcels, вызвать packageItem() и deliver()

        /* Исходя из условий ТЗ:
           "
               Чтобы посылки было удобно перевозить, служба доставки упаковывает их в коробки.
               Каждая коробка затем отправляется в один из каналов доставки
               <...>
               После чего все посылки в коробке совместно перевозятся в город назначения.
           "
           понимаем, что отправить посылку без упаковки в коробку нельзя.
           Значит, методж отправить мы можем вызвать только перебором посылок в коробках.
         */

        // Упаковать посылки
        allParcels.stream()
                .filter(x -> x.getKeyState() == ParcelStatus.NEW)
                .forEach(Parcel::packageItem);

        // Сложить посылки в нужные коробки
        allParcels.stream()
                .filter(x -> x.getKeyState() == ParcelStatus.PACKED)
                .forEach(p -> {
                    // Определяем, в какую коробку положить посылку
                    if (p instanceof StandardParcel) {
                        standartBox.addParcel((StandardParcel) p);
                    } else if (p instanceof FragileParcel) {
                        fragileBox.addParcel((FragileParcel) p);
                    } else if (p instanceof PerishableParcel) {
                        perishableBox.addParcel((PerishableParcel) p);
                    }
                });

        //Отправить коробки
        standartBox.sendBox();
        fragileBox.sendBox();
        perishableBox.sendBox();
    }

    private static void calculateCosts() {
        // Посчитать общую стоимость всех доставок и вывести на экран
        int deliveryCost = 0;
        for (Parcel p: allParcels) {
            deliveryCost += p.calculateDeliveryCost();
        }
        System.out.printf("Общая стоимость всех доставок = %d\n", deliveryCost);
    }

    private static void sendTrackMessage() {
        System.out.println("Введите новую локацию посылок");
        String newLocation = scanner.nextLine();
        trackableParcelsForStatus.forEach(p -> p.reportStatus(newLocation));
    }
}

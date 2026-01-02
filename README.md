# ShipIt

## Система статусов посылок
Для корректной работы с посылками (избежание недопустимых повторных действий) в проект добавлена система статусов
Реализация: ``` ParcelStatus```
Состав и последовательность:
* NEW -> Новая
* PACKED -> Упакована
* BOXED -> Добавлена в коробку
* DELIVERED -> Доставлена

При переходе в статус DELIVERED, посылки удаляются из соответствующей коробки.
### Инициаторы перевода в статус

|Статус|Метод|Класс| Условие                                   |
|-|-------|--------|-------------------------------------------|
|NEW|Конструктор|Pacel| -                                         |
|PACKED|packageItem()|Parcel| status == NEW                             |
|BOXED|addParcel()|ParcelBox| status == PACKED+проверка веса коробки    |
|DELIVERED|deliver()|Parecl| status == BOXED                           |

## Шаг 2. Добавляем мониторинг доставки
Здесь ТЗ сильно размыто из-за ограниченности сложности проекта.
Т.к. у нас отсутствует статус "В пути", то невозможно привязать какую-то понятную логику к изменению локации 
отслеживаемых посылок. 
Допускаю, что этот пункт неоходим для проверки навыков работы с интерфейсами, поэтому реализовал в лоб, без проверки 
на статус посылки.

# Parcel (посылка)
Идентификации осуществляется через Description.
Это определение нам важно, когда мы контролируем, чтобы одна и та же посылка не попала в короб.
Поэтому при вводе описания мы проверяем его на уникальность (езе до создания экземпляра класса).

## Проверка

```java
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
```

Если ввод пустой, то пропускаем такт обработки
```java
if (inputDscription.isEmpty()) {
        System.out.println("Описание не может быть пустым");
        continue;
    }
```

Проверяем наличие объекта с таким же описанием
```java
boolean isDescriptionExists = allParcels.stream()
        .anyMatch(p -> p.getDescription().equals(inputDscription));
```

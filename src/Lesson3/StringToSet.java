package Lesson3;

import java.util.*;

public class StringToSet {

    public static void main(String[] args) {
        System.out.println("                --- Задание 1 ---                 ");
        // Исходная строка
        String lorumIpsem = "lorem ipsum dolor sit amet consectetur adipiscing elit dolor " +
                            "sit do eiusmod tempor amet do lorem et dolor amet consectetur dolor";
        stringToSet(lorumIpsem);

        System.out.println("                --- Задание 2 ---                 ");

        String[] namesList = {"Lennon", "McCartney", "Harrison", "Star"};
        List<String> names = new ArrayList<>(Arrays.asList(namesList));

        Integer[] phonesList = {44_151_345,44_151_290,44_151_741,44_151_139};
        List<Integer> phones = new ArrayList<>(Arrays.asList(phonesList));

        PhoneBook phoneBook = new PhoneBook();

        for (int i = 0; i < names.size(); i++) {
                phoneBook.add(new Entry(names.get(i), phones.get(i)));
        }
        // Передача записей в телефонную книжку списком
        phoneBook.add(new ArrayList<>(Arrays.asList(new Entry("Jimmy page", 44_102_611), new Entry("Robert Plant", 44_102_611))));
        System.out.println("************ Вывод всей телефонной книги ************");
        phoneBook.getAll();
        System.out.println("************ Поиск по имени ****************");
        phoneBook.getByName("Star");
        System.out.println("************ Поиск по номеру ***************");
        phoneBook.getByNumber(44_102_611);
    }

    private static void stringToSet(String lorumIpsem) {
        // Строка в массив
        String[] lorumList = lorumIpsem.split("\\s");
        System.out.println("************** Массив слов **************");
        System.out.println(Arrays.toString(lorumList));
        System.out.println("********** Список уникальных слов **********");
        // Множество из списка
        Set<String> lorumSet = new HashSet<>(Arrays.asList(lorumList));
        System.out.println(lorumSet);
        Map<String, Integer> lorumMap = new HashMap<>();
        // Словарь -> каждому ключу : 0
        for (String s : lorumSet) {
            lorumMap.put(s, 0);
        }
        // Апдейт повторений ключа по исходной строке (массиву)
        for (String s : lorumList) {
            lorumMap.put(s, lorumMap.get(s) + 1);
        }
        System.out.println("********** Кол-во повторений слов **********");
        for (Map.Entry<String, Integer> entry : lorumMap.entrySet()) {
            System.out.print(entry.getKey() + ":" + entry.getValue() + " | ");
        }
        System.out.println();
    }
}

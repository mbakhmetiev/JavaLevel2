package Lesson3;

import java.util.*;

public class PhoneBook {
    private HashSet<Entry> phoneDirectory;

    public PhoneBook() {
        this.phoneDirectory = new HashSet<>();
    }

    public void add(Entry entry) {
        this.phoneDirectory.add(entry);
    }

    public void add(ArrayList<Entry> entryList) {
        this.phoneDirectory.addAll(entryList);
    }

    public void getAll() {
        for (Entry entry : phoneDirectory) {
            System.out.println(entry.getName() + " : " + entry.getPhoneNumber());
        }
    }

    public void getByName(String name) {
        int idx = 0;
        for (Entry entry : phoneDirectory) {
            if (entry.getName() == name) {
                System.out.println(entry.getName() + " : " + entry.getPhoneNumber());
                idx++;
            }
        }
        if (idx == 0) System.out.printf("Имя %s не найдено%n", name);
    }

    public void getByNumber(Integer number) {
        int idx = 0;
        for (Entry entry : phoneDirectory) {
            if ((int) entry.getPhoneNumber() == number) {
                System.out.println(entry.getPhoneNumber() + " : " + entry.getName());
                idx++;
            }
        }
        if (idx == 0) System.out.printf("Номер %d не найден%n", number);
    }
}


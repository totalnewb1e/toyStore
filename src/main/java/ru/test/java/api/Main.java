package ru.test.java.api;

public class Main {
    public static void main(String[] args) {
        ToyStore toyStore = new ToyStore();

        toyStore.addNewToy(1, "Car model", 15, 30);
        toyStore.addNewToy(2, "Plush", 10, 10);
        toyStore.addNewToy(3, "Slime", 5, 5);

        toyStore.updateToyWeight(1, 45);

        toyStore.toyLottery();
    }
}

package ru.test.java.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ToyStore {
    private final String FILE_PATH = "toys.txt";
    private final List<Toy> toys;

    public ToyStore() {
        toys = new ArrayList<>();
        loadToysFromFile();
    }

    private void loadToysFromFile() {
        try (Scanner scanner = new Scanner(new File(FILE_PATH))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0]);
                String name = data[1];
                int quantity = Integer.parseInt(data[2]);
                double weight = Double.parseDouble(data[3]);
                Toy toy = new Toy(id, name, quantity, weight);
                toys.add(toy);
            }
        } catch (FileNotFoundException exception) {
            System.out.println("File not found. Starting with an empty toy store.");
        }
    }

    private void saveToysToFile() {
        try (PrintWriter writer = new PrintWriter(FILE_PATH)) {
            for (Toy toy : toys) {
                writer.println(toy.getId() + "," + toy.getName() + "," + toy.getQuantity() + "," + toy.getWeight());
            }
        } catch (FileNotFoundException exception) {
            System.out.println("Failed to save toys to file.");
        }
    }

    public void addNewToy(int id, String name, int quantity, double weight) {
        Toy toy = new Toy(id, name, quantity, weight);
        toys.add(toy);
        saveToysToFile();
    }

    public void updateToyWeight(int id, double weight) {
        for (Toy toy :
                toys) {
            if (toy.getId() == id) {
                toy.setWeight(weight);
                saveToysToFile();
                return;
            }
        }
        System.out.println("Toy not found with ID" + id);
    }

    public void toyLottery() {
        List<Toy> toyLottery = new ArrayList<>();
        double totalWeight = 0;
        for (Toy toy :
                toys) {
            totalWeight += toy.getWeight();
        }
        Random random = new Random();
        while (totalWeight > 0) {
            double randomNum = random.nextDouble() * totalWeight;
            double cumulativeWeight = 0;
            for (Toy toy :
                    toys) {
                cumulativeWeight += toy.getWeight();
                if (randomNum <= cumulativeWeight) {
                    toyLottery.add(toy);
                    toy.decreaseQuantity();
                    totalWeight -= toy.getWeight();
                    break;
                }
            }
        }
        System.out.println("Congratz! You won following toys: ");
        for (Toy toy :
                toyLottery) {
            System.out.println(toy.getName());
        }
        saveToysToFile();
    }
}

//Marcus Hudson
//CS1351 
// Student ID: 892962369
//3/3/2024

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class aOrderedList {
    public static void main(String[] args) {
        Scanner scanner = getInputFile("Enter input filename: ");
        OrderedCarList orderedList = new OrderedCarList();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");
            if (parts.length > 0) {
                String operation = parts[0].trim();
                if (operation.equals("A")) {
                    String make = parts[1].trim();
                    int year = Integer.parseInt(parts[2].trim());
                    int price = Integer.parseInt(parts[3].trim());
                    orderedList.add(new Car(make, year, price));
                } else if (operation.equals("D")) {
                    if (parts.length > 1) {
                        int index = Integer.parseInt(parts[1].trim());
                        orderedList.remove(index);
                    } else {
                        System.out.println("Invalid delete operation format.");
                    }
                }
            }
        }

        PrintWriter writer = getOutputFile("Enter output filename: ");
        writer.println("Number of cars: " + orderedList.size());
        for (int i = 0; i < orderedList.size(); i++) {
            writer.println();
            writer.println(orderedList.get(i));
        }
        writer.close();
    }

    public static Scanner getInputFile(String userPrompt) {
        Scanner scanner = new Scanner(System.in);
        File file;
        do {
            System.out.print(userPrompt);
            String fileName = scanner.nextLine().trim();
            file = new File(fileName);
            if (!file.exists()) {
                System.out.println("File specified <" + fileName + "> does not exist.");
                System.out.print("Would you like to continue? <Y/N> ");
                String response = scanner.nextLine().trim();
                if (!response.equalsIgnoreCase("Y")) {
                    System.out.println("Program execution canceled.");
                    System.exit(0);
                }
            }
        } while (!file.exists());
        try {
            return new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
            System.exit(1);
            return null; // to satisfy compiler
        }
    }

    public static PrintWriter getOutputFile(String userPrompt) {
        Scanner scanner = new Scanner(System.in);
        PrintWriter writer = null;
        while (writer == null) {
            try {
                System.out.print(userPrompt);
                String fileName = scanner.nextLine().trim();
                writer = new PrintWriter(fileName);
            } catch (FileNotFoundException e) {
                System.out.println("File not found: " + e.getMessage());
            }
        }
        return writer;
    }
}

class Car {
    private String make;
    private int year;
    private int price;

    public Car(String make, int year, int price) {
        this.make = make;
        this.year = year;
        this.price = price;
    }

    public String getMake() {
        return make;
    }

    public int getYear() {
        return year;
    }

    public int getPrice() {
        return price;
    }

    public int compareTo(Car other) {
        if (!this.make.equals(other.make))
            return this.make.compareTo(other.make);
        else
            return Integer.compare(this.year, other.year);
    }

    @Override
    public String toString() {
        return "Make: " + make + ", Year: " + year + ", Price: $" + price;
    }
}

class OrderedCarList {
    private final int SIZEINCREMENTS = 20;
    private Car[] oList;
    private int listSize;
    private int numObjects;

    public OrderedCarList() {
        numObjects = 0;
        listSize = SIZEINCREMENTS;
        oList = new Car[SIZEINCREMENTS];
    }

    public void add(Car newCar) {
        if (numObjects >= listSize) {
            increaseSize();
        }
        int index = 0;
        while (index < numObjects && oList[index].compareTo(newCar) < 0) {
            index++;
        }
        for (int i = numObjects; i > index; i--) {
            oList[i] = oList[i - 1];
        }
        oList[index] = newCar;
        numObjects++;
    }

    private void increaseSize() {
        Car[] newList = Arrays.copyOf(oList, listSize + SIZEINCREMENTS);
        oList = newList;
        listSize += SIZEINCREMENTS;
    }

    public int size() {
        return numObjects;
    }

    public Car get(int index) {
        if (index < 0 || index >= numObjects) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        return oList[index];
    }

    public boolean isEmpty() {
        return numObjects == 0;
    }

    public void remove(int index) {
        if (index < 0 || index >= numObjects) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        for (int i = index; i < numObjects - 1; i++) {
            oList[i] = oList[i + 1];
        }
        numObjects--;
    }
}

package homework1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Box<T extends Fruit> {
    private final ArrayList<T> fruits;

    public ArrayList<T> getFruits() {
        return fruits;
    }

    public Box() {
        this.fruits = new ArrayList<>();
    }


    public void put(T fruit) {
        fruits.add(fruit);
    }

    public T take(T fruit) {
        fruits.remove(fruit);
        return fruit;
    }

    public static <H extends Fruit> Box<H> create() {
        return new Box<H>();
    }

    public double getWeight() {
        double weight = 0f;
        for (T fruit : fruits) {
            weight += fruit.getWeight();
        }
        return weight;
    }

    public boolean compareTo(Box box) {
        if (Math.abs(this.getWeight() - box.getWeight()) < 0.01) {
            return true;
        } else {
            return false;
        }
    }


    public void printElements() {
        System.out.println("В коробке лежит:");
        if (this.fruits.size() == 0) {
            System.out.println("Коробка пуста");
        } else {
            for (T fruit : this.fruits) {
                System.out.println("".concat(fruit.getName()).concat(" " + fruit.getWeight()));
            }
        }
    }
}

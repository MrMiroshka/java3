package homework5;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;


public class Car implements Runnable {
    private static int CARS_COUNT;
    private static volatile AtomicBoolean isWinner;
    private CountDownLatch finish;

    static {
        CARS_COUNT = 0;
    }

    private Race race;
    private final CountDownLatch countCarsStart;
    private int speed;
    private String name;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed, CountDownLatch countCarsStart, CountDownLatch finish) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.countCarsStart = countCarsStart;
        this.name = "Участник #" + CARS_COUNT;
        this.isWinner.set(false);
        this.finish = finish;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.countCarsStart.countDown();


        try {
            countCarsStart.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        if (!this.isWinner.get()) {
            this.isWinner.set(true);
            System.out.println(this.name + " - WIN");
        }
        finish.countDown();
    }
}

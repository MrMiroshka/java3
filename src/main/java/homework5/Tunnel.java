package homework5;

import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {
    private Semaphore carsInTunnel;
    public Tunnel(int CARS_COUNT) {
        this.length = 80;
        this.description = "������� " + length + " ������";
        carsInTunnel = new Semaphore(CARS_COUNT/2);
    }


    @Override
    public void go(Car c) {
        try {
            try {
                System.out.println(c.getName() + " ��������� � �����(����): " + description);
                carsInTunnel.acquire();
                System.out.println(c.getName() + " ����� ����: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " �������� ����: " + description);
                carsInTunnel.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

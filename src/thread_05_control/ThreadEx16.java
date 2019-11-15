package thread_05_control;

import java.util.ArrayList;
import java.util.List;

public class ThreadEx16 {
    public static void main(String[] args) throws Exception {
        Table table = new Table(); // 공유 자원
        Thread thread = new Thread(new Cook(table), "COOK1");
        Thread thread1 = new Thread(new Customer(table, "donut"), "CUST1");
        Thread thread2 = new Thread(new Customer(table, "burger"), "CUST2");

        thread.start();
        thread1.start();
        thread2.start();

        Thread.sleep(5000);
        System.exit(0);
    }

}


class Customer implements Runnable {

    private Table table;
    private String food;

    public Customer(Table table, String food) {
        this.table = table;
        this.food = food;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String name = Thread.currentThread().getName();

            table.remove(food);
            System.out.println(name + " ate a " + food);
        }
    }
}


class Cook implements Runnable {
    private Table table;

    public Cook(Table table) {
        this.table = table;
    }

    @Override
    public void run() {
        while (true) {
            int idx = (int) (Math.random() * table.dishNum()); // 임의의 요리를 하나 추가하기위한 랜덤
            table.add(table.dishNames[idx]);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Table {
    String[] dishNames = {"donut", "donut", "burger"};
    final int MAX_FOOD = 6; // 테이블에 둘수있는 음식 최대개수

    private List<String> dishes = new ArrayList<>();

    public synchronized void add(String dish) { // synchronized를 붙이면서 임계영역으로 설정. 동기화
        // 한번에 하나의 쓰레드만 접근가능.
        while (dishes.size() >= MAX_FOOD) {
            String name = Thread.currentThread().getName();
            System.out.println(name + " is waiting ");
            try {
                wait(); // COOK 쓰레드를 기다리게 만든다.
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
        }
        dishes.add(dish);
//        notify(); // waiting pool에 통보해서 랜덤의 쓰레드가 꺠어나게 만든다.
        // notify()는 랜덤의 쓰레드에게만 통보하기때문에 원하는 쓰레드가 깨어나지 못할수도있다.
        notifyAll();
        System.out.println("Dishes : " + dishes.toString());
    }

    public void remove(String dishName) {
        synchronized (this) {
            String name = Thread.currentThread().getName(); // 현재 쓰레드의 이름을 얻어오고
            while (dishes.size() == 0) { // dishes의 size가 0이면
                System.out.println(name + " is waiting");
                try {
                    wait(); // 쓰레드를 잠시 정지시키고 CUST 쓰레드를 기다리게 한다.
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
            }

            while (true) {
                for (int i = 0; i < dishes.size(); i++) {
                    if (dishName.equals(dishes.get(i))) {
                        dishes.remove(i);
//                        notify();// 잠자고있는 COOK 쓰레드를 깨우기위해 waiting pool에 통보
                        // COOK 쓰레드를 꺠워야하지만 다른 CUST 쓰레드가 깨어날수도있다.
                        notifyAll(); // waiting pool 에 있는 모든 대기쓰레드에게 통보한다.
                        return;
                    }
                }

                try {
                    System.out.println(name + "is waiting");
                    wait();
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
            }

        }
    }

    public int dishNum() {
        return dishNames.length;
    }
}
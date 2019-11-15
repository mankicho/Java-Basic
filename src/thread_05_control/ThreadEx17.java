package thread_05_control;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

//동기화 할수있는 방법은 synchronized 블럭 외의 lock 클래스 이용법이 있다.
public class ThreadEx17 {
    public static void main(String[] args) throws Exception {
        Table_17 table = new Table_17();
        new Thread(new Cook_17(table), "COOK1").start();
        new Thread(new Customer_17(table, "donut"), "CUST1").start();
        new Thread(new Customer_17(table, "burger"), "CUST2").start();
        Thread.sleep(2000);
        System.exit(0);
    }
}

class Table_17 {
    String[] dishNames = {"donut", "donut", "burger"};
    final int MAX_FOOD = 6;
    private List<String> dishes = new ArrayList<>();

    private ReentrantLock lock = new ReentrantLock(); // 배타 락 lock 생성.
    private Condition forCook = lock.newCondition(); // 요리사를 위한 Condition
    private Condition forCust = lock.newCondition(); // 고객을 위한 Condition
    // wait와 notify를 활용한 예제는 요리사 쓰레드와 손님 쓰레드를 구분하지못한다.
    // 이 문제를 해결하기위한 Condition 객체

    public void add(String dish) {
        lock.lock(); // 베타락의 락 설정

        try {
            while (dishes.size() >= MAX_FOOD) {
                String name = Thread.currentThread().getName(); // 현재 쓰레드의 이름
                System.out.println(name + " is waiting ");
                try {
                    forCook.await(); // COOK쓰레드를 기다리게 한다.
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
            }
            dishes.add(dish);
            forCust.signal(); // 기다리고있는 Cust 쓰레드를 깨우기 위함.
            System.out.println("DIshes = " + dishes.toString());
        } finally {
            lock.unlock(); // 베타 락 락 해제.
            // 베타락은 직접 설정과 해제를 해줘야한다.
        }
    }

    public void remove(String dishName) {
        lock.lock();
        String name = Thread.currentThread().getName();

        try {
            while (dishes.size() == 0) {
                System.out.println(name + "is waiting");
                try {
                    forCust.await();
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
            }

            while (true) {
                for (int i = 0; i < dishes.size(); i++) {
                    if (dishName.equals(dishes.get(i))) {
                        dishes.remove(i);
                        forCook.signal(); // 대기중인 Cook 쓰레드를 깨운다.
                        return;
                    }
                }

                try {
                    System.out.println(name + " is waiting");
                    forCust.await(); // Cust 쓰레드를 기다리게 한다.
                } catch (InterruptedException e) {
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public int dishNum() {
        return dishNames.length;
    }
}

class Cook_17 implements Runnable {
    private Table_17 table;

    public Cook_17(Table_17 table) {
        this.table = table;
    }

    @Override
    public void run() {
        while (true) {
            int idx = (int) (Math.random() * table.dishNum());
            table.add(table.dishNames[idx]);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
        }
    }
}

class Customer_17 implements Runnable {
    private Table_17 table;
    private String food;

    public Customer_17(Table_17 table, String food) {
        this.table = table;
        this.food = food;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
                String name = Thread.currentThread().getName();
                table.remove(food);
                System.out.println(name + "ate a " + food);
            } catch (InterruptedException e) {
            }
        }

    }
}
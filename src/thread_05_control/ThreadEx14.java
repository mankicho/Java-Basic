package thread_05_control;

import java.awt.*;

//다른 쓰레드의 작업을 기다린다.
// 자신의 쓰레드가 하던 작업을 멈추고 다른 쓰레드가 작업을 수행하도록 할때 join을 호출.
public class ThreadEx14 {
    public static void main(String[] args) {
        ThreadEx14_01 threadEx14_01 = new ThreadEx14_01();
        ThreadEx14_02 threadEx14_02 = new ThreadEx14_02();

        threadEx14_01.start();
        threadEx14_02.start();

        long startTime = System.currentTimeMillis();

        try {
            threadEx14_01.join(); // Main쓰레드가 threadEx14_01 쓰레드의 작업이 끝날때까지 기다린다.
            threadEx14_02.join(); // Main쓰레드가 threadEx14_02 쓰레드의 작업이 끝날때까지 기다린다.

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("소요시간 = " + (System.currentTimeMillis() - startTime));
    }
}

class ThreadEx14_01 extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 300; i++) {
            System.out.print(new String("-"));
        }
    }
}

class ThreadEx14_02 extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 300; i++) {
            System.out.print(new String("l"));
        }
    }
}
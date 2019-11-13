package thread_01_implement;

public class MyThread {
    public static void main(String[] args) {
        ThreadEx1 ex1 = new ThreadEx1();
        Runnable r = new ThreadEx2();
        Thread thread = new Thread(r);

        ex1.start();
        thread.start();
    }
}

class ThreadEx1 extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 15; i++) {
            System.out.println("이거는 = " + getName()); // 쓰레드의 이름..
        }
    }
}

class ThreadEx2 extends Thread {
    @Override
    public void run() {
        for(int i=0;i<15;i++){
            System.out.println("현재 실행중인 = " + Thread.currentThread().getName()); // 현재 실행중인 쓰레드의 이름
        }
    }
}
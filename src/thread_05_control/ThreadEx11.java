package thread_05_control;

public class ThreadEx11 {
    public static void main(String[] args) {
        ThreadEx11_01 threadEx11_01 = new ThreadEx11_01();
        ThreadEx11_02 threadEx11_02 = new ThreadEx11_02();

        threadEx11_01.start();
        threadEx11_02.start();

        try{
            threadEx11_01.sleep(2000); // ThreadEx11_01의 쓰레드를 중지시켰지만 sleep은 현재 쓰레드에 대해 일시중지시키기 때문에 Main쓰레드가 2초간 정지한다.
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("Main Thread 종료.");
    }
}

class ThreadEx11_01 extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 300; i++) {
            System.out.print("-");
        }
        System.out.println("ThreadEx11_01 종료");
    }
}

class ThreadEx11_02 extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 300; i++) {
            System.out.print("l");
        }
        System.out.println("ThreadEx11_02 종료");
    }
}

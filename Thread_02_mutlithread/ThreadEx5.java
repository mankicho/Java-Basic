package Thread_02_mutlithread;

public class ThreadEx5 {

    static long startTime = 0;

    public static void main(String[] args) {
        ThreadEx6 ex6 = new ThreadEx6();
        ex6.start();
        startTime = System.currentTimeMillis();

        for (int i = 0; i < 300; i++) {
            System.out.printf("%s", new String("-"));
        }
        System.out.println("소요시간 1 : " + (System.currentTimeMillis() - ThreadEx5.startTime));
    }
}

class ThreadEx6 extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 300; i++) {
            System.out.printf("%s", new String("ㅣ"));
        }
        System.out.println();
        System.out.print("소요시간 2 : " + (System.currentTimeMillis() - ThreadEx5.startTime));
        System.out.println();
    }
}
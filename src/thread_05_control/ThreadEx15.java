package thread_05_control;

//join의 또다른 예시
public class ThreadEx15 {
    public static void main(String[] args) {
        ThreadEx15_01 threadEx15_01 = new ThreadEx15_01();
        threadEx15_01.setDaemon(true);
        threadEx15_01.start();

        int requireMemory = 0;

        for (int i = 0; i < 20; i++) {

            // 필요한 메모리가 사용할 수 있는 양보다 크거나 전체 메모리의 60%이상을 사용할경우
            // 데몬쓰레드 threadEx15_01 을 깨운다.
            if (threadEx15_01.freeMemory() < requireMemory || threadEx15_01.freeMemory() < threadEx15_01.totalMemory() * 0.4) {
                threadEx15_01.interrupt();
                try {
                    threadEx15_01.join(100); // 0.1초정도 데몬쓰레드가 작업할 시간을 준다. 메인쓰레드는 기다린다.
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            requireMemory = (int) (Math.random() * 10) * 20;

            System.out.println("requireMemory = " + requireMemory);
            threadEx15_01.usedMemory += requireMemory;
            System.out.println("used Memory : " + threadEx15_01.usedMemory);
        }
    }
}

class ThreadEx15_01 extends Thread {
    final static int MAX_MEMORY = 1000;
    int usedMemory = 0;

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10 * 1000); // 10초 기다린다.
            } catch (InterruptedException e) {
                System.out.println("Awaken by interrupt().");
            }
            gc();
            System.out.println("Garbage Collected. Free Memory : " + freeMemory());
        }
    }

    public void gc() {
        usedMemory -= 300;
        if (usedMemory < 0) {
            usedMemory = 0;
        }
    }

    public int totalMemory() {
        return MAX_MEMORY;
    }

    public int freeMemory() {
        return MAX_MEMORY - usedMemory;
    }
}


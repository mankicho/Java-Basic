package thread_05_control;

import javax.swing.*;
// Interrupted가 실행되면 쓰레드의 작업을 취소한다.
public class ThreadEx12 {
    public static void main(String[] args) {
        ThreadEx12_01 threadEx12_01 = new ThreadEx12_01();
        threadEx12_01.start();
        System.out.println(threadEx12_01.isInterrupted());

        String input = JOptionPane.showInputDialog("ㅇㅇ");
        System.out.println(input);
        threadEx12_01.interrupt();
        System.out.println(threadEx12_01.isInterrupted());
    }
}

class ThreadEx12_01 extends Thread {
    int i = 10;

    @Override
    public void run() {
        while (i != 0 && !isInterrupted()) {
            System.out.println(i--);
            try{
                sleep(1000);
                System.out.println("Interrupted = " + isInterrupted());
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println("카운트 종료");
    }
}

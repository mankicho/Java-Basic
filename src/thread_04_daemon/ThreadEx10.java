package thread_04_daemon;

//데몬 쓰레드는 다른 쓰레드의 작업을 돕는 보조역할을한다.
// 일반쓰레드 종료시 데몬쓰레드도 종료.
// 무한루프와 조건문을 이용해 실행 후 대기하고있다가 특정 조건이 만족되면 작업을 수행하고 다시 대기하도록 작성.
public class ThreadEx10 implements Runnable {
    static boolean autoSave = false;

    public static void main(String[] args) {
        Thread thread = new Thread(new ThreadEx10());
        thread.setDaemon(true); // 데몬쓰레드로 설정, thread가 start 되기전에 반드시 setDaemon을 실행해야 한다.
        thread.start();

        for (int i = 0; i <= 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(i);
            if (i == 5) {
                autoSave = true;

            }
        }
        System.out.println("프로그램 종료");
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(3 * 1000); //3초간 지연
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (autoSave) { //3초마다 autoSave가 true인지 확인하고 true면
                autoSave(); // 자동저장.
                break;
            }
        }
    }

    public void autoSave() {
        System.out.println("작업파일 자동저장.");
    }
}

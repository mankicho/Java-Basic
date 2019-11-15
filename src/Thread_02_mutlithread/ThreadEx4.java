package Thread_02_mutlithread;

//싱글 쓰레드와 멀티쓰레드의 차이를 이해하기위한 예제
public class ThreadEx4 {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 300; i++) {
            System.out.printf("%s", new String("-"));
        }
        System.out.println();
        System.out.print("소요시간1:" + (System.currentTimeMillis() - startTime));
        System.out.println();

        for (int i = 0; i < 300; i++) {
            System.out.printf("%s", new String("ㅣ"));
        }
        System.out.println();
        System.out.print("소요시간2: " + (System.currentTimeMillis() - startTime));
        System.out.println();
    }
}

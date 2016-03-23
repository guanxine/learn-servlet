import org.junit.Test;

import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {//注意while处理多个case
            int a = in.nextInt();
            int b = in.nextInt();
            System.out.println(a + b);

        }
    }

    private static int i=0;

    public int calculate(int x){
        System.out.println(++i);
        if (x<=2){
            return 1;
        }else{
            return (calculate(x-1)+calculate(x-1))*2;
        }
    }

    @Test
    public void test(){
        System.out.println(calculate(5));
    }
}
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class ParallelMatMultiply {

    public static int[][] c;
    public static int[][] a;
    public static int[][] b;

    public static int[][] loadMatrix(String filename) throws Exception {
        Scanner s = new Scanner(new FileInputStream(filename));
        int rows = 4;
        int cols = 4;
        int mat[][] = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                mat[i][j] = s.nextInt();
            }

        }
        return mat;
    }

    public static void storeMatrix(int c[][], String filename) throws Exception {
        int rows = c.length;
        int cols = c[0].length;
        PrintWriter pw = new PrintWriter(new FileOutputStream(filename));
        pw.println("rows   " + rows + "      columns     " + cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                pw.print(c[i][j] + "    ");

            }
            pw.println();
        }
        pw.close();
    }

    public static void displayMatrix(int c[][], String filename) throws Exception {
        int rows = c.length;
        int cols = c[0].length;
//            System.out.println("rows   " + rows + "      columns     " + cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(c[i][j] + "    ");

            }
            System.out.println();
        }


    }

    public static class Thread1 extends Thread {

        @Override
        public void run() {
            int m = a.length;
            int n = b[0].length;
            int k = (a.length) / 4;



            for (int i = 0; i <= k; i++) {
                for (int j = 0; j < n; j++) {
                    for (int l = 0; l < b.length; l++) {
                        c[i][j] = c[i][j] + a[i][l] * b[l][j];

                    }

                }

            }
        }
    }

    public static class Thread2 extends Thread {

        @Override
        public void run() {
            int m = a.length;
            int n = b[0].length;
            int k = (a.length) / 2+1;
            int s = ((a.length) /4)+1;



            for (int i = s ; i < k; i++) {
                for (int j = 0; j < n; j++) {
                    for (int l = 0; l < b.length; l++) {
                        c[i][j] = c[i][j] + a[i][l] * b[l][j];

                    }

                }

            }
        }
    }
    public static class Thread3 extends Thread {

        @Override
        public void run() {
            int m = a.length;
            int n = b[0].length;
            int k = ((3*(a.length))/4)+ 1;
            int s = (a.length) / 2 + 1;



            for (int i = s ; i < k; i++) {
                for (int j = 0; j < n; j++) {
                    for (int l = 0; l < b.length; l++) {
                        c[i][j] = c[i][j] + a[i][l] * b[l][j];

                    }

                }

            }
        }
    }
    public static class Thread4 extends Thread {

        @Override
        public void run() {
            int m = a.length;
            int n = b[0].length;
            int k = ((3*(a.length))/4)+ 1;



            for (int i = k ; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    for (int l = 0; l < b.length; l++) {
                        c[i][j] = c[i][j] + a[i][l] * b[l][j];

                    }

                }

            }
        }
    }

    public static void main(String[] args) throws Exception {
        long t1 = System.currentTimeMillis();
        a = loadMatrix("a.txt");
        b = loadMatrix("b.txt");
        c= new int [a.length][b[0].length];

        Thread1 m1 = new Thread1();
        Thread2 m2 = new Thread2();
        Thread3 m3 = new Thread3();
        Thread4 m4 = new Thread4();
        m1.start();
        m2.start();
        m3.start();
        m4.start();
        m1.join();
        m2.join();
        m3.join();
        m4.join();

        storeMatrix(c, "xx.txt");
        long t2 = System.currentTimeMillis();
        System.out.println("the time is "+(t2-t1));

    }
}

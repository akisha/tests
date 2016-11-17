import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public final class Matrices {

    public static final Random RANDOM = new SecureRandom();

    private Matrices() {
    }

    public static Matrix generate(int height, int width, int min, int max) {
        final Matrix result = new Matrix(height, width);
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                result.set(i, j, min + RANDOM.nextInt(max - min));
            }
        }
        return result;
    }

    public static Matrix multiply(Matrix a, Matrix b) {
        if (a.getWidth() != b.getHeight()) {
            throw new IllegalArgumentException("first matrix width should be equal to second matrix height");
        }
        final Matrix result = new Matrix(a.getHeight(), b.getWidth());
        for (int i = 0; i < a.getHeight(); ++i) {
            for (int j = 0; j < b.getWidth(); ++j) {
                for (int r = 0; r < a.getWidth(); ++r) {
                    result.set(i, j, result.get(i, j) + a.get(i, r) * b.get(r, j));
                }
            }
        }
        return result;
    }

    public static Matrix parallelMultiply(final Matrix a, final Matrix b) throws InterruptedException {
        if (a.getWidth() != b.getHeight()) {
            throw new IllegalArgumentException("first matrix width should be equal to second matrix height");
        }
        final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        final Matrix result = new Matrix(a.getHeight(), b.getWidth());
        for (int i = 0; i < a.getHeight(); ++i) {
            for (int j = 0; j < b.getWidth(); ++j) {
                executorService.submit(new ParallelMatrixMultiplier(a, b, result, i, j));
            }
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.DAYS);
        return result;
    }

    private static class ParallelMatrixMultiplier implements Runnable {
        private final Matrix a;
        private final Matrix b;
        private final Matrix result;
        private final int i;
        private final int j;

        private ParallelMatrixMultiplier(Matrix a, Matrix b, Matrix result, int i, int j) {
            this.a = a;
            this.b = b;
            this.result = result;
            this.i = i;
            this.j = j;
        }

        @Override
        public void run() {
            for (int r = 0; r < a.getWidth(); ++r) {
                result.set(i, j, result.get(i, j) + a.get(i, r) * b.get(r, j));
            }
        }
    }

}
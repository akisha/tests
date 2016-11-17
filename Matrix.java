import java.util.Arrays;

public class Matrix {

    private final double[][] data;

    public Matrix(int height, int width) {
        this.data = new double[height][width];
    }

    public Matrix(double[][] data) {
        this.data = copy(data);
    }

    public int getHeight() {
        return data.length;
    }

    public int getWidth() {
        return data[0].length;
    }

    public double get(int row, int col) {
        return data[row][col];
    }

    public void set(int row, int col, double value) {
        data[row][col] = value;
    }

    /**
     * Return a new copy of specified row
     *
     * @param n row number to copy
     * @return a new instance of double array
     */
    public double[] getRow(int n) {
        return Arrays.copyOf(data[n], data[n].length);
    }

    /**
     * Return a new copy of specified column
     *
     * @param n column number to copy
     * @return a new instance of double array
     */
    public double[] getColumn(int n) {
        double [] result = new double[data.length];
        for (int i = 0; i < data.length; ++i) {
            result[i] = data[i][n];
        }
        return result;
    }

    /**
     * Returns a copy of matrix data in form of a two-dimension array
     *
     * @return a new two-dimension array of doubles
     */
    public double[][] getAsDoubleArray() {
        return copy(data);
    }

    private double[][] copy(double[][] data) {
        double[][] result = new double[data.length][];
        for (int i = 0; i < data.length; ++i) {
            result[i] = Arrays.copyOf(data[i], data[i].length);
        }
        return result;
    }

    @Override
    public String toString() {
        return "Matrix{" +
                "data=" + Arrays.deepToString(data) +
                '}';
    }
}

/**
 * Created by alexi on 5/9/2016.
 */
public class MultiplyRunnable implements Runnable{
    private int[][] matrixA;
    private int[][] matrixB;
    private int row;
    private int col;
    private Solution solution;

    public MultiplyRunnable(int[][] ma, int[][] mb, int r, int c, Solution solution){
        matrixA = ma;
        matrixB = mb;
        row = r;
        col = c;
        this.solution = solution;
    }

    public void run(){
        int[] suba = new int[matrixA[0].length];
        int[] subb = new int[matrixB.length];
        int total = 0;
        for(int i=0;i<matrixB.length;i++){
            suba[i] = matrixA[row][i];
            subb[i] = matrixB[i][col];
            total += suba[i]*subb[i];
        }
        solution.setDeterminant(total);
    }
}

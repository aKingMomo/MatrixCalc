/**
 * Created by alexi on 5/8/2016.
 */
public class DeterminantRunnable implements Runnable {
    private int[][] matrix;
    private Solution solution;
    private int startRow;
    private int startColumn;
    private String dir;

    public DeterminantRunnable(int[][] matrix, Solution solution, int startRow, int startColumn, String dir){
        this.matrix = matrix;
        this.solution = solution;
        this.startRow = startRow;
        this.startColumn = startColumn;
        this.dir = dir;
    }

    public void run() {
        try {
            int rows = matrix.length;
            int[] subset = new int[rows];
            int c=0; int a=startRow; int b=startColumn;
            while(c<rows){
                subset[c] = matrix[a][b];
                a = (a+1)%rows;
                if(dir.equals("RIGHT")){
                    b = (b+1)%rows;
                }else{
                    b = (b-1);
                }
                c++;
            }
            int num = multiply(subset);
            System.out.println(startRow + ","+ startColumn + " *~* " + num);
            solution.setDeterminant(num);
            Thread.sleep(5);
        }catch (InterruptedException e){

        }
    }

    private int multiply(int[] set) {
        int total = 1;
        for (int i = 0; i < set.length; i++) {
            total *= set[i];
        }
        return total;
    }
}

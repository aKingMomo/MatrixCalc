/**
 * Created by alexi on 5/8/2016.
 */


public class MatrixCalculations implements Runnable {
    private int[][] matrix;
    private Solution solution;
    private String operation;

    public void run(){
        try{
            if(operation.equals("DETERMINANT")){
                findDeterminant();
            } else if (operation.equals("MULTIPLYBYC")) {
                multiply();
            } else if (operation.equals("MULTIPLYAB")) {
                multiplyAB();
            } else if (operation.equals("RAISEPOWER")) {
                raiseToPower();
            } else if (operation.equals("ADD")) {
                add();
            } else if (operation.equals("SUB")) {
                sub();
            }
            Thread.sleep(5);
        }catch (InterruptedException e){

        }
    }

    private void sub() {
        int[][] matrixA = matrix;
        int[][] matrixB = solution.getMatrix();
        int[][] sol = new int[matrixA.length][matrixA[0].length];
        for(int i=0;i<matrixA.length;i++){
            for(int j=0;j<matrixA[0].length;j++){
                sol[i][j] = matrixA[i][j] - matrixB[i][j];
            }
        }
        solution.setMatrix(sol);
        solution.setDone(true);
    }

    private void add() {
        int[][] matrixA = matrix;
        int[][] matrixB = solution.getMatrix();
        int[][] sol = new int[matrixA.length][matrixA[0].length];
        for(int i=0;i<matrixA.length;i++){
            for(int j=0;j<matrixA[0].length;j++){
                sol[i][j] = matrixA[i][j] + matrixB[i][j];
            }
        }
        solution.setMatrix(sol);
        solution.setDone(true);
    }

    private void raiseToPower() {
        int power = solution.getDeterminant();
        int[][] startMatrix = matrix;
        solution.setMatrix(matrix);
        for(int i=1;i<power;i++){
            //matrix a
            matrix = solution.getMatrix();
            //matrix b
            solution.setMatrix(startMatrix);
            multiplyAB();
        }
        solution.setDone(true);
    }

    private void multiplyAB() {
        int[][] matrixA = matrix;
        int[][] matrixB = solution.getMatrix();
        Solution[][] sol = new Solution[matrixB.length][matrixB[0].length];
        for(int i=0;i<matrixB.length;i++){
            for(int j=0;j<matrixB[0].length;j++){
                sol[i][j] = new Solution();
                MultiplyRunnable m = new MultiplyRunnable(matrixA,matrixB,i,j,sol[i][j]);
                Thread a  = new Thread(m);
                a.start();
            }
        }
        int[][] ans = new int[matrixB.length][matrixB[0].length];
        for(int i=0;i<matrixB.length;i++) {
            for (int j = 0; j < matrixB[0].length; j++) {
                while (!sol[i][j].getIsEdited()){}
                ans[i][j] = sol[i][j].getDeterminant();
            }
        }
        solution.setMatrix(ans);
        solution.setDone(true);
    }

    private void multiply() {
        int c = solution.getDeterminant();
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] sol = new int[rows][cols];
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                sol[i][j] = matrix[i][j]*c;
            }
        }
        solution.setMatrix(sol);
        solution.setDone(true);
    }

    private void findDeterminant(){
        int rows = matrix.length;
        Solution[] sub1 = new Solution[rows];
        Solution[] sub2 = new Solution[rows];
        for (int i=0;i<rows;i++){
            sub1[i] = new Solution();
            sub2[i] = new Solution();
            DeterminantRunnable sub = new DeterminantRunnable(matrix,sub1[i],0,i,"RIGHT");
            DeterminantRunnable bsub = new DeterminantRunnable(matrix,sub2[i],i,rows-1,"LEFT");
            Thread a = new Thread(sub);
            Thread b = new Thread(bsub);
            a.start();
            b.start();
        }
        int pos = 0; int neg = 0;
        for (int j=0;j<rows;j++){
            while (!sub2[j].getIsEdited()){System.out.println("waiting");}
            pos+=sub1[j].getDeterminant();
            neg+=sub2[j].getDeterminant();
        }
        System.out.println("pos "+pos+" neg "+neg);
        solution.setDeterminant(pos-neg);
        solution.setDone(true);
    }

    public MatrixCalculations(int[][] matrix, Solution solution, String operation) {
        this.matrix = matrix;
        this.solution = solution;
        this.operation = operation;
    }







}

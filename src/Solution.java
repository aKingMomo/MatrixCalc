/**
 * Created by alexi on 5/8/2016.
 */
public class Solution {
    private int[][] matrix;
    private int determinant;
    private int[] set;
    private boolean edited;
    private boolean done;

    public Solution() {
        edited = false;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
        edited = true;
        done = false;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setDeterminant(int determinant) {
        this.determinant = determinant;
        edited = true;
    }

    public int getDeterminant() {
        return determinant;
    }


    public int[] getSet() {
        return set;
    }

    public void setSet(int[] set) {
        this.set = set;
        edited = true;
    }

    public boolean getIsEdited() {
        return edited;
    }

    public void setEdited(boolean edit) {
        edited = edit;
    }


    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

}

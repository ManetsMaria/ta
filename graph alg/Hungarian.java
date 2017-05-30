import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;
import java.util.*;

public class Demo {

     public static void main(String[]args) throws FileNotFoundException{
        PrintStream filewriter=new PrintStream ("output.txt");
        Scanner in = new Scanner(new File("input.txt"));
        String f=in.nextLine();
        String s=in.nextLine();
        String [] firstCompany= f.split(" ");
        String [] secondCompany = s.split(" ");
        int c= Math.max(firstCompany.length, secondCompany.length);
        double [][] matrix = new double [c][c];
        double [][] copy=new double[c][c];
        for (int i=0; i<c; i++){
            if (firstCompany.length>i){
                for (int j=0; j<c; j++){
                    if (secondCompany.length>j){
                        matrix[i][j]=Math.abs(Double.valueOf(firstCompany[i])-Double.valueOf(secondCompany[j]));
                        copy[i][j]=Math.abs(Double.valueOf(firstCompany[i])-Double.valueOf(secondCompany[j]));
                    }
                    else{
                        matrix[i][j]=Double.valueOf(firstCompany[i])/2.0;
                        copy[i][j]=Double.valueOf(firstCompany[i])/2.0;
                    }
                }
            }
            else{
                for (int j=0; j<c; j++){
                    matrix[i][j]=Double.valueOf(secondCompany[j])/2.0;
                    copy[i][j]=Double.valueOf(secondCompany[j])/2.0;
                }
            }
        }
        /*for (int i=0; i<c; i++){
            for (int j=0; j<c; j++){
                System.out.print(matrix[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }*/
        double sum=0;
        int [][] answer=(new Demo(matrix)).execute();
        for (int i=0; i<answer.length; i++){
            sum+=copy[answer[i][0]][answer[i][1]];
        }
        filewriter.print(sum);
    }

    private int rows;
    private int cols;
    private boolean[][] prime;
    private boolean[] colsCover;
    private double[][] cost;
    private boolean[][] star;
    private boolean[] rowsCover;

    public Demo(double cost[][]) {
        this.cost = cost;
        rows = cost.length;
        cols = cost[0].length;
        prime = new boolean[rows][cols];
        star = new boolean[rows][cols];
        rowsCover = new boolean[rows];
        colsCover = new boolean[cols];
        for (int j = 0; j < cols; j++) {
            colsCover[j] = false;
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                prime[i][j] = false;
                star[i][j] = false;
            }
        }
        for (int i = 0; i < rows; i++) {
            rowsCover[i] = false;
        }
    }

    public int[][] execute() {
        colMin();

        this.findStars(); 
        this.resetCovered(); 
        this.coverStarredZeroCols(); 

        while (!allColsCovered()) {
            int[] primedLocation = this.primeUncoveredZero();
            if (primedLocation[0] == -1) {
                this.minUncoveredRowsCols(); 
                primedLocation = this.primeUncoveredZero(); 
            }

            int primedRow = primedLocation[0];
            int starCol = this.findStarColInRow(primedRow);
            if (starCol != -1) {
                rowsCover[primedRow] = true;
                colsCover[starCol] = false;
            } else {
                this.augmentPathStartingAtPrime(primedLocation);
                this.resetCovered();
                this.resetPrimes();
                this.coverStarredZeroCols();
            }
        }

        return this.starsToAssignments();

    }

    private void resetPrimes() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                prime[i][j] = false;
            }
        }
    }

    private void resetCovered() {
        for (int i = 0; i < rows; i++) {
            rowsCover[i] = false;
        }
        for (int j = 0; j < cols; j++) {
            colsCover[j] = false;
        }
    }


    private int[][] starsToAssignments() {
        int[][] toRet = new int[cols][];
        for (int j = 0; j < cols; j++) {
            toRet[j] = new int[] {
                this.findStarRowInCol(j), j
            };
        }
        return toRet;
    }

    private void findStars() {
        boolean[] rowStars = new boolean[rows];
        boolean[] colStars = new boolean[cols];

        for (int i = 0; i < rows; i++) {
            rowStars[i] = false;
        }
        for (int j = 0; j < cols; j++) {
            colStars[j] = false;
        }

        for (int j = 0; j < cols; j++) {
            for (int i = 0; i < rows; i++) {
                if (cost[i][j] == 0 && !rowStars[i] && !colStars[j]) {
                    star[i][j] = true;
                    rowStars[i] = true;
                    colStars[j] = true;
                    break;
                }
            }
        }
    }


    private int[] primeUncoveredZero() {
        int[] location = new int[2];

        for (int i = 0; i < rows; i++) {
            if (!rowsCover[i]) {
                for (int j = 0; j < cols; j++) {
                    if (!colsCover[j]) {
                        if (cost[i][j] == 0) {
                            prime[i][j] = true;
                            location[0] = i;
                            location[1] = j;
                            return location;
                        }
                    }
                }
            }
        }

        location[0] = -1;
        location[1] = -1;
        return location;
    }

    private void augmentPathStartingAtPrime(int[] location) {
        ArrayList < int[] > primeLocations = new ArrayList < int[] > (rows + cols);
        ArrayList < int[] > starLocations = new ArrayList < int[] > (rows + cols);
        primeLocations.add(location);

        int currentRow = location[0];
        int currentCol = location[1];
        while (true) { 
            int starRow = findStarRowInCol(currentCol);
            if (starRow == -1) {
                break;
            }
            int[] starLocation = new int[] {
                starRow, currentCol
            };
            starLocations.add(starLocation);
            currentRow = starRow;

            int primeCol = findPrimeColInRow(currentRow);
            int[] primeLocation = new int[] {
                currentRow, primeCol
            };
            primeLocations.add(primeLocation);
            currentCol = primeCol;
        }

        unStarLocations(starLocations);
        starLocations(primeLocations);
    }


    private void minUncoveredRowsCols() {
        double minUncovered = Double.MAX_VALUE;
        for (int i = 0; i < rows; i++) {
            if (!rowsCover[i]) {
                for (int j = 0; j < cols; j++) {
                    if (!colsCover[j]) {
                        if (cost[i][j] < minUncovered) {
                            minUncovered = cost[i][j];
                        }
                    }
                }
            }
        }

        for (int i = 0; i < rows; i++) {
            if (rowsCover[i]) {
                for (int j = 0; j < cols; j++) {
                    cost[i][j] = cost[i][j] + minUncovered;

                }
            }
        }

        for (int j = 0; j < cols; j++) {
            if (!colsCover[j]) {
                for (int i = 0; i < rows; i++) {
                    cost[i][j] = cost[i][j] - minUncovered;
                }
            }
        }
    }

    

    private int findStarRowInCol(int theCol) {
        for (int i = 0; i < rows; i++) {
            if (star[i][theCol]) {
                return i;
            }
        }
        return -1;
    }


    private int findStarColInRow(int theRow) {
        for (int j = 0; j < cols; j++) {
            if (star[theRow][j]) {
                return j;
            }
        }
        return -1;
    }

private void starLocations(ArrayList < int[] > locations) {
        for (int k = 0; k < locations.size(); k++) {
            int[] location = locations.get(k);
            int row = location[0];
            int col = location[1];
            star[row][col] = true;
        }
    }

    private void unStarLocations(ArrayList < int[] > starLocations) {
        for (int k = 0; k < starLocations.size(); k++) {
            int[] starLocation = starLocations.get(k);
            int row = starLocation[0];
            int col = starLocation[1];
            star[row][col] = false;
        }
    }

    private int findPrimeColInRow(int theRow) {
        for (int j = 0; j < cols; j++) {
            if (prime[theRow][j]) {
                return j;
            }
        }
        return -1;
    }

    private boolean allColsCovered() {
        for (int j = 0; j < cols; j++) {
            if (!colsCover[j]) {
                return false;
            }
        }
        return true;
    }

    private void coverStarredZeroCols() {
        for (int j = 0; j < cols; j++) {
            colsCover[j] = false;
            for (int i = 0; i < rows; i++) {
                if (star[i][j]) {
                    colsCover[j] = true;
                    break; 
                }
            }
        }
    }

    private void colMin() {
        for (int i = 0; i < rows; i++) { 
            double rowMin = Double.MAX_VALUE;
            for (int j = 0; j < cols; j++) {
                if (cost[i][j] < rowMin) {
                    rowMin = cost[i][j];
                }
            }
            for (int j = 0; j < cols; j++) {
                cost[i][j] = cost[i][j] - rowMin;
            }
        }

        for (int j = 0; j < cols; j++) { 
            double colMin = Double.MAX_VALUE;
            for (int i = 0; i < rows; i++) { 
                if (cost[i][j] < colMin) {
                    colMin = cost[i][j];
                }
            }
            for (int i = 0; i < rows; i++) { 
                cost[i][j] = cost[i][j] - colMin;
            }
        }
    }
}


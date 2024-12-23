import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Forest {
    private int rows;
    private int columns;
    private String[][] grid;
    private List<int[]> fireSopts;
    private double probability;

    public Forest(int rows, int columns, double probability, List<int[]> fireSopts) {
        this.rows = rows;
        this.columns = columns;
        this.grid = new String[rows][columns];
        this.probability = probability;
        this.fireSopts = fireSopts;

        for (String[] line : grid) {
            Arrays.fill(line, "A");
        }
        fireSopts.forEach(casePosition -> grid[casePosition[0]][casePosition[1]] = "F");
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public String[][] getGrid() {
        return grid;
    }

    public void setGrid(String[][] grid) {
        this.grid = grid;
    }

    public List<int[]> getFireSopts() {
        return fireSopts;
    }

    public void setFireSopts(List<int[]> fireSopts) {
        this.fireSopts = fireSopts;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public void printGrid(){
        for (String[] strings : grid) {
            for (String string : strings) {
                System.out.print(string + "\t");
            }
            System.out.println();
        }
    }

    public boolean fireFree(){
        for (String[] strings : grid) {
            for (String string : strings) {
                if (string.equals("F")) {
                    return false;
                }
            }
        }
        return true;
    }

    public void spreadFire(){
        List<List<Integer>> fireSpot = new ArrayList<>();
        for(int i = 0; i< grid.length; i++){
            for (int j = 0; j< grid[i].length; j++){
                if (grid[i][j].equals("F")){
                    fireSpot.add(List.of(i,j));
                }
            }
        }
        fireSpot.forEach(couple ->{
            upadteFire(grid, couple.getFirst(), couple.getLast(),probability);
            grid[couple.getFirst()][couple.getLast()] = "C";
        });
    }

    private static void upadteFire(String[][] myGrid, int i, int j, double probability) {
        Random random = new Random();
        int[][] directions = new int[][]{{-1, 0},{1, 0},{0, -1},{0, 1}};
        for (int[] direction : directions) {
            int newi = i + direction[0];
            int newj = j + direction[1];
            if (isInBounds(myGrid, newi, newj) && !myGrid[newi][newj].equals("C")) {
                if (random.nextDouble() <= probability) myGrid[newi][newj] = "F";
            }
        }
    }
    public static boolean isInBounds(String[][] grid, int i, int j) {
        return i >= 0 && i < grid.length && j >= 0 && j < grid[i].length;
    }
}

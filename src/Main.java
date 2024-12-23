import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        String fileName = "config.txt";
        GridConfig config = readConfig(fileName);

        String[][] grid = new String[config.dimensions[0]][config.dimensions[1]];

        int step=1;

        boolean noFire=false;

        for (String[] line : grid) {
            Arrays.fill(line, "A");
        }
        config.casesEnFeu.forEach(casePosition -> grid[casePosition[0]][casePosition[1]] = "F");

        do {

            System.out.println("Etape "+step);
            for (String[] strings : grid) {
                for (String string : strings) {
                    System.out.print(string + "\t");
                }
                System.out.println();
            }
            if (fireFree(grid)) noFire= true;
            spreadFire(grid, config.probabilitePropagation);
            step++;
        }while (!noFire);
    }

    public static void spreadFire(String[][] myGrid,double probability){
        List<List<Integer>> fireSpot = new ArrayList<>();
        for(int i = 0; i< myGrid.length; i++){
            for (int j = 0; j< myGrid[i].length; j++){
                if (myGrid[i][j].equals("F")){
                    fireSpot.add(List.of(i,j));
                }
            }
        }
        fireSpot.forEach(couple ->{
            upadteFire(myGrid, couple.getFirst(), couple.getLast(),probability);
            myGrid[couple.getFirst()][couple.getLast()] = "C";
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

    public static boolean fireFree(String[][] myGrid){
        for (String[] strings : myGrid) {
            for (String string : strings) {
                if (string.equals("F")) {
                    return false;
                }
            }
        }
        return true;
    }

    public static GridConfig readConfig(String fileName) {
        GridConfig config = new GridConfig();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#") || line.trim().isEmpty()) {
                    continue;
                }

                if (line.startsWith("dimensions=")) {
                    String[] dimensions = line.split("=")[1].split(",");
                    config.dimensions[0] = Integer.parseInt(dimensions[0].trim());
                    config.dimensions[1] = Integer.parseInt(dimensions[1].trim());
                }

                if (line.startsWith("cases_en_feu=")) {
                    String[] cases = line.split("=")[1].split(";");
                    for (String casePosition : cases) {
                        String[] position = casePosition.split(",");
                        int row = Integer.parseInt(position[0].trim());
                        int col = Integer.parseInt(position[1].trim());
                        config.casesEnFeu.add(new int[]{row, col});
                    }
                }


                if (line.startsWith("probabilite_propagation=")) {
                    config.probabilitePropagation = Double.parseDouble(line.split("=")[1].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return config;
    }
}

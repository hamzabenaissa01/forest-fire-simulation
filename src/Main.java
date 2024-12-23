import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        String fileName = "config.txt";
        GridConfig config = readConfig(fileName);

        int step=1;

        boolean noFire=false;

        Forest forest = new Forest(config.dimensions[0],config.dimensions[1],config.probabilitePropagation,config.casesEnFeu);

        System.out.println("*************Terminologie*************");
        System.out.println("A : Arbre non brûlé \n");
        System.out.println("F : Arbre en feu \n");
        System.out.println("C : Cendres");
        System.out.println("*************++++++++++++*************");

        do {
            System.out.println("Etape "+step);
            forest.printGrid();
            if (forest.fireFree()) noFire= true;
            forest.spreadFire();
            step++;
        }while (!noFire);
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

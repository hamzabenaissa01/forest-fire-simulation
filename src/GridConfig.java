import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GridConfig {
    int[] dimensions = new int[2];
    List<int[]> casesEnFeu = new ArrayList<>();
    double probabilitePropagation;


    public GridConfig() {
    }

    public GridConfig(int[] dimensions, List<int[]> casesEnFeu, double probabilitePropagation) {
        this.dimensions = dimensions;
        this.casesEnFeu = casesEnFeu;
        this.probabilitePropagation = probabilitePropagation;
    }

    public int[] getDimensions() {
        return dimensions;
    }

    public void setDimensions(int[] dimensions) {
        this.dimensions = dimensions;
    }

    public List<int[]> getCasesEnFeu() {
        return casesEnFeu;
    }

    public void setCasesEnFeu(List<int[]> casesEnFeu) {
        this.casesEnFeu = casesEnFeu;
    }

    public double getProbabilitePropagation() {
        return probabilitePropagation;
    }

    public void setProbabilitePropagation(double probabilitePropagation) {
        this.probabilitePropagation = probabilitePropagation;
    }

    @Override
    public String toString() {
        return "GridConfig{" +
                "dimensions=" + Arrays.toString(dimensions) +
                ", casesEnFeu=" + casesEnFeu +
                ", probabilitePropagation=" + probabilitePropagation +
                '}';
    }
}

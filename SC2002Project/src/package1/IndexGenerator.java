package package1;

public class IndexGenerator {
    private static int currIndex = 1;
    public static int getIndex() {
        return currIndex++;
    }
}

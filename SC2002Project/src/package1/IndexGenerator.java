package package1;

public class IndexGenerator {
    private static int index = 1;
    public static int getIndex() {
        return index++;
    }
}

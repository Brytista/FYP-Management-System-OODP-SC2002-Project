package package1;

/**
 * The IndexGenerator class provides functionality for generating unique index values for project ID.
 */
public class IndexGenerator {
    /**
     * The current index value.
     */
    private static int currIndex = 1;
    
    /**
     * Returns the current index value and increments it.
     *
     * @return the current index value
     */
    public static int getIndex() {
        return currIndex++;
    }
}

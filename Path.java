/**
 * Path class stores path data, which includes the start airport, end airport, and distance between them.
 */
public class Path implements PathInterface {

    private String start; // 3-letter code of start airport
    private String end; // 3-letter code of end airport
    private int distance; // Distance between start and end airports in kilometers

    /**
     * Constructor method for Path object
     * @param start 3-letter code of start airport
     * @param end 3-letter code of end airport
     * @param distance Distance between start and end airports in kilometers
     */
    public Path(String start, String end, int distance) {
        this.start = start;
        this.end = end;
        this.distance = distance;
    }

    /**
     * Getter method for start airport
     * @return 3-letter code of start airport
     */
    @Override
    public String getStart() {
        return start;
    }

    /**
     * Getter method for end airport
     * @return 3-letter code of end airport
     */
    @Override
    public String getEnd() {
        return end;
    }

    /**
     * Getter method for distance between start and end airports
     * @return distance between start and end airports in kilometers
     */
    @Override
    public int getDistance() {
        return distance;
    }
}

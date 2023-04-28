public class PathBD implements PathInterface{
    String start;
    String end;
    int distance;
    
    public PathBD(String start, String end, int distance) {
        this.start = start;
        this.end = end;
        this.distance = distance;
    }

    public String getStart() {
        return start;
    }

	public String getEnd() {
        return end;
    }

	public int getDistance() {
        return distance;
    }

    public String toString() {
        return start + " --" + distance + "-> " + end;
    }
}

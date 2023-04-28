// Stores path data and each will represent an edge
public class PathFD implements PathInterface {
  String start;
  String end;
  String distance;
  
  public PathFD (String start, String end, int distance) {
    this.start = start;
    this.end = end;
    this.distance = Integer.toString(distance);
  }
  
  @Override
  public String getStart() {return start;}
  
  @Override
  public String getEnd() { return end; }
    
  
  @Override
  public int getDistance() { return Integer.parseInt(distance); }
}

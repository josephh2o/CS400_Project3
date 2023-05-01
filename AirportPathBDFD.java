import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AirportPathBDFD implements AirportPathInterface {


  @Override
  public boolean insertEdge(String predecessor, String successor, double weight) {
    return false;
  }

  @Override
  public boolean insertNode(String data) {
    return false;
  }

  @Override
  public List<String> getShortestPath(String start, String end) { 
    ArrayList<String> toRet = new ArrayList<String>();
    String startEnd = start+end;
    if (startEnd.equals("HLNLAX")) {
      toRet.add("HLN");
      toRet.add("MKE");
      toRet.add("LAX");
    } else if (startEnd.equals("HLNMKE")) {
      toRet.add("HLN");
      toRet.add("MKE");
    } else if (startEnd.equals("MKELAX")) {
      toRet.add("MKE");
      toRet.add("LAX");
    }
    else if (startEnd.equals("LAXMKE")) {
      toRet.add("LAX");
      toRet.add("MKE");
    } else {
      toRet.add(start);
      toRet.add(end);
    }
    
    return  toRet;
  }
  
  @Override
  public double getShortestDistance(String start, String end) { 
    return 20.1;
  }
  
  @Override
  public List<String> getShortestTrioPath(String uno, String dos, String tres) { 
    String startMidEnd = uno+dos+tres;
    ArrayList<String> toRet = new ArrayList<String>();
    if (startMidEnd.equals("MKELAXHLN")) {
      toRet.add("MKE");
      toRet.add("LAX");
      toRet.add("MKE");
      toRet.add("HLN");
      return toRet;
    } // else 
    
    else if (startMidEnd.equals("LAXMKEEWR")) {
      toRet.add("LAX");
      toRet.add("MKE");
      toRet.add("ORF");
      toRet.add("EWR");
      return toRet;
    }
    
    
    toRet.add(uno);
    toRet.add(dos);
    toRet.add(tres);
    return  toRet;
  }
  
  @Override
  public List<AirportInterface> getAirports() { 
    ArrayList<AirportInterface> list = new ArrayList<AirportInterface>();
    list.add(new AirportFD("MKE", "Milwaukee", (float)43.03, (float)87.9));
    list.add(new AirportFD("LAX", "Los Angeles", (float)34.04, (float)118.25 ));
    list.add(new AirportFD("HLN", "Helena", (float)46.5, (float)112.0 ));
    
    list.add(new AirportFD("ORF", "Norfolk", (float)37.0, (float)76.2 ));
    list.add(new AirportFD("DFW", "Dallas", (float)33.0, (float)97.0 ));
    list.add(new AirportFD("EWR", "Newark", (float)40.67, (float)74.17 ));
    return list;
  }
  
  @Override
  public List<PathInterface> getPaths() { 
    List<PathInterface> toRet = new ArrayList<PathInterface>();
    toRet.add(new PathFD("LAX", "MKE", 10));
    toRet.add(new PathFD("HLN", "MKE", 5));
    toRet.add(new PathFD("MKE", "HLN", 3));
    toRet.add(new PathFD("MKE", "LAX", 10));
    toRet.add(new PathFD("MKE", "ORF", 5));
    toRet.add(new PathFD("ORF", "EWR", 2));
    toRet.add(new PathFD("LAX", "DFW", 7));
    toRet.add(new PathFD("DFW", "ORF", 5));
    return toRet; 
    }
  
  
//get airport code from frontend and find airport’s full name 
  @Override
  public String getFullAirportName(String airportCode) { return null; }
  


}

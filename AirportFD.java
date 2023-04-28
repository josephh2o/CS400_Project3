// Stores airport data and each will represents a node
public class AirportFD  implements AirportInterface {
  String code;
  String name;
  float latitude;
  float longitude;
  
  public AirportFD(String code, String name, float latitude, float longitude) {
    this.code = code;
    this.name = name;
    this.latitude = latitude;
    this.longitude = longitude;    
  }
  
    @Override
    public String getAirportCode() { return code; }
    
    @Override
    public String getAirportName() { return name; }
    
    @Override
    public float getLatitude() { return latitude; }
    
    @Override
    public float getLongitude() { return longitude; }
}


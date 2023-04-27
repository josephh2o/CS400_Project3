// Stores airport data and each will represent a node
public interface AirportInterface {
// public Airport(String code, String name, float latitude, float longitude)
	public String getAirportCode();
    public String getAirportName();
	public float getLatitude();
	public float getLongitude();
}
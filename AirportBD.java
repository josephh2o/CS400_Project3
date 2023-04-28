public class AirportBD implements AirportInterface {
    String code;
    String name;
    float latitude;
    float longitude;

    public AirportBD(String code, String name, float latitude, float longitude) {
        this.code = code;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

	public String getAirportCode() {
        return code;
    }

    public String getAirportName() {
        return name;
    }

	public float getLatitude() {
        return latitude;
    }

	public float getLongitude() {
        return longitude;
    }

    public String toString() {
        return name;
    }
}


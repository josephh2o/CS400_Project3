/**
 * Airport class stores airport data, which includes the airport code, name, latitude, and longitude.
 */
public class AirportDW implements AirportInterface {

    private String code; // 3-letter code of airport
    private String name; // Name of airport
    private float latitude; // latitude of airport
    private float longitude; // Longitude of airport

    /**
     * Constructor method for Airport object
     * @param code 3-letter code of airport
     * @param name name of airport
     * @param latitude latitude of airport
     * @param longitude longitude of airport
     */
    public AirportDW(String code, String name, float latitude, float longitude) {
        this.code = code;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Getter method for airport code
     * @return 3-letter code of airport
     */
    @Override
    public String getAirportCode() {
        return code;
    }

    /**
     * Getter method for airport name
     * @return name of airport
     */
    @Override
    public String getAirportName() {
        return name;
    }

    /**
     * Getter method for airport latitude
     * @return latitude of airport
     */
    @Override
    public float getLatitude() {
        return latitude;
    }

    /**
     * Getter method for airport longitude
     * @return longitude of airport
     */
    @Override
    public float getLongitude() {
        return longitude;
    }
}

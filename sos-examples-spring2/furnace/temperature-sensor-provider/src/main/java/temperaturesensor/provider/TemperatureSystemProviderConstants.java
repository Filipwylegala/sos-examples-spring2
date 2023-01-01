package temperaturesensor.provider;

public class TemperatureSystemProviderConstants {
	
	//=================================================================================================
	// members
	
	public static final String BASE_PACKAGE = "temperaturesensor";
	
	public static final String READ_SENSOR_TEMPERATURE = "read-sensor-temperature";
	public static final String INTERFACE_SECURE = "HTTP-SECURE-JSON";
	public static final String INTERFACE_INSECURE = "HTTP-INSECURE-JSON";
	public static final String HTTP_METHOD = "http-method";
	public static final String SENSOR_TEMPERATURE = "/sensor/temperature";

	//=================================================================================================
	// assistant methods

	//-------------------------------------------------------------------------------------------------
	private TemperatureSystemProviderConstants() {
		throw new UnsupportedOperationException();
	}
}

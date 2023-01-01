package doorsensor.provider;

public class DoorSystemProviderConstants {
	
	//=================================================================================================
	// members
	
	public static final String BASE_PACKAGE = "doorsensor";
	
	public static final String READ_SENSOR_DOOR = "read-sensor-door-status";
	public static final String INTERFACE_SECURE = "HTTP-SECURE-JSON";
	public static final String INTERFACE_INSECURE = "HTTP-INSECURE-JSON";
	public static final String HTTP_METHOD = "http-method";
	public static final String SENSOR_DOOR = "/sensor/door/status";

	//=================================================================================================
	// assistant methods

	//-------------------------------------------------------------------------------------------------
	private DoorSystemProviderConstants() {
		throw new UnsupportedOperationException();
	}
}

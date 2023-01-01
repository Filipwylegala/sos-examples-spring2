package heatingsystem.provider;

public class HeatingSystemProviderConstants {
	
	//=================================================================================================
	// members
	
	public static final String BASE_PACKAGE = "heatingsystem";
	
	public static final String GET_THERMOSTAT_STATUS = "get-thermostat-status";
	public static final String INTERFACE_SECURE = "HTTP-SECURE-JSON";
	public static final String INTERFACE_INSECURE = "HTTP-INSECURE-JSON";
	public static final String HTTP_METHOD = "http-method";
	public static final String THERMOSTAT_STATUS = "/thermostat/status";

	//=================================================================================================
	// assistant methods

	//-------------------------------------------------------------------------------------------------
	private HeatingSystemProviderConstants() {
		throw new UnsupportedOperationException();
	}
}

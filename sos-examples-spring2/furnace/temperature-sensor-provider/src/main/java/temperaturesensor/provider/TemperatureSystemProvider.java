package temperaturesensor.provider;

import ai.aitia.demo.car_common.dto.ArrowheadPackage;
import eu.arrowhead.common.CommonConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {CommonConstants.BASE_PACKAGE, TemperatureSystemProviderConstants.BASE_PACKAGE, ArrowheadPackage.PATH})
public class TemperatureSystemProvider {

	//=================================================================================================
	// methods

	//-------------------------------------------------------------------------------------------------
	public static void main(final String[] args) {
		SpringApplication.run(TemperatureSystemProvider.class, args);
	}	
}

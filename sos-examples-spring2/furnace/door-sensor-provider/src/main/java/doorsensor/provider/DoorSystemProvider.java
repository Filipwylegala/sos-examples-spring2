package doorsensor.provider;

import ai.aitia.demo.car_common.dto.ArrowheadPackage;
import eu.arrowhead.common.CommonConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {CommonConstants.BASE_PACKAGE, DoorSystemProviderConstants.BASE_PACKAGE, ArrowheadPackage.PATH})
public class DoorSystemProvider {

	//=================================================================================================
	// methods

	//-------------------------------------------------------------------------------------------------
	public static void main(final String[] args) {
		SpringApplication.run(DoorSystemProvider.class, args);
	}	
}

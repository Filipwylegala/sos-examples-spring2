package heatingsystem;

import ai.aitia.demo.car_common.dto.ArrowheadPackage;
import eu.arrowhead.common.CommonConstants;
import heatingsystem.consumer.HeatingSystemConsumer;
import heatingsystem.consumer.HeatingSystemConsumerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {CommonConstants.BASE_PACKAGE, HeatingSystemConsumerConstants.BASE_PACKAGE, ArrowheadPackage.PATH})
public class HeatingSystemMain implements ApplicationRunner {

    @Autowired
    HeatingSystemConsumer consumer;


    public static void main( final String[] args ) {
    	SpringApplication.run(HeatingSystemMain.class, args);
    }

    //-------------------------------------------------------------------------------------------------

    @Override
	public void run(final ApplicationArguments args) {
        consumer.run();
	}
}

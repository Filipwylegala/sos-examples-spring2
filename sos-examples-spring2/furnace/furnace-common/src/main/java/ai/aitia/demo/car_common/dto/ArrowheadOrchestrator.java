package ai.aitia.demo.car_common.dto;

import ai.aitia.arrowhead.application.library.ArrowheadService;
import eu.arrowhead.common.SSLProperties;
import eu.arrowhead.common.Utilities;
import eu.arrowhead.common.dto.shared.*;
import eu.arrowhead.common.exception.InvalidParameterException;
import org.apache.logging.log4j.Logger;

public class ArrowheadOrchestrator {

    public static OrchestrationResponseDTO orchestrate(ArrowheadService arrowheadService, String service, Logger logger, SSLProperties sslProperties) {
        logger.info("Orchestration request for " + service + " service:");
        final ServiceQueryFormDTO serviceQueryForm = new ServiceQueryFormDTO.Builder(service)
                .interfaces(getInterface(sslProperties))
                .build();

        final OrchestrationFormRequestDTO.Builder orchestrationFormBuilder = arrowheadService.getOrchestrationFormBuilder();
        final OrchestrationFormRequestDTO orchestrationFormRequest = orchestrationFormBuilder.requestedService(serviceQueryForm)
                .flag(OrchestrationFlags.Flag.MATCHMAKING, true)
                .flag(OrchestrationFlags.Flag.OVERRIDE_STORE, true)
                .build();

        printOut(orchestrationFormRequest);

        final OrchestrationResponseDTO orchestrationResponse = arrowheadService.proceedOrchestration(orchestrationFormRequest);

        logger.info("Orchestration response:");
        printOut(orchestrationResponse);
        return  orchestrationResponse;
    }

    private static String getInterface(SSLProperties sslProperties) {
        return sslProperties.isSslEnabled() ? InterfaceConstants.INTERFACE_SECURE : InterfaceConstants.INTERFACE_INSECURE;
    }

    //-------------------------------------------------------------------------------------------------
    private static void printOut(final Object object) {
        System.out.println(Utilities.toPrettyJson(Utilities.toJson(object)));
    }

}

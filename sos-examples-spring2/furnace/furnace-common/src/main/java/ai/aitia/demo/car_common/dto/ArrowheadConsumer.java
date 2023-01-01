package ai.aitia.demo.car_common.dto;

import ai.aitia.arrowhead.application.library.ArrowheadService;
import eu.arrowhead.common.SSLProperties;
import eu.arrowhead.common.Utilities;
import eu.arrowhead.common.dto.shared.OrchestrationResponseDTO;
import eu.arrowhead.common.dto.shared.OrchestrationResultDTO;
import eu.arrowhead.common.dto.shared.ServiceInterfaceResponseDTO;
import eu.arrowhead.common.exception.InvalidParameterException;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpMethod;

import java.util.function.Consumer;

public class ArrowheadConsumer {
    public static <T> void consume(OrchestrationResponseDTO response, ArrowheadService arrowhead, String service, Consumer<T> consumer, Class<T> responseModel, Logger logger, SSLProperties sslProperties) {
        if (response == null) {
            logger.info("No orchestration response received");
        } else if (response.getResponse().isEmpty()) {
            logger.info("No provider found during the orchestration");
        } else {
            final OrchestrationResultDTO orchestrationResult = response.getResponse().get(0);
            validateOrchestrationResult(orchestrationResult, service, sslProperties);

            final String token = orchestrationResult.getAuthorizationTokens() == null ? null : orchestrationResult.getAuthorizationTokens().get(getInterface(sslProperties));
            @SuppressWarnings("unchecked")
            final T sensorReadDTO = arrowhead.consumeServiceHTTP(responseModel, HttpMethod.valueOf(orchestrationResult.getMetadata().get(InterfaceConstants.HTTP_METHOD)),
                    orchestrationResult.getProvider().getAddress(), orchestrationResult.getProvider().getPort(), orchestrationResult.getServiceUri(),
                    getInterface(sslProperties), token, null, new String[0]);
            consumer.accept(sensorReadDTO);
            printOut(sensorReadDTO);
        }
    }
    private static String getInterface(SSLProperties sslProperties) {
        return sslProperties.isSslEnabled() ? InterfaceConstants.INTERFACE_SECURE : InterfaceConstants.INTERFACE_INSECURE;
    }

    private static void validateOrchestrationResult(final OrchestrationResultDTO orchestrationResult, final String serviceDefinitin, SSLProperties sslProperties) {
        if (!orchestrationResult.getService().getServiceDefinition().equalsIgnoreCase(serviceDefinitin)) {
            throw new InvalidParameterException("Requested and orchestrated service definition do not match");
        }

        boolean hasValidInterface = false;
        for (final ServiceInterfaceResponseDTO serviceInterface : orchestrationResult.getInterfaces()) {
            if (serviceInterface.getInterfaceName().equalsIgnoreCase(getInterface(sslProperties))) {
                hasValidInterface = true;
                break;
            }
        }
        if (!hasValidInterface) {
            throw new InvalidParameterException("Requested and orchestrated interface do not match");
        }
    }

    //-------------------------------------------------------------------------------------------------
    private static void printOut(final Object object) {
        System.out.println(Utilities.toPrettyJson(Utilities.toJson(object)));
    }
}

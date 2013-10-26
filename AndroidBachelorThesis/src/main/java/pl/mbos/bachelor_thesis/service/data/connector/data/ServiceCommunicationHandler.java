package pl.mbos.bachelor_thesis.service.data.connector.data;

/**
 * Handles all communication with {@link pl.mbos.bachelor_thesis.service.data.DataService}
 */
public class ServiceCommunicationHandler {

    ServiceInboundCommunicationHandler inbound;
    ServiceOutboundCommunicationHandler outbound;
    DataServiceConnectionConnector connector;

}

package alom.services;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import javax.xml.namespace.QName;
import jakarta.xml.ws.WebEndpoint;
import jakarta.xml.ws.WebServiceClient;
import jakarta.xml.ws.WebServiceFeature;
import jakarta.xml.ws.Service;

/**
 * This class was generated by Apache CXF 4.1.0
 * 2024-12-22T19:11:21.735+01:00
 * Generated source version: 4.1.0
 *
 */
@WebServiceClient(name = "MessageServiceImplService",
                  wsdlLocation = "http://localhost:8080/aller/MessageService?wsdl",
                  targetNamespace = "http://services.alom/")
public class MessageServiceImplService extends Service {

    public static final URL WSDL_LOCATION;

    public static final QName SERVICE = new QName("http://services.alom/", "MessageServiceImplService");
    public static final QName MessageServiceImplPort = new QName("http://services.alom/", "MessageServiceImplPort");
    static {
        URL url = null;
        try {
            url = URI.create("http://localhost:8080/aller/MessageService?wsdl").toURL();
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(MessageServiceImplService.class.getName())
                .log(java.util.logging.Level.INFO,
                     "Can not initialize the default wsdl from {0}", "http://localhost:8080/aller/MessageService?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public MessageServiceImplService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public MessageServiceImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public MessageServiceImplService() {
        super(WSDL_LOCATION, SERVICE);
    }

    public MessageServiceImplService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public MessageServiceImplService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public MessageServiceImplService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }




    /**
     *
     * @return
     *     returns MessageService
     */
    @WebEndpoint(name = "MessageServiceImplPort")
    public MessageService getMessageServiceImplPort() {
        return super.getPort(MessageServiceImplPort, MessageService.class);
    }

    /**
     *
     * @param features
     *     A list of {@link jakarta.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns MessageService
     */
    @WebEndpoint(name = "MessageServiceImplPort")
    public MessageService getMessageServiceImplPort(WebServiceFeature... features) {
        return super.getPort(MessageServiceImplPort, MessageService.class, features);
    }

}

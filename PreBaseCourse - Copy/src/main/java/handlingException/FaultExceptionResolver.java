package handlingException;


import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.server.endpoint.AbstractSoapFaultDefinitionExceptionResolver;
import org.springframework.ws.soap.server.endpoint.SoapFaultDefinition;

import javax.xml.namespace.QName;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * При определении Бина этого класса, надо определить faultCode, defaultFaultCode,
 * и exceptionMapping. faultStringOrReason берется из exceptionMapping, locale = ENGLISH и стандартна.
 */
public class FaultExceptionResolver extends AbstractSoapFaultDefinitionExceptionResolver {
    private String faultCode;
    private String defaultFaultCode;
    private String faultStringOrReason;
    private Locale locale = Locale.ENGLISH;
    /**
     * key = FULL type of Exception (example "org.springframework.dao.DataIntegrityViolationException"), value = FaultStringOrReason
     */
    private Map<String, String> exceptionMapping = new LinkedHashMap<String, String>();
    private static final QName DESCRIPTION = new QName("DESCRIPTION");

    @Override
    protected SoapFaultDefinition getFaultDefinition(Object endpoint, Exception ex) {
        SoapFaultDefinition faultDefinition = new SoapFaultDefinition();

        faultDefinition.setFaultCode(getFaultCodeFromString(faultCode));
        if (exceptionMapping.containsKey(ex.getClass().getName())) {
            faultDefinition.setFaultStringOrReason(exceptionMapping.get(ex.getClass().getName()));
        }

        faultDefinition.setLocale(locale);

        SoapFaultDefinition defaultFaultDefenition = new SoapFaultDefinition();
        defaultFaultDefenition.setFaultCode(getFaultCodeFromString(defaultFaultCode));
        setDefaultFault(defaultFaultDefenition);

        return faultDefinition;
    }

    @Override
    protected void customizeFault(Object endpoint, Exception ex, SoapFault fault) {
        if (!exceptionMapping.containsKey(ex.getClass().getName())) {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));

            SoapFaultDetail detail = fault.addFaultDetail();
            detail.addFaultDetailElement(DESCRIPTION).addText(errors.toString());
        }
    }

    public String getFaultCode() {
        return faultCode;
    }

    public void setFaultCode(String faultCode) {
        this.faultCode = faultCode;
    }

    public String getDefaultFaultCode() {
        return defaultFaultCode;
    }

    public void setDefaultFaultCode(String defaultFaultCode) {
        this.defaultFaultCode = defaultFaultCode;
    }

    public String getFaultStringOrReason() {
        return faultStringOrReason;
    }

    public void setFaultStringOrReason(String faultStringOrReason) {
        this.faultStringOrReason = faultStringOrReason;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void setExceptionMapping(Map<String, String> mappings) {
        for (Map.Entry<String, String> entry : mappings.entrySet()) {
            exceptionMapping.put(entry.getKey(), entry.getValue());
        }
    }

    public QName getFaultCodeFromString(String faultCode) {
        if (faultCode.equalsIgnoreCase("CLIENT")) {
            return SoapFaultDefinition.CLIENT;
        }

        if (faultCode.equalsIgnoreCase("RECEIVER")) {
            return SoapFaultDefinition.RECEIVER;
        }

        if (faultCode.equalsIgnoreCase("SENDER")) {
            return SoapFaultDefinition.SENDER;
        }

        return SoapFaultDefinition.SERVER;

    }
}

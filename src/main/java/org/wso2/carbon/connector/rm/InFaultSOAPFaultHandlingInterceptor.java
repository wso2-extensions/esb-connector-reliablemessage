package org.wso2.carbon.connector.rm;

import org.apache.cxf.binding.soap.interceptor.Soap11FaultInInterceptor;
import org.apache.cxf.binding.soap.interceptor.Soap12FaultInInterceptor;
import org.apache.cxf.interceptor.ClientFaultConverter;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.synapse.MessageContext;

public class InFaultSOAPFaultHandlingInterceptor extends AbstractPhaseInterceptor<Message> {

    private String code = null;
    private String faultMessage = null;
    private String ex = null;
    private int statusCode;

    public InFaultSOAPFaultHandlingInterceptor(MessageContext messageContext) {
        super(Phase.UNMARSHAL);
        this.addBefore(ClientFaultConverter.class.getName());

        if (messageContext.isSOAP11()) {
            this.addAfter(Soap11FaultInInterceptor.class.getName());
        } else {
            this.addAfter(Soap12FaultInInterceptor.class.getName());
        }
    }

    @Override
    public void handleMessage(Message message) throws Fault {

        Fault fault = (Fault) message.getContent(Exception.class);
        code = fault.getFaultCode().toString();
        faultMessage = fault.getMessage();
        ex = (message.getContent(Exception.class)).toString();
        statusCode = fault.getStatusCode();
    }

    @Override
    public void handleFault(Message message) {
        //handleFault is not required
    }

    public String getCode() {
        return code;
    }

    public String getFaultMessage() {
        return faultMessage;
    }

    public String getException() {
        return ex;
    }

    public int getStatusCode() {
        return statusCode;
    }
}

/*
 *  Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.wso2.carbon.connector.rm;

/**
 * Reliable message specific parameters that will provided during initialization.
 */
public class RMParameters {
    private String endpoint;
    private String serviceName;
    private String portName;
    private String namespace;
    private String soapVersion;
    private String configLocation;

    /**
     * To get the endpoint.
     *
     * @return relevant endpoint configured for initialization.
     */
    public String getEndpoint() {
        return endpoint;
    }

    /**
     * To set the endpoint.
     *
     * @param endpoint Endpoint
     */
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * To get the service name of relevant soap endpoint.
     *
     * @return service name.
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * To set the service name.
     *
     * @param serviceName Service name.
     */
    void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * To get the port name of the relevant soap service.
     *
     * @return port name.
     */
    public String getPortName() {
        return portName;
    }

    /**
     * To set the port name of the relevant soap service.
     *
     * @param portName port name
     */
    void setPortName(String portName) {
        this.portName = portName;
    }

    /**
     * To get the namespace.
     *
     * @return namespace.
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * To get the soap version.
     *
     * @return soap version.
     */
    public String getSoapVersion() {
        return soapVersion;
    }

    /**
     * To set the soap version.
     *
     * @param soapVersion soap version.
     */
    public void setSoapVersion(String soapVersion) {
        this.soapVersion = soapVersion;
    }

    /**
     * To set the namespace.
     *
     * @param namespace namespace.
     */
    void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    /**
     * To get the ESB bus config location.
     *
     * @return Config location.
     */
    public String getConfigLocation() {
        return configLocation;
    }

    /**
     * To set the config location.
     *
     * @param configLocation Config location.
     */
    void setConfigLocation(String configLocation) {
        this.configLocation = configLocation;
    }
}

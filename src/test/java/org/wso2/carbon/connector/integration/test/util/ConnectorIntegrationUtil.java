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
 * /
 */
package org.wso2.carbon.connector.integration.test.util;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.automation.core.ProductConstant;

import javax.xml.stream.XMLStreamException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

/**
 * Util class for connector.
 */
public class ConnectorIntegrationUtil {
    private static final Log log = LogFactory.getLog(ConnectorIntegrationUtil.class);

    /**
     * To get the connector config properties for specific connector.
     *
     * @param connectorName Name of the connector.
     * @return Relevant property list
     */
    public static Properties getConnectorConfigProperties(String connectorName) {
        String connectorConfigFile;
        ProductConstant.init();
        try {
            connectorConfigFile =
                    ProductConstant.SYSTEM_TEST_SETTINGS_LOCATION + File.separator + "artifacts" + File.separator
                            + "ESB" + File.separator + "connector" + File.separator + "config" + File.separator
                            + connectorName + ".properties";
            File connectorPropertyFile = new File(connectorConfigFile);
            InputStream inputStream = null;
            if (connectorPropertyFile.exists()) {
                inputStream = new FileInputStream(connectorPropertyFile);
            }

            if (inputStream != null) {
                Properties prop = new Properties();
                prop.load(inputStream);
                inputStream.close();
                return prop;
            }

        } catch (IOException ignored) {
            log.error("automation.properties file not found, please check your configuration");
        }
        return null;
    }

    /**
     * To send a XML request.
     * @param addUrl URL.
     * @param query query.
     * @return the XML
     * @throws IOException IO Exception.
     * @throws XMLStreamException XML Stream Exception.
     */
    public static OMElement sendXMLRequest(String addUrl, String query) throws IOException,
            XMLStreamException {
        String charset = "UTF-8";
        System.out.println("=======url======" + addUrl + "=======" + query);
        URLConnection connection = new URL(addUrl).openConnection();
        connection.setDoOutput(true);
        connection.setRequestProperty("Accept-Charset", charset);
        connection.setRequestProperty("Content-Type", "text/xml;charset=" + charset);
        OutputStream output = null;
        try {
            output = connection.getOutputStream();
            output.write(query.getBytes(charset));
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException logOrIgnore) {
                    log.error("Error while closing the connection");
                }
            }
        }

        HttpURLConnection httpConn = (HttpURLConnection) connection;
        InputStream response;

        if (httpConn.getResponseCode() >= 400) {
            response = httpConn.getErrorStream();
        } else {
            response = connection.getInputStream();
        }

        String out = "{}";
        if (response != null) {
            StringBuilder sb = new StringBuilder();
            byte[] bytes = new byte[1024];
            int len;
            while ((len = response.read(bytes)) != -1) {
                sb.append(new String(bytes, 0, len));
            }

            if (!sb.toString().trim().isEmpty()) {
                out = sb.toString();
            }
        }
        log.info("Out : " + out);
        return AXIOMUtil.stringToOM(out);
    }

    public static String getFileContent(String path) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            String ls = System.getProperty("line.separator");
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }
        } catch (IOException ioe) {
            log.error("Error reading request from file.", ioe);
        }
        return stringBuilder.toString();
    }
}

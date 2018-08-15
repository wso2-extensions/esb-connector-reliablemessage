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
package org.wso2.carbon.connector.integration.test;

import org.apache.axiom.om.OMElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.carbon.automation.engine.frameworkutils.FrameworkPathUtil;
import org.wso2.carbon.connector.integration.test.util.ConnectorIntegrationUtil;
import org.wso2.connector.integration.test.base.ConnectorIntegrationTestBase;

import java.io.File;
import java.util.Properties;

public class RMConnectorIntegrationTest extends ConnectorIntegrationTestBase {
    private String pathToRequestsDirectory = null;
    private static final String CONNECTOR_PROPERTIES = "reliablemessage";

    @BeforeClass(alwaysRun = true)
    public void setEnvironment() throws Exception {
        String connectorName =
                System.getProperty("connector_name") + "-connector-" + System.getProperty("connector_version") + ".zip";
        init(connectorName);
        String repoLocation;
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            repoLocation = System.getProperty("connector_repo").replace("/", "\\");
        } else {
            repoLocation = System.getProperty("connector_repo").replace("/", "/");
        }
        Properties reliableMessagingConnectorProperties = ConnectorIntegrationUtil.getConnectorConfigProperties
                (CONNECTOR_PROPERTIES);
        if (reliableMessagingConnectorProperties == null) {
            reliableMessagingConnectorProperties = new Properties();
        }
        pathToRequestsDirectory =
                repoLocation + reliableMessagingConnectorProperties.getProperty("requestDirectoryRelativePath");
        ProcessBuilder pb = new ProcessBuilder("java", "-jar", "rm_server.jar");
        pb.directory(new File(FrameworkPathUtil.getSystemResourceLocation() + File.separator + "rmserver"));
        pb.start();
    }

    @Test(description = "Send reliable message success request")
    public void sendRmEnableRequest() throws Exception {
        String methodName = "reliable";
        String actualResponse = "Hello Gil";
        String pathToMessage = pathToRequestsDirectory + "greetMeRequest.xml";
        File file = new File(pathToMessage);
        final String soapRequestString = ConnectorIntegrationUtil
                .getFileContent(file.getPath());
        OMElement omElement = ConnectorIntegrationUtil.sendXMLRequest(
                getProxyServiceURLHttp(methodName), soapRequestString);
        Assert.assertEquals(omElement.getFirstElement().getText(), actualResponse, "Expected response not found");
    }
}

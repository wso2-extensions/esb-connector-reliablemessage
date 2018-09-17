## Working with the Reliable Message Connector

### Installation Prerequisites
You need to install the **WSO2 Carbon — CXF WS Reliable Messaging feature** for the connector to work successfully. To install this, copy the WSO2 EI pack and the following pom.xml file to the same directory. For more information, see [installing features](https://docs.wso2.com/display/ADMIN44x/Working+with+Features#WorkingwithFeatures-pom_approachInstallingfeaturesusingpomfiles).

You can use the sample POM file that is given below to install this feature(Tip: use the EI product version you are using for config <destination>  and 'dir' attribute).
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>org.wso2.sample</groupId>
  <artifactId>sample-feature-installation</artifactId>
  <version>1.0.0</version>
  <packaging>pom</packaging>
  <name>New feature</name>
  <url>http://wso2.org</url>
  <build>
    <plugins>
      <plugin>
        <groupId>org.wso2.maven</groupId>
        <artifactId>carbon-p2-plugin</artifactId>
        <version>1.5.4</version>
        <executions>
          <execution> 
            <id>feature-install</id>
            <phase>package</phase>
            <goals>
              <goal>p2-profile-gen</goal>
            </goals> 
            <configuration>
              <profile>default</profile>
              <metadataRepository>http://product-dist.wso2.com/p2/carbon/releases/wilkes/</metadataRepository>
              <artifactRepository>http://product-dist.wso2.com/p2/carbon/releases/wilkes/</artifactRepository>
              <destination>wso2ei-6.1.0/wso2/components</destination>
              <deleteOldProfileFiles>false</deleteOldProfileFiles>
              <features>
                <feature>
                  <id>org.wso2.carbon.inbound.endpoints.ext.wsrm.feature.group</id>
                  <version>4.6.6</version>
                </feature>
              </features>
            </configuration>
          </execution>
        </executions>
      </plugin>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-antrun-plugin</artifactId>
        <version>1.1</version>
        <executions>
          <execution>
            <phase>package</phase>
            <configuration>
              <tasks>
                <replace token="false" value="true" dir="wso2ei-6.1.0/wso2/components/default/configuration/org.eclipse.equinox.simpleconfigurator">
                  <include name="**/bundles.info"/>
                </replace>
              </tasks>
            </configuration>
            <goals>
              <goal>run</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
    </plugins>
  </build>
  <repositories>
    <repository>
      <id>wso2-nexus</id>
      <name>WSO2 internal Repository</name>
      <url>http://maven.wso2.org/nexus/content/groups/wso2-public/</url>
      <releases>
        <enabled>true</enabled>
        <updatePolicy>daily</updatePolicy>
        <checksumPolicy>ignore</checksumPolicy>
      </releases>
    </repository>
  </repositories>
  <pluginRepositories>
    <pluginRepository>
      <id>wso2-maven-releases-repository</id>
      <url>http://maven.wso2.org/nexus/content/repositories/releases/</url>
    </pluginRepository>
    <pluginRepository>
      <id>wso2-maven-snapshots-repository</id>
      <url>http://maven.wso2.org/nexus/content/repositories/snapshots/</url>
    </pluginRepository>
  </pluginRepositories>
</project>
```

#### Initializing and using the Connector
After adding connector through WSO2 Enterprise Integrator, to use this connector for reliable messaging, you need to initialize it using the `<reliablemessage.init>`
element as follows,

```xml
 <reliablemessage.init>
    <endpoint>http://localhost:9010/SoapContext/GreeterPort</endpoint>
    <port>GreeterPort</port>
    <service>GreeterService</service>
    <namespace>http://cxf.apache.org/hello_world_soap_http</namespace>
    <soapVersion>1.1</soapVersion>
    <configLocation>repository/conf/cxf/client.xml</configLocation>
</reliablemessage.init>
```
The parameters that are listed below are required.

Parameter Name |  Description
--- | ---
endpoint | The SOAP backend that implement reliable messaging (i.e. ws-rm).
port | The WSDL port name of the SOAP service that you would like to send messages to. 
service |  The service name of the SOAP service.
soapVersion | The SOAP version of relevant service.
configLocation | The CXF service bus configuration location.

Define the `GreeterService` WSDL service as follows,
```xml
<service name="GreeterService">
    <port binding="tns:Greeter_SOAPBinding" name="GreeterPort">
        <soap:address location="http://localhost:9010/SoapContext/GreeterPort"/>
        <wswa:UsingAddressing xmlns:wswa="http://www.w3.org/2005/02/addressing/wsdl"/>
    </port>
</service>
```

The `init` configuration for the service you defined will be as follows,
```xml
 <reliablemessage.init>
    <endpoint>http://localhost:9010/SoapContext/GreeterPort</endpoint>
    <port>GreeterPort</port>
    <service>GreeterService</service>
    <namespace>http://cxf.apache.org/hello_world_soap_http</namespace>
    <soapVersion>1.1</soapVersion>
    <configLocation>repository/conf/cxf/client.xml</configLocation>
</reliablemessage.init>
```
To send a message after the initialization, you need to call the send operation as follows,
```xml
<reliablemessage.send/>
```

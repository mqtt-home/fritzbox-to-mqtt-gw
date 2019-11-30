# FritzTR064

Java-library to communicate with the AVM FritzBox by using the TR-064 protocol.

This project uses the build tool _gradle_.

## Create FritzTR064.jar

Compile the project with _gradle_.

```bash
> ./gradlew clean build 
```

You can find the JAR file in directory ```build/libs/FritzTR064.jar```

## Dependencies

This library depends on:

* [HttpClient 4.x](https://hc.apache.org/httpcomponents-client-4.4.x/logging.html)

## Quickstart

Get all the posibel Actions:

```java
FritzConnection fc = new FritzConnection("192.168.1.1","<username>","<password>");
fc.init();
fc.printInfo();
```

The next Example shows how you can get the number of connected Wlan Devices:

```java
FritzConnection fc = new FritzConnection("192.168.1.1","<username>","<password>");
fc.init();
Service service = fc.getService("WLANConfiguration:1");
Action action = service.getAction("GetTotalAssociations");
Response response = action.execute();
int deviceCount = response.getValueAsInteger("NewTotalAssociations");
```

For more examples see: [The Example Folder](https://github.com/a-brandt/FritzTR064/tree/master/src/test/java/de/mapoll/javaAVMTR064)

## Debuging

The examples in ```src/test/java/de/mapoll/javaAVMTR064``` uses _logback_ for debug output.

To see the HTTP traffic change the log level in ```src/test/resources/logback-test.xml```:

```xml
		<!-- change log level here to get more output -->
		<logger name="org.apache.http" level="debug" />
		<!-- change log level here to get more output -->
		<logger name="org.apache.http.wire" level="debug" />
```


## Resorces
* [AVM API Description](http://avm.de/service/schnittstellen/) (German)
* [Original Project](https://github.com/mirthas/FritzTR064)

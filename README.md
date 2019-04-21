# Neo4j Platform

This platform allows to create nodes, set properties, and create relationships from Jarvis bots. It is used as an example of custom platform creation in the [jarvis tutorial](https://github.com/SOM-Research/jarvis/wiki/Create-a-custom-Jarvis-Platform).

## Installation

You need to have a working Jarvis installation to use this platform. You can download the latest release [here](https://github.com/SOM-Research/jarvis/releases/tag/v1.0.1), or build Jarvis from sources by following [this tutorial](https://github.com/SOM-Research/jarvis/wiki/Installation).

You also need to build the Neo4j platform jar from the sources. You can do it by running the following commands from the project's root directory:

```bash
cd runtime
mvn install
```
This will create a `neo4j-platform-jar-with-dependencies.jar` file in the `runtime/target` folder.

## Execution

The Neo4j platform is not part of Jarvis core platforms, and need to be manually added to the classpath when starting the application. You can run this command (windows version) to run Jarvis with the Neo4j platform.

```bash
java -cp "jarvis_1.0.1.jar;runtime/target/neo4j-platform-jar-with-dependencies.jar" edu.uoc.som.jarvis.Jarvis Neo4jBot.properties
```

> **Useful Tips** You can provide an absolute path for the `jar` files to include in the classpath.

## Run the example

The `examples/` folder contains sample bots using the Neo4j platform. You can run them using the command above. Note that some bots require additional information in their `.properties` files such as DialogFlow credentials or Slack tokens that cannot be provided as part as this repository for privacy and security reasons. See [our tutorial section](https://github.com/SOM-Research/jarvis/wiki/Deploying-chatbots) to create your own credentials and deploy the bots.

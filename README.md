Xatkit Neo4j Platform
=====

[![License Badge](https://img.shields.io/badge/license-EPL%202.0-brightgreen.svg)](https://opensource.org/licenses/EPL-2.0)
[![Build Status](https://travis-ci.com/xatkit-bot-platform/xatkit-neo4j-platform.svg?branch=master)](https://travis-ci.com/xatkit-bot-platform/xatkit-neo4j-platform)
[![Wiki Badge](https://img.shields.io/badge/doc-wiki-blue)](https://github.com/xatkit-bot-platform/xatkit-releases/wiki/Xatkit-Neo4j-Platform)


Create, manipulate, and store [Neo4j](https://neo4j.com/) graphs from your Xatkit execution model. This platform is **not** bundled with the [Xatkit release](https://github.com/xatkit-bot-platform/xatkit-releases/releases).


## Providers

The Neo4j platform does not define any provider.

## Actions

| Action | Parameters                                                   | Return                         | Return Type | Description                                                 |
| ------ | ------------------------------------------------------------ | ------------------------------ | ----------- | ----------------------------------------------------------- |
| CreateNode | - `label`(**String**): the label of the Node to create | The created [Node](https://neo4j.com/docs/java-reference/current/javadocs/org/neo4j/graphdb/Node.html) | [Node](https://neo4j.com/docs/java-reference/current/javadocs/org/neo4j/graphdb/Node.html)      | Creates a new Node in the current Neo4j graph with the provided `label`. If `label` is `null` the created Node will not define any label |
| AddProperty | - `node` (**[Node](https://neo4j.com/docs/java-reference/current/javadocs/org/neo4j/graphdb/Node.html)**): the Node to add the property to<br/> - `key` (**String**): the key of the property to set<br/> - `value` (**String**): the value of the property | The updated [Node](https://neo4j.com/docs/java-reference/current/javadocs/org/neo4j/graphdb/Node.html) | [Node](https://neo4j.com/docs/java-reference/current/javadocs/org/neo4j/graphdb/Node.html) | Sets a new property `key` to the given `node` with the provided `value` |
| CreateRelationship | - `fromNode` (**[Node](https://neo4j.com/docs/java-reference/current/javadocs/org/neo4j/graphdb/Node.html)**): the source node of the relationship to create<br/> - `toNode` (**[Node](https://neo4j.com/docs/java-reference/current/javadocs/org/neo4j/graphdb/Node.html)**): the target node of the relationship to create<br/> - `label` (**String**): the label of the relationship to create | The created [Relationship](https://neo4j.com/docs/java-reference/current/javadocs/org/neo4j/graphdb/Relationship.html) | [Relationship](https://neo4j.com/docs/java-reference/current/javadocs/org/neo4j/graphdb/Relationship.html) | Creates a Relationship between `fromNode` and `toNode` with the provided `label` |

## Options

The Neo4j platform supports the following configuration options

| Key                  | Values | Description                                                  | Constraint    |
| -------------------- | ------ | ------------------------------------------------------------ | ------------- |
| `xatkit.neo4j.db.path` | String | The path of the directory to use to store/load the Neo4j database | **Mandatory** |

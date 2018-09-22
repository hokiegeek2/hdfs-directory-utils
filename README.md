# hdfs-directory-utils
hdfs-directory-utils ties provides Scala API for Zookeeper Watcher events 

## Background

The [Zookeeper Watcher](https://zookeeper.apache.org/doc/current/api/org/apache/zookeeper/Watcher.html) API provides a programmatic 
hook for responding to changes in a Zookeper node tree.

## Use Cases

The main use case for hdfs-directory-utils is changing of the Active NameNode in an HDFS cluster. There are HDFS APIs that do not 
support accessing HDFS in HA mode. In such cases, if the Active NameNode changes, all CRUD operations will fail. The hdfs-directory-utils
API can be used to write a Zookeeper Watcher client that will update the HDFS connect string when the Active NameNode changes via
watching the HDFS Active Namenode zk node.

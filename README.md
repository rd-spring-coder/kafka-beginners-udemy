**Zookeeper**

`Start: zookeeper-server-start <path-to-server-zookeeper-properties>`

This runs on localhost:2181

**Kafka**

`Start: Kafka-server-start <path-to-server-dot-properties>`

This runs on localhost:9092

**Kafka Commands**

_List topics:_
`Kafka-topics --zookeeper localhost:2181 --list`

_Describe a topic:_
`Kafka-topic --zookeeper localhost:2181 --describe`
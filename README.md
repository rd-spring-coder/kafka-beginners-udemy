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

**REFERENCES**
https://techbeacon.com/app-dev-testing/what-apache-kafka-why-it-so-popular-should-you-use-it
https://www.learningjournal.guru/courses/kafka/kafka-foundation-training/offset-management/

**OPEN Question**
1. How do partitions on a kafka topic work?
    
    Suppose a Kafka topic has 3 partitions, each message sent by the producer is consumed by every partition
    i.e. a replica of each message is stored on all 3 partitions

2. What are offsets?
    
    Position within a partition, e.g. Say Kafka has 100 messages and no message is consumed yet, the offset
    would then by pointing at 0. 
    Consumer polls the queue to get 20 messages, position of the offset moves to 20 meaning 20 messages have
    been consumed. Next consumption would start from 21st message.
    There is something called as commit offset as well and the one described above is a current offset
    
    Commit Offset: Commit offset is the last record that the consumer has successfully processed.
    
    So, in summary.
    
    Current offset -> Sent Records -> This is used to avoid resending same records again to the same consumer.
    Committed offset -> Processed Records -> It is used to avoid resending same records to a new consumer in the event
    of partition rebalance. 

3. What are kafka-consumer-groups, how to use?

    A consumer can be assigned to a particular group. Multiple instances of the consumer with the same group
    are load balanced and fault tolerant.
    
    TODO: will add a reference link on how it works
    
4. In case of a failure on one of the consumer group instances, how are messages recovered ? Is it possible?
    
   All messages written to Kafka are persisted and replicated to peer brokers for fault tolerance, 
   and those messages stay around for a configurable period of time (i.e., 7 days, 30 days, etc.).
# Deserializing JSON with spring-kafka

## Intro

This example shows how to use `spring-kafka`'s `JsonDeserializer` to deserialize JSON in cases where `spring-kafka` wasn't used to serialize.

## Prerequisites

Maven 3
Confluent Cloud cluster

## Running

For the producer side, you can use ksqlDB to populate a topic with generic JSON data:

```
CREATE STREAM my_stream (id int key, value1 varchar, value2 varchar)
WITH (kafka_topic='MY_TOPIC', value_format='JSON', partitions=1);

INSERT INTO MY_TOPIC (id, value1, value2) values (1, 'foo1', 'foo2');
...
```

Populate the cluster endpoint, key, secret, and topic name in the `Consumer` class.
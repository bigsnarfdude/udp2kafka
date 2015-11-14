# UDP2Kafka 

Simple UDP service using Scala Akka that can recieve any UDP message received from UDP port 9000.  The service sends the UDP message to a named [Apache Kafka](http://kafka.apache.org/) topic. 

I'm using this service as part of Project Rehydrate to send metrics data via UDP to Kafka for processing and rehydration of Twitter Algebird HLLs in a Scala Akka HTTP REST API.

Related Projects:
[https://github.com/bigsnarfdude/addifier](https://github.com/bigsnarfdude/addifier)
[https://github.com/bigsnarfdude/simmer](https://github.com/bigsnarfdude/akka-http-algebird)

Inspired by:
[https://github.com/bigsnarfdude/simmer](https://github.com/bigsnarfdude/simmer)

## Get Kafka up and going and create "metrics" topic
[https://kafka.apache.org/documentation.html#introduction](https://kafka.apache.org/documentation.html#introduction)

## Running Details

UDP listener is set up on port `9000`.
Kafka topic name is: `metrics`.

## Build fat jar

`sbt assembly`


## Run project 

`sbt run`

or via java jar:

`java -jar udp2kafka-assembly-0.1.jar`

To modify default values you may use java system properties such as:

    java \
    -Dbind.host=0.0.0.0 \
    -Dbind.port=9000 \
    -Dtopic=metrics \
    -Dkafka.metadata.broker.list=localhost:9092 \
    -Dkafka.serializer.class=kafka.serializer.StringEncoder \
    -Dkafka.compression.codec=1 \
    -Dkafka.producer.type=async \
    -jar udp2kafka-assembly-0.1.jar
    
Or create a config file called `config.conf`:

    bind.host = "0.0.0.0"
    bind.port = 9000
    topic = metrics

    kafka {
        metadata.broker.list = "localhost:9092"
        producer.type = "async"
        serializer.class = "kafka.serializer.StringEncoder"
        compression.codec = "1"
    }

and run it with:

    java -Dconfig.file=config.conf -jar udp2kafka-assembly-0.1.jar
    

## Develop

`sbt run`
    

## Author and Licence
If you have any questions regarding this project contact:

@bigsnarfdude on Twitter

For licensing info see LICENSE file in project's root directory.

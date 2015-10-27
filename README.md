# UDP2Kafka

Sends any message received from a specified UDP port 9000 to a named [kafka](http://kafka.apache.org/) topic. I send metrics data via UDP to Kafka for processing.

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
    

Project uses [logback](http://logback.qos.ch/) for logging and default place for log files is `logs/` under cwd. If you'd like to change that you should provide another `logback.xml` to classpath.

## Develop

`sbt run`
    
## License

See attached project Licence in repo.

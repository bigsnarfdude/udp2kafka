package com.example.udp2kafka

import collection.convert.wrapAll._
import akka.actor.{ ActorRef, ActorSystem, Props, Actor }
import akka.io.{ IO, Udp }

import kafka.javaapi.producer.Producer
import kafka.producer.KeyedMessage
import kafka.producer.ProducerConfig

object Main extends App {

  val system = ActorSystem("udp2kafka")

  val kafkaProps = new java.util.Properties
  system.settings.config.withOnlyPath("kafka").entrySet.foreach{e =>
    kafkaProps.put(e.getKey.substring(6), e.getValue.unwrapped)
  }

  val kafkaProducer = system.actorOf(Props(new Packager(kafkaProps)))
  val udpListener = system.actorOf(Props(new Listener(kafkaProducer)))
}

class Listener(nextActor: ActorRef) extends Actor {
  import context.system
  
  val conf = context.system.settings.config

  IO(Udp) ! Udp.Bind(self, new java.net.InetSocketAddress(
    conf.getString("bind.host"),
    conf.getInt("bind.port")))
 
  def receive = {
    case Udp.Bound(local) ⇒
      context.become(ready(sender))
  }
 
  def ready(socket: ActorRef): Receive = {
    case Udp.Received(data, remote) ⇒
      nextActor ! data.utf8String
    case Udp.Unbind  ⇒ socket ! Udp.Unbind
    case Udp.Unbound ⇒ context.stop(self)
  }
}

class Packager(kafkaProps: java.util.Properties) extends Actor {
  val producer = new Producer[Integer, String](new ProducerConfig(kafkaProps))
  val topic = context.system.settings.config.getString("topic")

  def receive = {
    case message: String => 
      producer.send(new KeyedMessage[Integer, String](topic, message))
  }
}

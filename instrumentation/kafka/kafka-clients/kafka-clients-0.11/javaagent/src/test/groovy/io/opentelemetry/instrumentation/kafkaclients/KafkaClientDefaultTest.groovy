/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package io.opentelemetry.instrumentation.kafkaclients


import io.opentelemetry.sdk.trace.data.SpanData
import io.opentelemetry.semconv.trace.attributes.SemanticAttributes
import org.apache.kafka.clients.producer.ProducerRecord

import java.time.Duration
import java.util.concurrent.TimeUnit

import static io.opentelemetry.api.trace.SpanKind.CONSUMER
import static io.opentelemetry.api.trace.SpanKind.PRODUCER

class KafkaClientDefaultTest extends KafkaClientPropagationBaseTest {

  def "test kafka produce and consume"() {
    when:
    String greeting = "Hello Kafka!"
    runWithSpan("parent") {
      producer.send(new ProducerRecord(SHARED_TOPIC, greeting)) { meta, ex ->
        if (ex == null) {
          runWithSpan("producer callback") {}
        } else {
          runWithSpan("producer exception: " + ex) {}
        }
      }.get(5, TimeUnit.SECONDS)
    }

    then:
    awaitUntilConsumerIsReady()
    def records = consumer.poll(Duration.ofSeconds(5).toMillis()) // instrumentation => "IteratorAdvice.wrap"
    records.count() == 1

    // iterate over records to generate spans

    int i = 0
    // records has one element
    /*
    def iterator = records.iterator()
    iterator.hasNext()
    iterator.next()

    iterator.hasNext()
    try {
    } catch (Throwable t) {
      System.out.println()
      System.out.println("Throwable **");
      t.printStackTrace()
      System.out.println()
    }
    */


    for (record in records) {


      // for each iteration ? or just the iteration before the break?

      /*Context context =
      GlobalOpenTelemetry.getPropagators()
        .getTextMapPropagator()
        .extract(Context.root(), record, KafkaConsumerRecordGetter.INSTANCE);
*/

      //try (Scope ignored = context.makeCurrent()) {

      //try (Scope ignored = Context.current()) {
      //}
      // break;
//      break
    }
/*
*/
    //for (record in records) { // instrumentation => "IteratorAdvice.wrap"
    /*
  runWithSpan("processing") {
    assert record.value() == greeting
    assert record.key() == null
  }*/
    //i++;
    //System.out.println("i =" + i);
    //break;

    // [otel.javaagent 2022-07-28 04:12:42:664 -0700] [otel-javaagent-transform-safe-logger] DEBUG io.opentelemetry.javaagent.tooling.AgentInstaller$TransformLoggingListener - Transformed org.awaitility.core.ConditionAwaiter$ConditionPoller -- jdk.internal.loader.ClassLoaders$AppClassLoader@5e2de80c
    // [otel.javaagent 2022-07-28 04:12:42:668 -0700] [awaitility-thread] ERROR io.opentelemetry.javaagent.shaded.io.opentelemetry.context.StrictContextStorage - Multiple scopes leaked - first will be thrown as an error.
    // [otel.javaagent 2022-07-28 04:12:42:669 -0700] [awaitility-thread] ERROR io.opentelemetry.javaagent.shaded.io.opentelemetry.context.StrictContextStorage - Scope leaked
    // java.lang.AssertionError: Thread [Time-limited test] opened a scope of {opentelemetry-trace-span-key=SdkSpan{traceId=747c2515b724701ebff4ce2749a12139, spanId=5ddccb9cbf6f0d8d, parentSpanContext=ImmutableSpanContext{traceId=747c2515b724701ebff4ce2749a12139, spanId=d8027d259c363431, traceFlags=01, traceState=ArrayBasedTraceState{entries=[]}, remote=false, valid=true}, name=shared.topic process, kind=CONSUMER, attributes=AttributesMap{data={messaging.operation=process, kafka.record.queue_time_ms=1051, kafka.offset=0, messaging.destination=shared.topic, messaging.kafka.partition=0, messaging.system=kafka, messaging.message_payload_size_bytes=12, messaging.destination_kind=topic}, capacity=128, totalAddedValues=8}, status=ImmutableStatusData{statusCode=UNSET, description=}, totalRecordedEvents=0, totalRecordedLinks=1, startEpochNanos=1659006762621951600, endEpochNanos=0}, opentelemetry-traces-local-root-span=SdkSpan{traceId=747c2515b724701ebff4ce2749a12139, spanId=d8027d259c363431, parentSpanContext=ImmutableSpanContext{traceId=00000000000000000000000000000000, spanId=0000000000000000, traceFlags=00, traceState=ArrayBasedTraceState{entries=[]}, remote=false, valid=false}, name=shared.topic receive, kind=CONSUMER, attributes=AttributesMap{data={messaging.operation=receive, messaging.destination=shared.topic, messaging.system=kafka, messaging.destination_kind=topic}, capacity=128, totalAddedValues=4}, status=ImmutableStatusData{statusCode=UNSET, description=}, totalRecordedEvents=0, totalRecordedLinks=0, startEpochNanos=1659006762027714200, endEpochNanos=1659006762607358400}, opentelemetry-traces-span-key-consumer-receive=SdkSpan{traceId=747c2515b724701ebff4ce2749a12139, spanId=d8027d259c363431, parentSpanContext=ImmutableSpanContext{traceId=00000000000000000000000000000000, spanId=0000000000000000, traceFlags=00, traceState=ArrayBasedTraceState{entries=[]}, remote=false, valid=false}, name=shared.topic receive, kind=CONSUMER, attributes=AttributesMap{data={messaging.operation=receive, messaging.destination=shared.topic, messaging.system=kafka, messaging.destination_kind=topic}, capacity=128, totalAddedValues=4}, status=ImmutableStatusData{statusCode=UNSET, description=}, totalRecordedEvents=0, totalRecordedLinks=0, startEpochNanos=1659006762027714200, endEpochNanos=1659006762607358400}, opentelemetry-traces-span-key-consumer-process=SdkSpan{traceId=747c2515b724701ebff4ce2749a12139, spanId=5ddccb9cbf6f0d8d, parentSpanContext=ImmutableSpanContext{traceId=747c2515b724701ebff4ce2749a12139, spanId=d8027d259c363431, traceFlags=01, traceState=ArrayBasedTraceState{entries=[]}, remote=false, valid=true}, name=shared.topic process, kind=CONSUMER, attributes=AttributesMap{data={messaging.operation=process, kafka.record.queue_time_ms=1051, kafka.offset=0, messaging.destination=shared.topic, messaging.kafka.partition=0, messaging.system=kafka, messaging.message_payload_size_bytes=12, messaging.destination_kind=topic}, capacity=128, totalAddedValues=8}, status=ImmutableStatusData{statusCode=UNSET, description=}, totalRecordedEvents=0, totalRecordedLinks=1, startEpochNanos=1659006762621951600, endEpochNanos=0}} here:

    //}

  }

  def "test pass through tombstone"() {
    when:
    producer.send(new ProducerRecord<>(SHARED_TOPIC, null)).get(5, TimeUnit.SECONDS)

    then:
    awaitUntilConsumerIsReady()
    def records = consumer.poll(Duration.ofSeconds(5).toMillis())
    records.count() == 1

    // iterate over records to generate spans
    for (record in records) {
      assert record.value() == null
      assert record.key() == null
    }

    assertTraces(2) {
      traces.sort(orderByRootSpanKind(PRODUCER, CONSUMER))

      SpanData producerSpan

      trace(0, 1) {
        span(0) {
          name SHARED_TOPIC + " send"
          kind PRODUCER
          hasNoParent()
          attributes {
            "$SemanticAttributes.MESSAGING_SYSTEM" "kafka"
            "$SemanticAttributes.MESSAGING_DESTINATION" SHARED_TOPIC
            "$SemanticAttributes.MESSAGING_DESTINATION_KIND" "topic"
            "$SemanticAttributes.MESSAGING_KAFKA_TOMBSTONE" true
          }
        }

        producerSpan = span(0)
      }
      trace(1, 2) {
        span(0) {
          name SHARED_TOPIC + " receive"
          kind CONSUMER
          hasNoParent()
          attributes {
            "$SemanticAttributes.MESSAGING_SYSTEM" "kafka"
            "$SemanticAttributes.MESSAGING_DESTINATION" SHARED_TOPIC
            "$SemanticAttributes.MESSAGING_DESTINATION_KIND" "topic"
            "$SemanticAttributes.MESSAGING_OPERATION" "receive"
          }
        }
        span(1) {
          name SHARED_TOPIC + " process"
          kind CONSUMER
          childOf span(0)
          hasLink producerSpan
          attributes {
            "$SemanticAttributes.MESSAGING_SYSTEM" "kafka"
            "$SemanticAttributes.MESSAGING_DESTINATION" SHARED_TOPIC
            "$SemanticAttributes.MESSAGING_DESTINATION_KIND" "topic"
            "$SemanticAttributes.MESSAGING_OPERATION" "process"
            "$SemanticAttributes.MESSAGING_MESSAGE_PAYLOAD_SIZE_BYTES" Long
            "$SemanticAttributes.MESSAGING_KAFKA_PARTITION" { it >= 0 }
            "$SemanticAttributes.MESSAGING_KAFKA_TOMBSTONE" true
            "kafka.offset" Long
            "kafka.record.queue_time_ms" { it >= 0 }
          }
        }
      }
    }
  }

  def "test records(TopicPartition) kafka consume"() {
    setup:
    def partition = 0

    when: "send message"
    def greeting = "Hello from MockConsumer!"
    producer.send(new ProducerRecord<>(SHARED_TOPIC, partition, null, greeting)).get(5, TimeUnit.SECONDS)

    then: "wait for PRODUCER span"
    waitForTraces(1)

    when: "receive messages"
    awaitUntilConsumerIsReady()
    def consumerRecords = consumer.poll(Duration.ofSeconds(5).toMillis())
    def recordsInPartition = consumerRecords.records(KafkaClientBaseTest.topicPartition)
    recordsInPartition.size() == 1

    // iterate over records to generate spans
    for (record in recordsInPartition) {
      assert record.value() == greeting
      assert record.key() == null
    }

    then:
    assertTraces(2) {
      traces.sort(orderByRootSpanKind(PRODUCER, CONSUMER))

      SpanData producerSpan

      trace(0, 1) {
        span(0) {
          name SHARED_TOPIC + " send"
          kind PRODUCER
          hasNoParent()
          attributes {
            "$SemanticAttributes.MESSAGING_SYSTEM" "kafka"
            "$SemanticAttributes.MESSAGING_DESTINATION" SHARED_TOPIC
            "$SemanticAttributes.MESSAGING_DESTINATION_KIND" "topic"
            "$SemanticAttributes.MESSAGING_KAFKA_PARTITION" { it >= 0 }
          }
        }

        producerSpan = span(0)
      }
      trace(1, 2) {
        span(0) {
          name SHARED_TOPIC + " receive"
          kind CONSUMER
          hasNoParent()
          attributes {
            "$SemanticAttributes.MESSAGING_SYSTEM" "kafka"
            "$SemanticAttributes.MESSAGING_DESTINATION" SHARED_TOPIC
            "$SemanticAttributes.MESSAGING_DESTINATION_KIND" "topic"
            "$SemanticAttributes.MESSAGING_OPERATION" "receive"
          }
        }
        span(1) {
          name SHARED_TOPIC + " process"
          kind CONSUMER
          childOf span(0)
          hasLink producerSpan
          attributes {
            "$SemanticAttributes.MESSAGING_SYSTEM" "kafka"
            "$SemanticAttributes.MESSAGING_DESTINATION" SHARED_TOPIC
            "$SemanticAttributes.MESSAGING_DESTINATION_KIND" "topic"
            "$SemanticAttributes.MESSAGING_OPERATION" "process"
            "$SemanticAttributes.MESSAGING_MESSAGE_PAYLOAD_SIZE_BYTES" Long
            "$SemanticAttributes.MESSAGING_KAFKA_PARTITION" { it >= 0 }
            "kafka.offset" Long
            "kafka.record.queue_time_ms" { it >= 0 }
          }
        }
      }
    }
  }
}
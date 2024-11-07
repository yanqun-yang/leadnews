package com.heima.kafka.sample;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * 生产者
 */
public class ProducerQuickStart {
    public static void main(String[] args) {

        //1.kafka连接配置信息
        Properties prop = new Properties();
        //kafka连接地址
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.200.130:9092");
        //key和value的序列化
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        //2.创建kafka生产者对象
        KafkaProducer<String, String> producer = new KafkaProducer<>(prop);

        //3.发送消息
        /**
         * 第一个参数：topic
         * 第二个参数：消息的key
         * 第三个参数：消息的value
         */
        ProducerRecord<String, String> kvProducerRecord = new ProducerRecord<String, String>("itcast-topic-input", "key-001", "hello kafka");
        producer.send(kvProducerRecord);
        kvProducerRecord = new ProducerRecord("itcast-topic-input", "key-002", "hello itcast");
        producer.send(kvProducerRecord);
        //同步发送消息 -- 大量数据可能阻塞
//        RecordMetadata recordMetadata = null;
//        try {
//            recordMetadata = producer.send(kvProducerRecord).get();
//            System.out.println(recordMetadata.offset());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        //异步消息发送
        producer.send(kvProducerRecord, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if(e != null){
                    System.out.println("记录异常信息到日志表中");
                }
                System.out.println(recordMetadata.offset());
            }
        });

        //4.关闭消息通道 必须要关闭，否则消息发送不成功
        producer.close();
    }
}

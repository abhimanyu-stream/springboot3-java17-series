package com.stream.order.processing.poller.configuration;


import com.stream.order.processing.poller.model.OrderOutBoxEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.transaction.KafkaTransactionManager;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {


    private final  String bootstrapServer = "127.0.0.1:9092";
    @Bean
    public Map<String,Object> producerConfig(){


        //Properties prop = new Properties();
        Map<String, Object> prop = new HashMap<>();
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class);


        // TO have a safe producer where the writes are acknowledged, we should use below settings
        prop.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
        prop.put(ProducerConfig.ACKS_CONFIG	, "all");
        prop.put(ProducerConfig.RETRIES_CONFIG, Integer.toString(Integer.MAX_VALUE));
        prop.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "5");

        // To have a High throughput producer, we should use below settings
        prop.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
        prop.put(ProducerConfig.LINGER_MS_CONFIG, "20");
        prop.put(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(32*1024)); //32KB
        prop.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG,"tx-");


        return prop;
    }




    @Bean("producerFactoryOrderOutBoxEvent")
    public ProducerFactory<String, String> producerFactoryOrderOutBoxEvent(){
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }



    @Bean("kafkaTemplateOrderOutBoxEvent")
    public KafkaTemplate<String, String> kafkaTemplateOrderOutBoxEvent() {
        KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(producerFactoryOrderOutBoxEvent());
        kafkaTemplate.setTransactionIdPrefix("tx-");
        return kafkaTemplate;
    }

    @Bean("kafkaTransactionManagerOrderOutBoxEvent")
    public KafkaTransactionManager<String, String> kafkaTransactionManagerOrderOutBoxEvent(ProducerFactory<String, String> producerFactory) {
        return new KafkaTransactionManager<>(producerFactory);
    }

  /*  @Bean("producerFactoryOrderOutBoxEvent")
    public ProducerFactory<String, OrderOutBoxEvent> producerFactoryOrderOutBoxEvent(){
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }



    @Bean("kafkaTemplateOrderOutBoxEvent")
    public KafkaTemplate<String, OrderOutBoxEvent> kafkaTemplateOrderOutBoxEvent() {
        KafkaTemplate<String, OrderOutBoxEvent> kafkaTemplate = new KafkaTemplate<>(producerFactoryOrderOutBoxEvent());
        kafkaTemplate.setTransactionIdPrefix("tx-");
        return kafkaTemplate;
    }

    @Bean("kafkaTransactionManagerOrderOutBoxEvent")
    public KafkaTransactionManager<String, OrderOutBoxEvent> kafkaTransactionManagerOrderOutBoxEvent(ProducerFactory<String, OrderOutBoxEvent> producerFactory) {
        return new KafkaTransactionManager<>(producerFactory);
    }*/


}

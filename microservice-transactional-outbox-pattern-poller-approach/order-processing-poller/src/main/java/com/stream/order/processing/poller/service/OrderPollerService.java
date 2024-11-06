package com.stream.order.processing.poller.service;

import com.stream.order.processing.poller.model.OrderOutBoxEvent;
import com.stream.order.processing.poller.producer.MessagePublisher;
import com.stream.order.processing.poller.repository.OrderOutBoxEventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@EnableScheduling
public class OrderPollerService {



    private OrderOutBoxEventRepository orderOutBoxEventRepository;
    private MessagePublisher messagePublisher;

    @Autowired
    public OrderPollerService(OrderOutBoxEventRepository orderOutBoxEventRepository, MessagePublisher messagePublisher) {
        this.orderOutBoxEventRepository = orderOutBoxEventRepository;
        this.messagePublisher = messagePublisher;
    }

    @Scheduled(fixedRate = 60000)
    public void pollOutboxMessagesAndPublish() {

        //1. fetch unprocessed record
        List<OrderOutBoxEvent> unprocessedRecords = orderOutBoxEventRepository.findByProcessedFalse();

        log.info("unprocessed record count : {}", unprocessedRecords.size());

        //2. publish record to kafka/queue

        unprocessedRecords.forEach(outbox -> {
            try {
                messagePublisher.publish(outbox);
                //update the message status to processed=true to avoid duplicate message processing
                outbox.setProcessed(true);
                orderOutBoxEventRepository.save(outbox);

            } catch (Exception exception) {
                log.error(exception.getMessage());
            }
        });


    }
}
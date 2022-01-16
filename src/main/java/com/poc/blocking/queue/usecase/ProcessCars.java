package com.poc.blocking.queue.usecase;

import com.poc.blocking.queue.domain.Car;
import com.poc.blocking.queue.external.WirermockClient;
import com.poc.blocking.queue.usecase.blockingqueue.ConsumerCar;
import com.poc.blocking.queue.usecase.blockingqueue.ProducerCar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class ProcessCars {
    private static final Logger LOG = LoggerFactory.getLogger(ProcessCars.class);

    private final WirermockClient wirermockClient;

    public ProcessCars(final WirermockClient wirermockClient) {
        this.wirermockClient = wirermockClient;
    }

    public void process(final Integer queueCapacity) throws InterruptedException {
        LOG.info("Starting process using blocking queue...");

        final BlockingQueue<Car> queue = new LinkedBlockingQueue<>(queueCapacity);
        final Car poisonPill = new Car("stopQueue", "stopQueue");
        List<Car> cars = create();

        final Thread producerThread = new Thread(new ProducerCar(queue, poisonPill, cars));
        final Thread consumerThread = new Thread(new ConsumerCar(queue, poisonPill, wirermockClient));

        producerThread.start();
        consumerThread.start();
        producerThread.join();
        consumerThread.join();

        LOG.info("Finishing process using blocking queue...");
    }

    private List<Car> create() {
        List<Car> elements = new ArrayList<>();

        for (int i = 0; i < 200; i++) {
            elements.add(new Car("car " + i, "color " + i));
        }

        return elements;
    }
}

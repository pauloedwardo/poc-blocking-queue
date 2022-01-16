package com.poc.blocking.queue.usecase.blockingqueue;

import com.poc.blocking.queue.domain.Car;
import com.poc.blocking.queue.external.WirermockClient;
import feign.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;

public class ConsumerCar implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(ConsumerCar.class);

    private final BlockingQueue<Car> queue;
    private final Car poisonPill;
    private final WirermockClient client;

    public ConsumerCar(final BlockingQueue<Car> queue, final Car poisonPill, final WirermockClient client) {
        this.queue = queue;
        this.poisonPill = poisonPill;
        this.client = client;
    }

    @Override
    public void run() {

        try {
            while (true) {
                final Car take = queue.take();
                consume(take);

                if (take.equals(poisonPill)) {
                    break;
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    private void consume(final Car take) throws InterruptedException {
        LOG.info("[Consumer] Take [{}]", take.toString());

        final Response response = client.sendCar();

        LOG.info("Wiremock response: [{}]", response);

        Thread.sleep(500);
    }
}

package com.poc.blocking.queue.usecase.blockingqueue;

import com.poc.blocking.queue.domain.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class ProducerCar implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(ProducerCar.class);

    private final BlockingQueue<Car> queue;
    private final Car poisonPill;
    private final List<Car> cars;

    public ProducerCar(final BlockingQueue<Car> queue, final Car poisonPill, final List<Car> cars) {
        this.queue = queue;
        this.poisonPill = poisonPill;
        this.cars = cars;
    }

    @Override
    public void run() {

        try {
            produce();
        } catch (InterruptedException e) {
            LOG.error("Failed to run cars producer...", e);
            Thread.currentThread().interrupt();
        } finally {
            while (true) {
                try {
                    queue.put(poisonPill);
                    break;
                } catch (InterruptedException e) {
                    LOG.error("Failed to put cars producer poison pill...", e);
                }
            }
        }
    }

    private void produce() throws InterruptedException {
        LOG.info("List cars size: [{}]", this.cars.size());

        for (int i = 0; i < this.cars.size(); i++) {
            LOG.info("[Producer] Put [{}]", i);

            queue.put(this.cars.get(i));

            LOG.info("[Producer] Queue remainingCapacity [{}]", queue.remainingCapacity());
            Thread.sleep(100);
        }

    }
}

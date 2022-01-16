package com.poc.blocking.queue.usecase;

import com.poc.blocking.queue.external.WirermockClient;
import feign.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProcessCars {
    private static final Logger LOG = LoggerFactory.getLogger(ProcessCars.class);

    private final WirermockClient wirermockClient;

    public ProcessCars(final WirermockClient wirermockClient) {
        this.wirermockClient = wirermockClient;
    }

    public void process() {
        LOG.info("Calling wiremock client...");

        final Response response = wirermockClient.sendCar();

        LOG.info("Wiremock response: [{}]", response);
    }
}

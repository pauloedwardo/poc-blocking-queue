package com.poc.blocking.queue.endpoint;

import com.poc.blocking.queue.usecase.ProcessCars;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarsController {

    private static final Logger LOG = LoggerFactory.getLogger(CarsController.class);

    private final ProcessCars processCars;

    public CarsController(final ProcessCars processCars) {
        this.processCars = processCars;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/execute")
    public ResponseEntity<String> execute(@RequestParam(value = "queueCapacity") final Integer queueCapacity) {
        LOG.info("Starting to execute endpoint...");

        processCars.process(queueCapacity);

        return new ResponseEntity<String>("Executed!", HttpStatus.OK);
    }
}

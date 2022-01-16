package com.poc.blocking.queue.external;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "wiremock-local", url = "http://localhost:8090")
public interface WirermockClient {

    @RequestMapping(method = RequestMethod.GET, value = "/api/test")
     Response sendCar();
}

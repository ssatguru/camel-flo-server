/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssatguru.camel.flo.api;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 *
 * @author satguru
 */
@RestController
@RequestMapping("/url")
public class FloRestController {

    Logger logger = Logger.getLogger(FloRestController.class.getName());
    CamelContext camelContext;

    public FloRestController() {
        logger.info("++++ creating a camel Context ++++ ");
        camelContext = new DefaultCamelContext();
    }

    @RequestMapping(method = GET)
    public List<Object> list() {
        return null;
    }

    @RequestMapping(value = "/{id}", method = GET)
    public Object get(@PathVariable String id) throws Exception {
        if (id.equals("start")) {
            if (camelContext.getStatus().isStarted()) {
                return "flow already started";
            }
            camelContext.addRoutes(new RouteBuilder() {
                public void configure() {
                    from("timer://foo?fixedRate=true&period=5000").log("still running");
                }
            });
            camelContext.start();
            return "started a flow with a timer ";
        } else if (id.equals("stop")) {
            if (camelContext.getStatus().isStarted()) {
                camelContext.stop();
                return "flow stopped";
            } else {
                logger.info("route not running");
                return "cannot stop as flow is not running";
            }
        } else {
            return "invalid request";
        }
    }

    @RequestMapping(value = "/{id}", method = PUT)
    public ResponseEntity<?> put(@PathVariable String id, @RequestBody Object input) {
        return null;
    }

    @RequestMapping(value = "/{id}", method = POST)
    public ResponseEntity<?> post(@PathVariable String id, @RequestBody Object input) {
        return null;
    }

    @RequestMapping(value = "/{id}", method = DELETE)
    public ResponseEntity<Object> delete(@PathVariable String id) {
        return null;
    }

}

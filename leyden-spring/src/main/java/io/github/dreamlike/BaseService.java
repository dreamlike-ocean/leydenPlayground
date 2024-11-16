package io.github.dreamlike;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface BaseService {
     static final Logger log = LoggerFactory.getLogger(BaseService.class);


    default void run() {
        log.info("Hello world!");
    }
}

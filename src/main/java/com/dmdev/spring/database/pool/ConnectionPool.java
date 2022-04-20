package com.dmdev.spring.database.pool;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;

@Slf4j
@Component("pool1")
@RequiredArgsConstructor
public class ConnectionPool {

    @Value("${db.username}")
    private final String username;
    @Value("${db.pool.size}")
    private final Integer poolsize;
//    private List<Object> args;
//    private Map<String, Object> properties;

//    @Autowired
//    public ConnectionPool(@Value("${db.username}") String username,
//                          @Value("${db.pool.size}") Integer poolsize
////                          List<Object> args,
////                          Map<String, Object> properties
//    ) {
//        this.username = username;
//        this.poolsize = poolsize;
////        this.args = args;
////        this.properties = properties;
//    }

    @PostConstruct
    private void init() {
        log.info("Init connection pool");
    }

    @PreDestroy
    private void destroy() {
        log.info("Clean connection pool");
    }
}

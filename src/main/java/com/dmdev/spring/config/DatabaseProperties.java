package com.dmdev.spring.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.List;
import java.util.Map;

//@Data
//@NoArgsConstructor

// immutable class
//@Value
//@ConstructorBinding if DatabaseProperties was immutable class
@ConfigurationProperties(prefix = "db") // the annotation seeks application.properties (or .yaml/yml) to find props with prefix "db" and then create our custom new DatabaseProperties bean
public record DatabaseProperties(   String username,
                                    String password,
                                    String driver,
                                    String url,
                                    String hosts,
                                    PoolProperties pool,
                                    List<PoolProperties> pools,
                                    Map<String, Object> properties) {
    

//    @Data
//    @NoArgsConstructor
//    @Value
    public static record PoolProperties(   String name,
                                    Integer size,
                                    Integer timeout) {
    }
}

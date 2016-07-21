package com.wanda.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by 钱斌 on 2016/7/1.
 */

@ConfigurationProperties(prefix = "config")
@Configuration
public class Config {
    private String logPath;
    private String linePath;
    private String table;

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getLinePath() {
        return linePath;
    }

    public void setLinePath(String linePath) {
        this.linePath = linePath;
    }

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

}

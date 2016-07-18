package com.wanda;

import com.wanda.config.Config;
import com.wanda.service.LogMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableConfigurationProperties({Config.class})
@EnableAutoConfiguration
public class LogMessageApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(LogMessageApplication.class);

    @Autowired
    private Config config;

    @Autowired
    private LogMessageService logMessageService;

    @Override
    public void run(String... strings) throws Exception {
        try {
            int messageLine = logMessageService.getMessageLine(config.getLinePath());
            List<String> allMessage = Files.lines(Paths.get(config.getLogPath())).collect(Collectors.toList());
            List<String> messages = allMessage.subList(messageLine, allMessage.size());
            messageLine = logMessageService.loadMessage(messages) + messageLine;
            logMessageService.setMessageLine(messageLine,config.getLinePath());
        } catch (Exception e) {
            logger.error("导入数据发生异常", e);
        }
    }

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(LogMessageApplication.class, args);
    }


}

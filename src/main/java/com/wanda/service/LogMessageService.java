package com.wanda.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanda.bean.Message;
import com.wanda.common.Constants;
import com.wanda.dao.MessageDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 钱斌 on 2016/7/14.
 */
@Service
public class LogMessageService {

    private Logger logger = LoggerFactory.getLogger(LogMessageService.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MessageDao messageDao;



    /**
     * 将异常message导入数据库
     *
     * @param messages
     */
    public int loadMessage(List<String> messages) {
        try {
            if (null != messages && !messages.isEmpty()) {
                List<Message> objects = messages.stream().map(line -> {
                    try {
                        return objectMapper.readValue(line, Message.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }).filter(message -> message != null).collect(Collectors.toList());
                messageDao.saveMessages(objects, Constants.MESSAGE_EXCEPTION);
                logger.warn("插入异常数据" + objects.size() + "条");
                return messages.size();
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return 0;
    }

    public int getMessageLine(String linePath) throws Exception{
        BufferedReader dis = new BufferedReader(new FileReader(linePath));
        int i = Integer.valueOf(dis.readLine());
        dis.close();
        return i;
    }

    public void setMessageLine(int messageLine, String linePath) throws Exception {
        BufferedWriter dos = new BufferedWriter(new FileWriter(linePath));
        dos.write(String.valueOf(messageLine));
        dos.close();
    }
}

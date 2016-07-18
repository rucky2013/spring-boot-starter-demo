package com.wanda.dao;

import com.wanda.bean.Message;

import java.util.List;

/**
 * Created by 钱斌 on 2016/7/13.
 */
public interface MessageDao {

    Boolean saveMessages(List<Message> storeMessage, String status);

}

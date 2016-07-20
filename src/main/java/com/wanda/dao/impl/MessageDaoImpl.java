package com.wanda.dao.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanda.bean.Message;
import com.wanda.common.SystemException;
import com.wanda.dao.MessageDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by 钱斌 on 2016/7/13.
 */
@Repository
public class MessageDaoImpl implements MessageDao {

    private static Logger logger = LoggerFactory.getLogger("message");

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    protected JdbcTemplate jdbcTemplate;


    @Override
    public Boolean saveMessages(List<Message> storeMessage, String status) {
        try {
            final List<Message> mlist = storeMessage;
            String sql = "insert into rawdata_d(time, unixTime, plaza, floor, xCoordinate,yCoordinate ,mac, status)" +
                    " values(?,?,?,?,?,?,?,?)";
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                    preparedStatement.setTimestamp(1, new Timestamp(mlist.get(i).getTime() * 1000L));
                    preparedStatement.setInt(2, mlist.get(i).getTime());
                    preparedStatement.setString(3, mlist.get(i).getPlaza());
                    preparedStatement.setString(4, mlist.get(i).getFloor());
                    preparedStatement.setString(5, mlist.get(i).getX());
                    preparedStatement.setString(6, mlist.get(i).getY());
                    preparedStatement.setString(7, mlist.get(i).getMac());
                    preparedStatement.setString(8, status);
                }
                @Override
                public int getBatchSize() {
                    return mlist.size();
                }
            });
            return true;
        } catch (Exception e) {
            throw new SystemException(e);
        }
    }

}

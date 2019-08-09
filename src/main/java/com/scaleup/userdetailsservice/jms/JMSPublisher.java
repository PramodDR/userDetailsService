package com.scaleup.userdetailsservice.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.scaleup.userdetailsservice.config.JMSConfig;
import com.scaleup.userdetailsservice.dto.UserDataDto;

@Service
public class JMSPublisher {
	private static Logger log = LoggerFactory.getLogger(JMSPublisher.class);
	
	@Autowired
    private JmsTemplate jmsTemplate;

    public void publishUserData(UserDataDto userData) {
        log.info("sending with publishUserData() to queue <" + userData + ">");
        jmsTemplate.convertAndSend(JMSConfig.QUEUE_NAME, userData);
    }

}

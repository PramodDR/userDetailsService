package com.scaleup.userdetailsservice.domain;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.scaleup.userdetailsservice.db.entity.UserDetailsEntity;
import com.scaleup.userdetailsservice.db.repository.UserDetailsRepository;
import com.scaleup.userdetailsservice.dto.UserDataDto;
import com.scaleup.userdetailsservice.exception.BadRequestException;
import com.scaleup.userdetailsservice.exception.ConflictException;
import com.scaleup.userdetailsservice.jms.JMSPublisher;
import com.scaleup.userdetailsservice.util.EmailValidatorUtil;

@Component
public class UserDetailsDomainManagerImpl implements UserDetailsDomainManager {
	@Autowired
	private UserDetailsRepository userDetailsRepository;
	
	@Autowired
	private JMSPublisher jmsPublisher;

	private static Logger log = LoggerFactory.getLogger(UserDetailsDomainManagerImpl.class);

	@Override
	public CompletableFuture<Void> addUser(UserDataDto userDataDto) {
		// TODO Auto-generated method stub
		return CompletableFuture.supplyAsync(() -> {
			throwIfNullInputs(userDataDto);
			boolean isValid = EmailValidatorUtil.isValidEmail(userDataDto.getEmail());
			if (isValid) {
				log.info("Email is valid :: Inserting User");
				UserDetailsEntity userDetailsEntity = userDetailsRepository.findByEmail(userDataDto.getEmail());
				if (null == userDetailsEntity) {
					UserDetailsEntity userDetailsSave = createUserEntity(userDataDto);
					UserDetailsEntity savedEntity = userDetailsRepository.save(userDetailsSave);
					//TODO Publishing the data but the status should be sent along with object
					//jmsPublisher.publishUserData(savedEntity.toDto());
				} else {
					log.info("User already exists :: Conflict exception");
					throw new ConflictException("User already exists");
				}
			} else {
				throw new BadRequestException("Email is format is not valid !!!!!!!!");
			}
			return null;
		}, ForkJoinPool.commonPool());
	}
	
	@Override
	public CompletableFuture<Void> deleteUser(String email) {
		// TODO Auto-generated method stub
		return CompletableFuture.supplyAsync(() -> {
			boolean isValid = EmailValidatorUtil.isValidEmail(email);
			if (isValid) {
				log.info("Email is valid :: Inserting User");
				UserDetailsEntity userDetailsEntity = userDetailsRepository.findByEmail(email);
				if (null != userDetailsEntity) {
					userDetailsRepository.delete(userDetailsEntity);
					//TODO Publishing the data but the status should be sent along with object
					//jmsPublisher.publishUserData(userDetailsEntity.toDto());
				} else {
					log.info("Email not exists :: Bad Request");
					throw new BadRequestException("Email does not exists !!");
				}
			} else {
				throw new BadRequestException("Email is format is not valid !!!!!!!!");
			}
			return null;
		}, ForkJoinPool.commonPool());
	}

	private UserDetailsEntity createUserEntity(UserDataDto userDataDto) {
		UserDetailsEntity userDetailsEntity = new UserDetailsEntity();
		userDetailsEntity.setEmail(userDataDto.getEmail());
		userDetailsEntity.setFirstName(userDataDto.getFirstName());
		userDetailsEntity.setLastName(userDataDto.getLastName());
		return userDetailsEntity;
	}

	private void throwIfNullInputs(UserDataDto userDataDto) {
		if (StringUtils.isEmpty(userDataDto.getFirstName()) || StringUtils.isEmpty(userDataDto.getLastName())) {
			throw new BadRequestException("One or more of inputs are null");
		}
	}

}

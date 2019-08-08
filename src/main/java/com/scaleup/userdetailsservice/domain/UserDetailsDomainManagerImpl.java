package com.scaleup.userdetailsservice.domain;

import java.util.concurrent.CompletableFuture;






import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;






import com.scaleup.userdetailsservice.db.entity.UserDetailsEntity;
import com.scaleup.userdetailsservice.db.repository.UserDetailsRepository;
import com.scaleup.userdetailsservice.dto.UserDataDto;
import com.scaleup.userdetailsservice.exception.BadRequestException;import com.scaleup.userdetailsservice.exception.ConflictException;
import com.scaleup.userdetailsservice.util.EmailValidatorUtil;


@Component
public class UserDetailsDomainManagerImpl implements UserDetailsDomainManager {
	@Autowired
	private UserDetailsRepository userDetailsRepository;
	
	private static Logger log = LoggerFactory
			.getLogger(UserDetailsDomainManagerImpl.class);
	
	@Override
	public CompletableFuture<Void> addUser(UserDataDto userDataDto) {
		// TODO Auto-generated method stub
		return CompletableFuture.runAsync(() ->{
			throwIfNullInputs(userDataDto);
			boolean isValid = EmailValidatorUtil.isValidEmail(userDataDto.getEmail());
			if(isValid){
				UserDetailsEntity userDetailsEntity =  userDetailsRepository.findByEmail(userDataDto.getEmail());
				if(null == userDetailsEntity){
					UserDetailsEntity userDetailsSave = createUserEntity(userDataDto);
					userDetailsRepository.save(userDetailsSave);
				}else{
					throw new ConflictException("User already exists");
				}
			}else{
				throw new BadRequestException("Email is format is not valid !!!!!!!!");
			}
		});
	}

	
	private UserDetailsEntity createUserEntity(UserDataDto userDataDto){
		UserDetailsEntity userDetailsEntity = new UserDetailsEntity();
		userDetailsEntity.setEmailAddress(userDataDto.getEmail());
		userDetailsEntity.setFirstName(userDataDto.getFirstName());
		userDetailsEntity.setLastName(userDataDto.getLastName());
		return userDetailsEntity;
	}
	private void throwIfNullInputs(UserDataDto userDataDto) {
		if (StringUtils.isEmpty(userDataDto.getFirstName())
				|| StringUtils.isEmpty(userDataDto.getLastName())) {
			throw new BadRequestException("One or more of inputs are null");
		}
	}

}

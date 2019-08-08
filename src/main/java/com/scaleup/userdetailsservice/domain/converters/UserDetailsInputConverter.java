package com.scaleup.userdetailsservice.domain.converters;

import com.scaleup.userdetailsservice.dto.UserDataDto;
import com.scaleup.userdetailsservice.jaxb.UserData;

public class UserDetailsInputConverter {

	
	public UserDataDto convert(UserData userData) {
		if(null == userData) {
			return null;
		}
		UserDataDto userDataDto = new UserDataDto();
		userDataDto.setFirstName(userData.getFirstName());
		userDataDto.setLastName(userData.getLastName());
		userDataDto.setEmail(userData.getEmail());
		return userDataDto;
	}
}

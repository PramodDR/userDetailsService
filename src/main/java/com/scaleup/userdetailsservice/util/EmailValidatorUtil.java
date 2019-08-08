package com.scaleup.userdetailsservice.util;

import java.util.regex.Pattern;

import com.scaleup.userdetailsservice.exception.BadRequestException;

public class EmailValidatorUtil {

	public static boolean isValidEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@"
				+ "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";

		Pattern pat = Pattern.compile(emailRegex);
		if (email == null)
			throw new BadRequestException("");
		return pat.matcher(email).matches();
	}

}

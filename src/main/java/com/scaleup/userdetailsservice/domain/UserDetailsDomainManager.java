package com.scaleup.userdetailsservice.domain;

import java.util.concurrent.CompletableFuture;

import com.scaleup.userdetailsservice.dto.UserDataDto;

public interface UserDetailsDomainManager {
	CompletableFuture<Void> addUser(UserDataDto userDataDto);
	CompletableFuture<Void> deleteUser(String email);
}

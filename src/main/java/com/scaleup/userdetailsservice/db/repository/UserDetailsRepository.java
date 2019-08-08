package com.scaleup.userdetailsservice.db.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.scaleup.userdetailsservice.db.entity.UserDetailsEntity;

@Repository
public interface UserDetailsRepository extends CrudRepository<UserDetailsEntity,String> {

	
	public UserDetailsEntity findByEmail(String email);
}

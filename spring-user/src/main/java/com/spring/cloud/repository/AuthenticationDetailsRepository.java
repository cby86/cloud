package com.spring.cloud.repository;

import com.spring.cloud.base.BaseRepository;
import com.spring.cloud.entity.Authentication;
import com.spring.cloud.entity.AuthenticationDetails;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthenticationDetailsRepository extends BaseRepository<AuthenticationDetails, String> {

}

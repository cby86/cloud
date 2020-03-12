package com.spring.cloud.repository;

import com.spring.cloud.base.BaseRepository;
import com.spring.cloud.entity.Authentication;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthenticationRepository extends BaseRepository<Authentication, String> {
    @Modifying
    @Query("delete  from  Authentication  obj where obj.menuId=?1")
    void deleteAllAuthenticationByMenuId(String menuId);

    @Query("select obj  from  Authentication  obj where obj.role is null ")
    List<Authentication> findAllAuthentication();
}

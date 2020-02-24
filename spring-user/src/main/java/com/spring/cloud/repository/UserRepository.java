package com.spring.cloud.repository;
import com.spring.cloud.base.BaseRepository;
import com.spring.cloud.entity.Authentication;
import com.spring.cloud.entity.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends BaseRepository<User,String> {

    @Query("select obj from User obj where obj.deleted=false and obj.username=?1")
    User findUserByName(String username);

    @Query("select auth from User obj left join obj.roles role left join role.authentications auth where obj.deleted=false and auth is not null and obj.id=?1 order by auth.sort asc")
    List<Authentication> findAuthentication(String userId);
}

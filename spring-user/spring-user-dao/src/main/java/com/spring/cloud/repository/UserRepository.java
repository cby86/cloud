package com.spring.cloud.repository;
import com.spring.cloud.base.BaseRepository;
import com.spring.cloud.entity.User;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends BaseRepository<User,String> {

    @Query("select obj from User obj where obj.deleted=false and obj.username=?1")
    User findUserByName(String username);
}

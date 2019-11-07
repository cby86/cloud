package com.spring.cloud.repository;
import com.spring.cloud.base.BaseRepository;
import com.spring.cloud.entity.Role;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends BaseRepository<Role,String> {

    @Query("select obj from Role obj where obj.deleted=false")
    List<Role> findRoles();
    @Query("select obj from Role obj where obj.deleted=false and obj.id=?1")
    Role findRoleById(String roleId);
}

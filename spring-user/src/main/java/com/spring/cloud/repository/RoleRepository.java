package com.spring.cloud.repository;

import com.spring.cloud.base.BaseRepository;
import com.spring.cloud.entity.Authentication;
import com.spring.cloud.entity.Role;
import com.spring.cloud.repository.component.ResourcePermit;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends BaseRepository<Role, String> {

    @Query("select obj from Role obj where obj.deleted=false and obj.id=?1")
    Role findRoleById(String roleId);

    @Query(value = "select new com.spring.cloud.repository.component.ResourcePermit(a.url,group_concat(obj.code,true)) from Role obj left join obj.authentications a  where obj.deleted=0 and a.deleted=0 and a.authentionType=1 group by a.url")
    List<ResourcePermit> loadAuthentications();

}

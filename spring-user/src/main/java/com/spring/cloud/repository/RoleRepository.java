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

    @Query(value = "select new com.spring.cloud.repository.component.ResourcePermit(d.systemResource,group_concat(obj.code)) " +
            "from Role obj left join obj.authentications a left join a.authenticationDetails d " +
            "where obj.deleted=0 and d.systemResource is not null group by d.systemResource")
    List<ResourcePermit> loadAuthentications();

    @Query("select auth from Role obj left join obj.authentications auth where auth.deleted=false and auth.menuId=?1 group  by obj.id")
    List<Authentication> findAuthenticationByMenuId(String menuId);
}

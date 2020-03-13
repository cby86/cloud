package com.spring.cloud.repository;
import com.spring.cloud.base.BaseRepository;
import com.spring.cloud.entity.App;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;

public interface AppRepository extends BaseRepository<App, String> {
    @Query("select obj from App obj where obj.deleted=false and obj.name=?1")
    App findByName(String name);
}

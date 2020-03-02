package com.spring.cloud.base;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.*;
import java.util.Date;

/**
 * User: chenby
 * Date: 13-10-15
 * Time: 下午3:30
 */
@MappedSuperclass
@Configurable(autowire = Autowire.BY_TYPE)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "sessionFactory"})
public abstract class IntIdBaseEntity implements Entity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name = "create_date")
    private Date createDate = new Date();

    @Column(name = "update_date")
    private Date updateDate=new Date();

    @Column(columnDefinition = "int default 0")
    private boolean deleted = false;

    @Version
    @Column(columnDefinition = "int default 0")
    private int version;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * 用于在更新时，更新最近的操作时间
     */
    @PreUpdate
    private void preUpdate() {
        this.updateDate = new Date();
    }

    /**
     * 用于在更新时，更新最近的操作时间
     */
    @PrePersist
    private void prePersist() {
        this.createDate = new Date();
    }

}

package com.hcskia.maianalyzerapi.pojo;

import jakarta.persistence.*;

@Table(name = "user")
@Entity
public class User{
    @Id
    @Column(name = "userid")
    private String userId;
    @Column(name = "qq")
    private String qq;

    public String getUserId(){
        return this.userId;
    }
    public void setUserId(String userid){
        this.userId = userid;
    }

    public String getQq(){
        return this.qq;
    }
    public void setQq(String qq){
        this.qq = qq;
    }

}

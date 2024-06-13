package com.hcskia.maianalyzerapi.repository;
import com.hcskia.maianalyzerapi.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(String UserId);
}
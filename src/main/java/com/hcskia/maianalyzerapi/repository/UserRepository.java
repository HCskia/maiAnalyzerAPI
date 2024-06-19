package com.hcskia.maianalyzerapi.repository;
import com.hcskia.maianalyzerapi.pojo.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(String UserId);
    @Transactional
    void deleteByUserId(String UserId);

    boolean existsByUserId(String UserId);
}
package com.example.social_media_app_uaa.repository;

import com.example.social_media_app_uaa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Collection;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
    Boolean existsByUsername(String username);
    List<UserEntity> findAllByIdIn(Collection<Long> userIds);
}

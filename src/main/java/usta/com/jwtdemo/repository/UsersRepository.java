package usta.com.jwtdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import usta.com.jwtdemo.entity.UsersEntity;

public interface UsersRepository extends JpaRepository<UsersEntity, Long> {
    @Query("SELECT u FROM UsersEntity u WHERE u.username=?1")
    public UsersEntity findByUsername(String userTemp);

}

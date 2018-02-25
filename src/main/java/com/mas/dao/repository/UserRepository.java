package com.mas.dao.repository;

import com.mas.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends CrudRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findUserByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.login = :login")
    User findUserByLogin(@Param("login") String login);

    @Query("SELECT u FROM User u WHERE u.fbId = :fbId")
    User findUserByfbId(@Param("fbId") String fbId);
}

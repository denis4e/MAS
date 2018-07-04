package com.mas.dao.repository;

import com.mas.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findUserByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.login = :login")
    User findUserByLogin(@Param("login") String login);

    @Query("SELECT u FROM User u WHERE u.fbId = :fbId")
    User findUserByFaceBookId(@Param("fbId") String fbId);

    @Query("SELECT u FROM User u WHERE u.googleId = :googleId")
    User findUserByGoogleId(@Param("googleId") String googleId);

    @Modifying
    @Transactional
    @Query(value = "update User  set email=:email, phone=:phone, user_name=:userName, last_name=:lastName where id_user=:userId", nativeQuery = true)
    int updateUser(@Param("userId") int userId, @Param("phone") String phone, @Param("email") String email, @Param("userName") String userName, @Param("lastName") String lastName);
}

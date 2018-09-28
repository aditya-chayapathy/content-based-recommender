package com.assignment1.repository;

import com.assignment1.pojo.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {

    Login findByUserIdAndTimestampAndLoginType(Long userId, Long timestamp, String loginType);

    List<Login> findTop8ByUserIdOrderByTimestampDesc(Long userId);

    List<Login> findTop8ByUserIdAndLoginTypeOrderByTimestampDesc(Long userId, String loginType);

    List<Login> findAllByUserIdAndLoginType(Long userId, String loginType);

    List<Login> findAllByUserIdAndLoginTypeAndTimestampGreaterThanOrderByTimestampAsc(Long userId, String loginType, Long timestamp);

}
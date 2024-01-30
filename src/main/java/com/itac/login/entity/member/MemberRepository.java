package com.itac.login.entity.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {


    static final String UPDATE_MEMBER_LAST_LOGIN = "UPDATE Member "
            + "SET LASTLOGINTIME = :lastLoginTime "
            + "WHERE EMAIL = :email";

    static final String CREATE_MEMBER = "insert into public.member(email,pwd) values('1','1')";
    static final String LOGIN_CHECK = "select 1 from member where email= :email and pwd= :password";
    static final String GET_PASSWORD = "select pwd from member where email= :email";



    @Transactional
    @Query(value=GET_PASSWORD, nativeQuery = true)
    public String getPassword(@Param("email") String email);

    @Transactional
    @Query(value=LOGIN_CHECK, nativeQuery = true)
    public int loginCheck(@Param("email") String email, @Param("password") String password);


    @Transactional
    @Modifying
    @Query(value=UPDATE_MEMBER_LAST_LOGIN, nativeQuery = true)
    public int updateMemberLastLogin(@Param("email") String email, @Param("lastLoginTime") LocalDateTime lastLoginTime);

    public Member findByEmail(String email);
    @Transactional
    @Modifying
    @Query(value="insert into public.member(email,pwd) values(:username,:password)",nativeQuery = true)
    public int registerMember(@Param(value = "username") String username,@Param(value = "password") String password);


}

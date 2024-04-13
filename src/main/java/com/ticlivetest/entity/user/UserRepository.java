package com.ticlivetest.entity.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    static final String CREATE_MEMBER = "insert into public.users(useremail,userpassword) values('1','1')";
    static final String LOGIN_CHECK = "select 1 from users where useremail= :useremail and userpassword= :userpassword";
    static final String GET_PASSWORD = "select userpassword from users where useremail= :useremail";



    @Transactional
    @Query(value=GET_PASSWORD, nativeQuery = true)
    public String getPassword(@Param("useremail") String email);

    @Transactional
    @Query(value=LOGIN_CHECK, nativeQuery = true)
    public int loginCheck(@Param("useremail") String email, @Param("userpassword") String password);

    public Users findByUserEmail(String userEmail);

    @Transactional
    @Modifying
    @Query(value="insert into public.users(useremail,userpassword,auth) values(:useremail,:userpassword,:auth)",nativeQuery = true)
    public int registerUser(@Param(value = "useremail") String username,@Param(value = "userpassword") String password,@Param(value = "auth") String auth);


    @Query(value="select auth from public.users where useremail=(:useremail)", nativeQuery = true)
    public String getAuth(@Param(value = "useremail") String useremail);

    @Query(value="select usernum from public.users where useremail=(:useremail)", nativeQuery = true)
    public int getId(@Param(value="useremail") String useremail);
}

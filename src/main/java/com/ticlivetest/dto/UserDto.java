package com.ticlivetest.dto;

import com.ticlivetest.entity.user.Users;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Getter
@Setter
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Component
public class UserDto {

    private long userNum;
    private String userEmail;

    public UserDto(Users user){
        this.userNum = user.getUserNum();
        this.userEmail = user.getUserEmail();
    }
}

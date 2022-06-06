package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeleteUserRes { // 필요한 이유는????
    private String name;
    private String nickName;
    private String phone;
    private String email;
    private String password;
}

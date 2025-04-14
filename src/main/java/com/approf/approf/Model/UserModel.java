package com.approf.approf.Model;

import com.approf.approf.Model.UserRole;
import lombok.Data;

@Data
public class UserModel {

    private String username;
    private String password;
    private UserRole role;
}
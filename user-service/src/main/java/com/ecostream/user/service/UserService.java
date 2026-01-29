package com.ecostream.user.service;

import com.ecostream.user.dto.Signin;
import com.ecostream.user.dto.Signup;
import com.ecostream.user.model.UserEntity;

import java.util.List;

public interface UserService {
    Signup register(Signup signup);
    String verify(Signin signin);
    List<UserEntity> getAllUsers();
}

package com.example.securityapp.mapper;


import com.example.securityapp.dto.*;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    User findByEmail(String email);

}

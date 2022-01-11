package com.example.securityapp.mapper;


import com.example.securityapp.dto.*;
import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface UserRolesMapper {

    List<UserRole> findRolesByEmail(String email);



}

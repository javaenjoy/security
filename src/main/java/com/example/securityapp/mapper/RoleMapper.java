package com.example.securityapp.mapper;

import com.example.securityapp.dto.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {

    List<Role> findRoleAll();

}

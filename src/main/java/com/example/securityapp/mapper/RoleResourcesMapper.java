package com.example.securityapp.mapper;

import com.example.securityapp.dto.RoleResources;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleResourcesMapper {
    List<RoleResources> findAll();
}

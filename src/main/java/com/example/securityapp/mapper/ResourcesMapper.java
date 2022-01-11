package com.example.securityapp.mapper;

import com.example.securityapp.dto.Resources;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ResourcesMapper {
    List<Resources> findAll();
}

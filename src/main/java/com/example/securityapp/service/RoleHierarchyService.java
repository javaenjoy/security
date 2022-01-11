package com.example.securityapp.service;


import com.example.securityapp.dto.Role;
import com.example.securityapp.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleHierarchyService {

    @Autowired
    private RoleMapper roleMapper;


    public String findAllHierarchy(){
        List<Role> roleHierarchies = roleMapper.findRoleAll();

        StringBuilder concatedRoles = new StringBuilder();

        for (int i = 0; i < roleHierarchies.size(); i++) {
            concatedRoles.append(roleHierarchies.get(i));
            if (i  < roleHierarchies.size() - 1) {
                concatedRoles.append(" > ");
            }
        }
        concatedRoles.append("\n");

        System.out.println("Role Hierarchy : " + concatedRoles.toString());
        return concatedRoles.toString();
    }

}
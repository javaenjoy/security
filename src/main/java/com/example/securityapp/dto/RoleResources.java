package com.example.securityapp.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RoleResources {
    private Integer id;
    private String resourceName;
    private String resourceType;
    private String roleName;
    private Integer roleDesc;
}

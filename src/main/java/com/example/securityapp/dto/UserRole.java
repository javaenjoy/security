package com.example.securityapp.dto;


import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRole {
    private String id;
    private Integer roleDesc;
    private String roleName;
    private String resourceName;
}

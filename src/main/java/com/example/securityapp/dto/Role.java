package com.example.securityapp.dto;


import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    private int roleId;
    private int roleDesc;
    private String roleName;
}

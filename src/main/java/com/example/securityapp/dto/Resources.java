package com.example.securityapp.dto;


import lombok.*;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Resources {
    private Integer resourceId;
    private String resourceName;
    private String resourceType;
}


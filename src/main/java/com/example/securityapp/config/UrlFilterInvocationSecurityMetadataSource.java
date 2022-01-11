package com.example.securityapp.config;

import com.example.securityapp.dto.RoleResources;
import com.example.securityapp.mapper.RoleResourcesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.*;


public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private RoleResourcesMapper roleResourcesMapper;


    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        HttpServletRequest request = ((FilterInvocation) object).getRequest();

        List<RoleResources> roleResources = roleResourcesMapper.findAll();

        LinkedHashMap<RequestMatcher, List<ConfigAttribute>> requestMap = new LinkedHashMap<>();

        for (RoleResources roleResource : roleResources) {
            if (requestMap.containsKey(new AntPathRequestMatcher(roleResource.getResourceName()))) {
                requestMap.get(new AntPathRequestMatcher(roleResource.getResourceName())).add(new SecurityConfig(roleResource.getRoleName()));
            } else {
                List<ConfigAttribute> arr = new ArrayList<>();
                arr.add(new SecurityConfig(roleResource.getRoleName()));
                requestMap.put(new AntPathRequestMatcher(roleResource.getResourceName()), arr);
            }
        }

        System.out.println("request : " + request.getRequestURI());
        System.out.println("requestMap : " + requestMap);

        if (requestMap != null) {
            var flag = requestMap.entrySet().stream()
                    .filter(entry -> entry.getKey().matches(request))
                    .findFirst()
                    .map(Map.Entry::getValue)
                    .orElseGet(ArrayList::new);

            System.out.println("flag " + flag);

            return flag;
        }
        return null;
    }


    @Override
    // 모든 권한 목록을 가져온다
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        /*
        return requestMap.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        */
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }


}
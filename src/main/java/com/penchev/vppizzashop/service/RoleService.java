package com.penchev.vppizzashop.service;

import com.penchev.vppizzashop.domain.entities.Role;

import java.util.LinkedHashSet;
import java.util.Set;

public interface RoleService {

    void seedRolesInDb();

    Set<Role> findAllAuthorities();

    Role getByAuthority(String authority);
}

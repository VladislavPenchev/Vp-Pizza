package com.penchev.vppizzashop.service;

import com.penchev.vppizzashop.domain.entities.Role;
import com.penchev.vppizzashop.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void seedRolesInDb() {
        if (this.roleRepository.count() == 0) {
            this.roleRepository.saveAndFlush(new Role("ROLE_USER"));
            this.roleRepository.saveAndFlush(new Role("ROLE_MODERATOR"));
            this.roleRepository.saveAndFlush(new Role("ROLE_ADMIN"));
            this.roleRepository.saveAndFlush(new Role("ROLE_ROOT"));
        }
    }

    @Override
    public Set<Role> findAllAuthorities(){
        return new LinkedHashSet<>(this.roleRepository.findAll());
    }

    @Override
    public Role getByAuthority(String authority) {
        return this.roleRepository.findByAuthority(authority).orElse(null);
    }
}

package com.penchev.vppizzashop.service;

import com.penchev.vppizzashop.domain.entities.Role;
import com.penchev.vppizzashop.domain.entities.User;
import com.penchev.vppizzashop.domain.models.services.EditServiceModel;
import com.penchev.vppizzashop.domain.models.services.RegisterServiceModel;
import com.penchev.vppizzashop.domain.models.view.EditProfileViewModel;
import com.penchev.vppizzashop.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Validator validator;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder, Validator validator) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.validator = validator;
    }

    @Override
    public List<String> validateForm(Object form) {
        return validator.validate(form).stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        if(usernameOrEmail.contains("@")){
            return this.userRepository
                    .findByEmail(usernameOrEmail)
                    .orElseThrow(() -> new EntityNotFoundException("Email not found!"));
        }else {
            return this.userRepository
                    .findByUsername(usernameOrEmail)
                    .orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
        }
    }

    @Override
    public void register(RegisterServiceModel registerServicemodel) {
        this.roleService.seedRolesInDb();
        User user = this.modelMapper.map(registerServicemodel, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        if (this.userRepository.count() == 0) {
            user.setAuthorities(this.roleService.findAllAuthorities());
        } else {
            Set<Role> roles = new LinkedHashSet<>();
            Role role = this.roleService.getByAuthority("USER_ROLE");
            roles.add(role);
            user.setAuthorities(roles);
        }

        this.userRepository.save(user);
    }

    @Override
    public String checkEmailExists(String email) {
        String result = new String("");

        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            result = "email.already.exists";
        }
        return result;
    }

    @Override
    public String checkUsernameExists(String username) {
        String result = new String("");

        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            result = "username.already.exists";
        }
        return result;
    }

    @Override
    public String checkUserExistByEmailAndId(String email, String id) {
        String result = new String("");

        User user = userRepository.findByEmailAndId(email, id).orElse(null);
        if (user == null) {
            result = "email.already.exists";
        }
        return result;
    }

    @Override
    public void editProfile(EditServiceModel editServiceModel, String username) {
        User currentUser = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found!"));

        currentUser.setFirstName(editServiceModel.getFirstName());
        currentUser.setSurName(editServiceModel.getSurName());
        currentUser.setEmail(editServiceModel.getEmail());

        this.userRepository.save(currentUser);
    }

    @Override
    public EditProfileViewModel loadEditProfile(String username) {
        return this.userRepository.findByUsername(username)
                .map(m -> modelMapper.map(m, EditProfileViewModel.class))
                .orElseThrow(() -> new UsernameNotFoundException("Username not found!"));

    }
}

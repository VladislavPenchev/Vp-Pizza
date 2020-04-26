package com.penchev.vppizzashop.service;

import com.penchev.vppizzashop.domain.models.services.EditServiceModel;
import com.penchev.vppizzashop.domain.models.services.RegisterServiceModel;
import com.penchev.vppizzashop.domain.models.view.EditProfileViewModel;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<String> validateForm(Object form);

    UserDetails loadUserByUsername(String username);

    void register(RegisterServiceModel registerServicemodel);

    String checkEmailExists(String email);

    String checkUsernameExists(String username);

    String checkUserExistByEmailAndId(String email, String id);

    void editProfile(EditServiceModel editServiceModel, String username);

    EditProfileViewModel loadEditProfile(String username);
}

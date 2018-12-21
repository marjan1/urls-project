package com.eadvocate.util;

import com.eadvocate.persistence.model.Privilege;
import com.eadvocate.persistence.model.Role;
import com.eadvocate.persistence.model.User;
import com.eadvocate.rest.dto.PrivilegeDto;
import com.eadvocate.rest.dto.RoleDto;
import com.eadvocate.rest.dto.UserDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ConversionUtil {

    private ModelMapper modelMapper;

    public UserDto convertToDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        userDto.setRoleDtos(user.getRoles().stream()
                .map(this::convertToDto).collect(Collectors.toSet()));
        return userDto;
    }

    public User convertToEntity(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setRoles(userDto.getRoleDtos().stream()
                .map(this::convertToEntity).collect(Collectors.toSet()));
        return user;
    }

    public RoleDto convertToDto(Role role) {
         RoleDto roleDto = modelMapper.map(role, RoleDto.class);
//        roleDto.setUserDtos(role.getUsers().stream()
//                .map(this::convertToDto).collect(Collectors.toSet()));
        roleDto.setPrivilegeDtos(role.getPrivileges().stream()
                .map(this::convertToDto).collect(Collectors.toSet()));
        return roleDto;
    }

    public Role convertToEntity(RoleDto roleDto) {
        Role role = modelMapper.map(roleDto, Role.class);
//        role.setUsers(roleDto.getUserDtos().stream()
//                .map(this::convertToEntity).collect(Collectors.toSet()));
        role.setPrivileges(roleDto.getPrivilegeDtos().stream()
                .map(this::convertToEntity).collect(Collectors.toSet()));
        return role;
    }

    public PrivilegeDto convertToDto(Privilege privilege) {
        PrivilegeDto privilegeDto = modelMapper.map(privilege, PrivilegeDto.class);
//        privilegeDto.setRoleDtos(privilege.getRoles().stream()
//                .map(this::convertToDto).collect(Collectors.toSet()));
        return privilegeDto;
    }

    public Privilege convertToEntity(PrivilegeDto privilegeDto) {
        Privilege privilege = modelMapper.map(privilegeDto, Privilege.class);
//        privilege.setRoles(privilegeDto.getRoleDtos().stream()
//                .map(this::convertToEntity).collect(Collectors.toSet()));
        return privilege;
    }


}

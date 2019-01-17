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

/**
 * Conversion util class for converting Model(Entity) classes to
 * appropriate Dto classes and vice versa.
 */
@Component
@AllArgsConstructor
public class ConversionUtil {

    private ModelMapper modelMapper;

    /**
     * Convesrion of one object to another
     *
     * @param object     source object
     * @param targetType class of target object
     * @param <E>        type of source object
     * @param <D>        type of target object
     * @return instance of the target object populated with data from the source object
     */
    public <E, D> D convertObjectTo(E object, Class<D> targetType) {
        return targetType.cast(modelMapper.map(object, targetType));
    }

    @Deprecated
    public UserDto convertToDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        userDto.setRoleDtos(user.getRoles().stream()
                .map(this::convertToDto).collect(Collectors.toSet()));
        return userDto;
    }

    @Deprecated
    public User convertToEntity(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setRoles(userDto.getRoleDtos().stream()
                .map(this::convertToEntity).collect(Collectors.toSet()));
        return user;
    }

    @Deprecated
    public RoleDto convertToDto(Role role) {
        RoleDto roleDto = modelMapper.map(role, RoleDto.class);
//        roleDto.setUserDtos(role.getUsers().stream()
//                .map(this::convertToDto).collect(Collectors.toSet()));
        roleDto.setPrivilegeDtos(role.getPrivileges().stream()
                .map(this::convertToDto).collect(Collectors.toSet()));
        return roleDto;
    }

    @Deprecated
    public Role convertToEntity(RoleDto roleDto) {
        Role role = modelMapper.map(roleDto, Role.class);
//        role.setUsers(roleDto.getUserDtos().stream()
//                .map(this::convertToEntity).collect(Collectors.toSet()));
        role.setPrivileges(roleDto.getPrivilegeDtos().stream()
                .map(this::convertToEntity).collect(Collectors.toSet()));
        return role;
    }

    @Deprecated
    public PrivilegeDto convertToDto(Privilege privilege) {
        PrivilegeDto privilegeDto = modelMapper.map(privilege, PrivilegeDto.class);
//        privilegeDto.setRoleDtos(privilege.getRoles().stream()
//                .map(this::convertToDto).collect(Collectors.toSet()));
        return privilegeDto;
    }

    @Deprecated
    public Privilege convertToEntity(PrivilegeDto privilegeDto) {
        Privilege privilege = modelMapper.map(privilegeDto, Privilege.class);
//        privilege.setRoles(privilegeDto.getRoleDtos().stream()
//                .map(this::convertToEntity).collect(Collectors.toSet()));
        return privilege;
    }


}

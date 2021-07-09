package com.example.command;

import com.example.core.dto.RoleDTO;
import com.example.core.dto.UserDTO;
import com.example.core.dto.UserImportDTO;
import com.example.core.wed.command.AbstractCommand;

import java.util.List;

public class UserCommand extends AbstractCommand<UserDTO> {

    public UserCommand() {
        this.pojo = new UserDTO();
    }

    private String confirmPassword;
    private List<RoleDTO> roles;
    private Integer roleId;
    private List<UserImportDTO> userImportDTOs;


    public List<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public List<UserImportDTO> getUserImportDTOs() {
        return userImportDTOs;
    }

    public void setUserImportDTOs(List<UserImportDTO> userImportDTOs) {
        this.userImportDTOs = userImportDTOs;
    }
}

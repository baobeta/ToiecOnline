package com.example.command;

import com.example.core.dto.RoleDTO;
import com.example.core.dto.UserDTO;
import com.example.core.wed.command.AbstractCommand;

import java.util.List;

public class UserCommand extends AbstractCommand<UserDTO> {
    public String getConfirmPassword() {
        return confirmPassword;
    }
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    public UserCommand() {
        this.pojo = new UserDTO();
    }
    private String confirmPassword;
    private List<RoleDTO> roles ;


    public List<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }
}

package com.example.command;

import com.example.core.dto.UserDTO;
import com.example.core.wed.command.AbstractCommand;

public class UserCommand extends AbstractCommand<UserDTO> {
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    private String confirmPassword;
    public UserCommand() {
        this.pojo = new UserDTO();
    }


}

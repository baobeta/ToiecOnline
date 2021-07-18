package com.example.command;

import com.example.core.dto.ExaminationDTO;
import com.example.core.wed.command.AbstractCommand;

public class ExaminationCommand extends AbstractCommand<ExaminationDTO> {
    public ExaminationCommand() {
        this.pojo = new ExaminationDTO();
    }
}
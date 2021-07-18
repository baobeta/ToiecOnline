package com.example.command;


import com.example.core.dto.ExaminationDTO;
import com.example.core.dto.ResultDTO;
import com.example.core.wed.command.AbstractCommand;

public class ResultCommand  extends AbstractCommand<ResultDTO> {
    public ResultCommand() {
        this.pojo = new ResultDTO();
    }
}


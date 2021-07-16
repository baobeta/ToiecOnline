package com.example.command;

import com.example.core.dto.ExerciseDTO;
import com.example.core.wed.command.AbstractCommand;

public class ExerciseCommand extends AbstractCommand<ExerciseDTO> {
    public ExerciseCommand() {
        this.pojo = new ExerciseDTO();
    }
}

package com.example.command;

import com.example.core.dto.ListenGuideLineDTO;
import com.example.core.wed.command.AbstractCommand;

public class ListenGuilineCommand  extends AbstractCommand<ListenGuideLineDTO> {
    public  ListenGuilineCommand() {
        this.pojo = new ListenGuideLineDTO();
    }
}

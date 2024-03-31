package com.example.movie.assembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChooseAssembler {
    @Autowired
    public ChooseCiemaAssembler chooseCinemaAssembler;

    @Autowired
    public ChooseRoomAssembler chooseRoomAssembler;

    @Autowired
    public ChooseScheduleAssembler chooseScheduleAssembler;

    @Autowired
    public ChooseFoodAssembler chooseFoodAssembler;
}

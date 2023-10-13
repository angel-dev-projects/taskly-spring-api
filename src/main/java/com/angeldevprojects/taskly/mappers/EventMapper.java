package com.angeldevprojects.taskly.mappers;

import com.angeldevprojects.taskly.dtos.EventDto;
import com.angeldevprojects.taskly.models.Event;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {
    Event toEvent(EventDto eventDto);
}

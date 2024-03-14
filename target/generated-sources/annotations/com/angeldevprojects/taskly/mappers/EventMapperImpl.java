package com.angeldevprojects.taskly.mappers;

import com.angeldevprojects.taskly.dtos.EventDto;
import com.angeldevprojects.taskly.models.Event;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-14T19:36:48+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class EventMapperImpl implements EventMapper {

    @Override
    public Event toEvent(EventDto eventDto) {
        if ( eventDto == null ) {
            return null;
        }

        Event.EventBuilder event = Event.builder();

        event.title( eventDto.title() );
        event.description( eventDto.description() );
        event.start_date( eventDto.start_date() );
        event.end_date( eventDto.end_date() );
        event.allDay( eventDto.allDay() );
        event.backgroundColor( eventDto.backgroundColor() );
        event.borderColor( eventDto.borderColor() );
        event.textColor( eventDto.textColor() );

        return event.build();
    }
}

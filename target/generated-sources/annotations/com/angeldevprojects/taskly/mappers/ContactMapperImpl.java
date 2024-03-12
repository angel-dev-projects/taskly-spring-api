package com.angeldevprojects.taskly.mappers;

import com.angeldevprojects.taskly.dtos.ContactDto;
import com.angeldevprojects.taskly.models.Contact;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-12T20:38:15+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class ContactMapperImpl implements ContactMapper {

    @Override
    public Contact toContact(ContactDto contactDto) {
        if ( contactDto == null ) {
            return null;
        }

        Contact.ContactBuilder contact = Contact.builder();

        contact.name( contactDto.name() );
        contact.surname( contactDto.surname() );
        contact.phoneNumber( contactDto.phoneNumber() );
        contact.email( contactDto.email() );

        return contact.build();
    }
}

package com.angeldevprojects.taskly.mappers;

import com.angeldevprojects.taskly.dtos.ContactDto;
import com.angeldevprojects.taskly.models.Contact;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContactMapper {
    Contact toContact(ContactDto contactDto);
}

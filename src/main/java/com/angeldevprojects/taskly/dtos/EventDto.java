package com.angeldevprojects.taskly.dtos;

public record EventDto(String title, String description, String start_date, String end_date, Boolean allDay,
                       String backgroundColor, String borderColor, String textColor) {
}
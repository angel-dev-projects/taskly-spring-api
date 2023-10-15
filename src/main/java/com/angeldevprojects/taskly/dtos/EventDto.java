package com.angeldevprojects.taskly.dtos;

public record EventDto(String title, String description, String start, String end, Boolean allDay,
                       String backgroundColor, String borderColor, String textColor) {
}
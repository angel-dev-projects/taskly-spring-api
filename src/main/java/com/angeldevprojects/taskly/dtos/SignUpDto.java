package com.angeldevprojects.taskly.dtos;

public record SignUpDto(String username, char[] password, String email, String name, String surname) {
}

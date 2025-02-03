package com.openclassrooms.ycyw.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MessageDTO {
    @NotBlank(message = "Content is mandatory")
    private String content;
}

package com.crud.tasks.domain;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class Task {
    
    private Long id;
    private String title;
    private String content;
}

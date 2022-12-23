package com.example.servlets.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Library {
    String id;
    String title;
    String author;
    String quantity;

}

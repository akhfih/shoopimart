package com.enigma.shopeymart.entity;

import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder(toBuilder = true)
public class FileStorage {
    private String fileName;
    private LocalDateTime dateTime;
}

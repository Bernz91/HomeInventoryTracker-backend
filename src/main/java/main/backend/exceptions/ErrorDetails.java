package main.backend.exceptions;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter @Setter
public class ErrorDetails {
    private LocalDateTime timestamp;
    private String message;
}


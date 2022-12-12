package com.brago.app.shoplistapp.exception;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class ErrorMessage {

    private String code;
    private String name;
    @NonNull
    private String message;
    private String path;
}

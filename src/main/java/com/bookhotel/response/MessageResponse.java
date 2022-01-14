package com.bookhotel.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse {
    private String message;
    private boolean success;

}

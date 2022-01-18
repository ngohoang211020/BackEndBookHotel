package com.bookhotel.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private final String type = "Bearer";
    private Integer id;
    private String username;
    private String name;
    private String phone;
    private String address;
    private String identification;
    private String email;
    private List<String> roles;

}

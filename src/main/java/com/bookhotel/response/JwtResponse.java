package com.bookhotel.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
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
    private String password;
    private List<String> roles;

    public JwtResponse(String token, Integer id, String username, String name, String phone, String address, String identification, String email,String password, List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.identification = identification;
        this.email = email;
        this.password=password;
        this.roles = roles;
    }

}

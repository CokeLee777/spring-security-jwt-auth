package io.github.cokelee777.springsecurityjwtauth.domain;

import io.github.cokelee777.springsecurityjwtauth.enums.UserRole;
import lombok.Getter;

import java.util.UUID;

@Getter
public class MemoryUser extends User {

    private String id;
    private String identifier;
    private String password;
    private String nickname;

    public MemoryUser(String identifier, String password, String nickname) {
        super();
        init(identifier, password, nickname);
    }

    public MemoryUser(String identifier, String password, String nickname, UserRole role) {
        super(role);
        init(identifier, password, nickname);
    }

    private void init(String identifier, String password, String nickname){
        this.id = createUUID();
        this.identifier = identifier;
        this.password = password;
        this.nickname = nickname;
    }

    private String createUUID(){
        return UUID.randomUUID().toString();
    }
}

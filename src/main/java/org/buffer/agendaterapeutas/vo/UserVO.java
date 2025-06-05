package org.buffer.agendaterapeutas.vo;

import org.buffer.agendaterapeutas.model.User;

public class UserVO {
    private Long id;
    private String username;
    private String name;
    private String email;

    public UserVO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getFirstName() + " " + user.getLastName();
        this.email = user.getEmail();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}

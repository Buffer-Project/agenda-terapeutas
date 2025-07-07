package org.buffer.agendaterapeutas.vo;

import org.buffer.agendaterapeutas.model.User;

public class UserVO {
    private final Long id;
    private final String username;
    private final String name;
    private final String email;

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

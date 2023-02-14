package me.zuif.teashop.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    ADMIN("Admin"),
    USER("User"),
    GUEST("Guest");
    private final String name;
}

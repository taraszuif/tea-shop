package me.zuif.teashop.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public enum Role {
    ADMIN("Admin"),
    USER("User"),
    GUEST("Guest");
    private final String name;

    public static List<String> getValues() {
        List<String> res = new ArrayList<>();
        for (Role role : values()) {
            res.add(role.getName());
        }
        return res;
    }
}

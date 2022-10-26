package me.zuif.teashop.utils;

import lombok.Data;

@Data
public class FindOptions {
    Object data;
    FindType type;

    public enum FindType {
        SEARCH, DEFAULT, TYPE
    }
}

package me.zuif.teashop.model.tea;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TeaType {
    BLACK("Black"),
    GREEN("Green"),
    WHITE("White"),
    OOLONG("Oolong");
    private String name;

}

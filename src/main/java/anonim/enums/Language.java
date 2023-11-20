package anonim.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Language {
    UZ("O'zbekcha"),
    RU("Русский"),
    EN("English");
    private final String code;
}

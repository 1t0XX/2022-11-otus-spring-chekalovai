package ru.otus.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class User {

    private final String name;

    private final String surName;
}

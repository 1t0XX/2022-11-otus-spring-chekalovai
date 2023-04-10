package ru.otus.service;

import java.util.function.Function;

public interface WrapperService {

    <InType, OutType> OutType convertInput(
            Class<InType> inType,
            String requestMessage,
            Function<InType, OutType> caller
    );
}
package ru.otus.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class WrapperServiceImpl implements WrapperService {

    private final ObjectMapper objectMapper;
    private final IOService ioService;

    private final MessageWriterService messageWriterService;

    @Override
    public <InType, OutType> OutType convertInput(Class<InType> inType, String requestMessage, Function<InType, OutType> caller) {
        try {
            messageWriterService.printLocalizedMessage(requestMessage);
            String inObjectString = ioService.readString();
            InType inObject = objectMapper.readValue(inObjectString, inType);
            return caller.apply(inObject);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

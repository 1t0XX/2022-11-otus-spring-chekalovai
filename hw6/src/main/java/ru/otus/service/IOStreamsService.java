package ru.otus.service;


import ru.otus.exception.service.InputReadingException;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class IOStreamsService implements IOService {

    private final PrintStream out;
    private final Scanner sc;

    public IOStreamsService(PrintStream out, InputStream in) {
        this.out = out;
        this.sc = new Scanner(in);
    }

    @Override
    public String readString() {
        try {
            return sc.nextLine();
        } catch (Exception e) {
            throw new InputReadingException(e);
        }
    }

    @Override
    public void writeString(String s) {
        out.println(s);
    }
}

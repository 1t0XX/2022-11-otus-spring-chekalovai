package ru.otus.service;

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
    public void out(String s) {
        out.println(s);
    }

    @Override
    public String readString() {
        return sc.nextLine();
    }

    @Override
    public String readLine(String text) {
        out(text);
        return sc.nextLine();
    }
}
package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import org.h2.tools.Console;

@ShellComponent
@RequiredArgsConstructor
public class H2ConsoleShellController {
    @ShellMethod(key = "h2-start")
    public String startH2Console() throws Exception{
        Console.main();
        return "Start h2 console";
    }
}

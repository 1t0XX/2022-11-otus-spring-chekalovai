package ru.otus.utils;

import lombok.extern.slf4j.Slf4j;
import ru.otus.model.Enrollee;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Slf4j
public class ExamUtils {

    private static final Random RANDOM = new Random();

    public static final List<Enrollee> enrollees = new ArrayList<>();

    public static List<Enrollee> getComeToTheExam() {
        IntStream.range(0, 15).mapToObj(index -> new Enrollee(("person " + (index + 1)), 0)).forEachOrdered(enrollees::add);
        return enrollees;
    }

    public static int calculateExamResult() {
        return RANDOM.nextInt(61);
    }

    public static boolean isPassedDiploma() {
        return RANDOM.nextBoolean();
    }

    public static void delay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.info(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
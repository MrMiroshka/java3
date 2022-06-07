package ru.miroshka.metods;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class WorkWithArrayTest {
    private static WorkWithArray workWithArray;

    @BeforeEach
    public void init() {
        workWithArray = new WorkWithArray();
    }

    private static Stream<Arguments> argsForThrowException() {
        return Stream.of(
                Arguments.of(new int[]{74, 2, 1, 2, 2, 3, 5, 1, 34}),
                Arguments.of(new int[]{34}),
                Arguments.of(new int[]{}),
                Arguments.of(new int[]{5, 5, 5, 5, 5, 5, 5, 5, 5})
        );
    }

    @ParameterizedTest
    @MethodSource("argsForThrowException")
    public void testTakeArrayAfter4ForExceptions(int[] in) {
        assertThrows(RuntimeException.class, () -> workWithArray.takeArrayAfter4(in));
        assertThrows(RuntimeException.class, () -> workWithArray.takeArrayAfter4(in));
        assertThrows(RuntimeException.class, () -> workWithArray.takeArrayAfter4(in));
        assertThrows(RuntimeException.class, () -> workWithArray.takeArrayAfter4(in));
    }


    @BeforeAll
    public static void Before() {
        workWithArray = new WorkWithArray();
    }

    private static Stream<Arguments> args() {
        return Stream.of(
                Arguments.of(new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7}, new int[]{1, 7}),
                Arguments.of(new int[]{4, 2, 1, 2, 2, 3, 5, 1, 7}, new int[]{2, 1, 2, 2, 3, 5, 1, 7}),
                Arguments.of(new int[]{74, 2, 1, 2, 2, 3, 5, 1, 4}, new int[]{})
        );
    }

    @ParameterizedTest
    @MethodSource("args")
    public void test(int[] in, int[] out) {
        Assertions.assertArrayEquals(out, workWithArray.takeArrayAfter4(in));
    }

    private static Stream<Arguments> argsForBoolMetodTrue() {
        return Stream.of(
                Arguments.of(new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7}),
                Arguments.of(new int[]{5, 2, 1, 2, 2, 3, 5, 1, 7}),
                Arguments.of(new int[]{74, 2, 5, 2, 2, 3, 5, 1, 5})
        );
    }

    @ParameterizedTest
    @MethodSource("argsForBoolMetodTrue")
    public void arrayContainTrue(int[] in) {
        assertTrue(workWithArray.arrayContain(in) == true);//, Matchers.is(true));
    }


    private static Stream<Arguments> argsForBoolMetodFalse() {
        return Stream.of(
                Arguments.of(new int[]{11, 2, 14, 14, 2, 3, 14, 11, 7}),
                Arguments.of(new int[]{5, 2, 12, 2, 2, 3, 5, 31, 7}),
                Arguments.of(new int[]{74, 2, 5, 2, 2, 3, 5, 12, 5}),
                Arguments.of(new int[]{})
        );
    }

    @ParameterizedTest
    @MethodSource("argsForBoolMetodFalse")
    public void arrayContainFalse(int[] in) {
        assertTrue(workWithArray.arrayContain(in) == false);//, Matchers.is(true));
    }

}
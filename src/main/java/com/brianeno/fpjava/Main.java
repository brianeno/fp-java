package com.brianeno.fpjava;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class Main {

    public void imperative() {
        System.out.println("//" + "START:START_IMPERATIVE_FIND");
        List<ChargeSession> over400 = new ArrayList<>();
        for (ChargeSession cs : ChargeSession.SESSIONS) {
            if (cs.getWatts() > 400) {
                over400.add(cs);
            }
        }
        System.out.println(over400);
        System.out.println("//" + "END:START_IMPERATIVE_FIND");

        System.out.println("//" + "START:START_IMPERATIVE_SKIP2");
        int i = 0;
        List<ChargeSession> skip2 = new ArrayList<>();
        for (ChargeSession cs : ChargeSession.SESSIONS) {
            i++;
            if (i > 2) {
                skip2.add(cs);
            }
        }
        System.out.println(skip2);
        System.out.println("//" + "END:START_IMPERATIVE_SKIP2");

        System.out.println("//" + "START:START_IMPERATIVE_JOINING");
        i = 0;
        StringBuilder bldr = new StringBuilder();
        for (ChargeSession cs : ChargeSession.SESSIONS) {
            if (i > 0) {
                bldr.append(", ");
            }
            bldr.append(cs.getMake()).append(" ").append(cs.getModel());
            i++;
        }
        System.out.println(bldr);
        System.out.println("//" + "END:START_IMPERATIVE_JOINING");
    }

    public void streams() {
        System.out.println("//" + "START:SIMPLE_STREAM_CREATE");
        Stream<Integer> numsFromVals = Stream.of(1, 2, 3, 4);
        numsFromVals.forEach(System.out::println);
        int[] numbers = {5, 6, 7, 8};
        IntStream numsFromArray = Arrays.stream(numbers);
        numsFromArray.forEach(System.out::println);
        System.out.println("//" + "END:SIMPLE_STREAM_CREATE");
    }

    public void stringIterators() {
        final String str = "functional";
        System.out.println("//" + "START:STRING_ITER");
        str.chars().forEach(System.out::println);
        System.out.println("//" + "END:STRING_ITER");
        System.out.println("//" + "START:STRING_ITER_2");
        str.chars().mapToObj(ch -> (char) ch).forEach(System.out::println);
        System.out.println("//" + "END:STRING_ITER_2");
    }

    public void print() {
        System.out.println("//" + "START:SIMPLE_PRINT");
        ChargeSession.SESSIONS.forEach(System.out::println);
        System.out.println("//" + "END:SIMPLE_PRINT");

        System.out.println("//" + "START:PRINT_NUM_ARRAY");
        List<Integer> numbers
            = Arrays.asList(11, 22, 33, 44,
            55, 66, 77, 88,
            99, 100);
        numbers.forEach(number
            -> System.out.print(
            number + " "));
        System.out.println("//" + "END:PRINT_NUM_ARRAY");
    }

    public void runCollectionTests() {
        System.out.println("//" + "START:COLLECT_TO_LIST_OUTPUT");
        List<ChargeSession> greaterThan350 =
            ChargeSession.SESSIONS.stream()
                .filter(cs -> cs.getWatts() > 350)
                .toList();
        System.out.println("CS greater than 350: " + greaterThan350);
        System.out.println("//" + "END:COLLECT_TO_LIST_OUTPUT");

        System.out.println("//" + "START:GROUP_BY_WATT_HUNDREDS_OUTPUT");
        Map<Integer, List<String>> csById =
            ChargeSession.SESSIONS.stream()
                .collect(
                    groupingBy(ChargeSession::getHundredValue, mapping(ChargeSession::toString, toList())));
        System.out.println("CS Grouped by ID: " + csById);
        System.out.println("//" + "END:GROUP_BY_WATT_HUNDREDS_OUTPUT");

        System.out.println("//" + "START:CONVERT_TO_MAP");
        Map<String, String> modelById =
            ChargeSession.SESSIONS.stream()
                .collect(
                    toMap(ChargeSession::getId, ChargeSession::getModel));
        System.out.println("To Map: " + modelById);
        System.out.println("//" + "END:CONVERT_TO_MAP");

        System.out.println("//" + "START:CONVERT_TO_MAP_OBJ");
        Map<String, ChargeSession> modelByObject =
            ChargeSession.SESSIONS.stream()
                .collect(
                    toMap(ChargeSession::getId, cs -> cs));
        System.out.println("To Map: " + modelByObject);
        System.out.println("//" + "END:CONVERT_TO_MAP_OBJ");
    }

    public void iterate() {
        System.out.println("//" + "START:SIMPLE_ITERATION_FIND_FIRST");
        Optional<ChargeSession> csOpt = ChargeSession.SESSIONS.stream()
            .filter(cs -> cs.getId().startsWith("1111"))
            .findFirst();
        csOpt.ifPresent(System.out::println);
        System.out.println("//" + "END:SIMPLE_ITERATION_FIND_FIRST");

        System.out.println("//" + "START:SIMPLE_ITERATION_FIND_FIRST_2");
        csOpt = ChargeSession.SESSIONS.stream()
            .filter(cs -> cs.getHundredValue() == 3)
            .findFirst();
        csOpt.ifPresent(System.out::println);
        System.out.println("//" + "END:SIMPLE_ITERATION_FIND_FIRST_2");


        System.out.println("//" + "START:SIMPLE_ITERATION_FIND_ANY");
        csOpt = ChargeSession.SESSIONS.stream()
            .filter(cs -> cs.getId().startsWith("1111"))
            .findAny();
        csOpt.ifPresent(System.out::println);
        System.out.println("//" + "END:SIMPLE_ITERATION_FIND_ANY");

        //Returns, if this stream is ordered, a stream consisting of the remaining elements of
        //this stream after dropping the longest prefix of elements that match the given predicate.
        System.out.println("//" + "START:ITERATION_DROPWHILE");
        ChargeSession.SESSIONS.stream()
            .dropWhile(cs -> cs.getId().startsWith("1"))
            .forEach(System.out::println);
        System.out.println("//" + "END:ITERATION_DROPWHILE");

        System.out.println("//" + "START:ITERATION_TAKEWHILE");
        ChargeSession.SESSIONS.stream()
            .takeWhile(cs -> cs.getWatts() > 400)
            .forEach(System.out::println);
        System.out.println("//" + "END:ITERATION_TAKEWHILE");

        System.out.println("//" + "START:ITERATION_TAKEWHILE_SUM");
        long sum = ChargeSession.SESSIONS.stream()
            .takeWhile(cs -> cs.getWatts() > 400)
            .count();
        System.out.println(sum);
        System.out.println("//" + "END:ITERATION_TAKEWHILE_SUM");

        System.out.println("//" + "START:ITERATION_SKIP");
        ChargeSession.SESSIONS.stream()
            .skip(2)
            .forEach(System.out::println);
        System.out.println("//" + "END:ITERATION_SKIP");
    }

    public void transform() {

        System.out.println("//" + "START:COLLECTION_TRANFORM");
        ChargeSession.SESSIONS.stream().map(cs -> cs.getMake().toLowerCase()).forEach(System.out::println);
        System.out.println("//" + "END:COLLECTION_TRANFORM");

        System.out.println("//" + "START:COLLECTION_COUNT");
        ChargeSession.SESSIONS.stream().map(cs -> cs.getMake().length()).forEach(System.out::println);
        System.out.println("//" + "END:COLLECTION_COUNT");

        System.out.println("//" + "START:MAP_TO_INT");
        System.out.println("Total make chars:" + ChargeSession.SESSIONS.stream().mapToInt(cs -> cs.getMake().length()).sum());
        System.out.println("//" + "END:MAP_TO_INT");
    }

    public void comparator() {
        System.out.println("//" + "START:SORT_WATTS");
        List<ChargeSession> sortedList =
            ChargeSession.SESSIONS.stream()
                .sorted(ChargeSession::wattsDifference)
                .toList();
        System.out.println(sortedList);
        System.out.println("//" + "END:SORT_WATTS");

        System.out.println("//" + "START:SORT_WATTS_DESC");
        sortedList =
            ChargeSession.SESSIONS.stream()
                .sorted((c1, c2) -> c2.wattsDifference(c1))
                .toList();
        System.out.println(sortedList);
        System.out.println("//" + "END:SORT_WATTS_DESC");

        System.out.println("//" + "START:SMALLEST_CHARGE");
        ChargeSession.SESSIONS.stream()
            .min(ChargeSession::wattsDifference)
            .ifPresent(System.out::println);
        System.out.println("//" + "END:SMALLEST_CHARGE");

        System.out.println("//" + "START:LARGEST_CHARGE");
        ChargeSession.SESSIONS.stream()
            .max(ChargeSession::wattsDifference)
            .ifPresent(System.out::println);
        System.out.println("//" + "END:LARGEST_CHARGE");

        System.out.println("//" + "START:COMPARE_MULTI_FIELD");
        Comparator<ChargeSession> compareByName = Comparator
            .comparing(ChargeSession::getMake)
            .thenComparing(ChargeSession::getModel)
            .thenComparing(ChargeSession::getWatts);
        sortedList =
            ChargeSession.SESSIONS.stream()
                .sorted(compareByName)
                .toList();
        System.out.println(sortedList);
        System.out.println("//" + "END:COMPARE_MULTI_FIELD");
    }

    public void runPredicateTests() {
        System.out.println("//" + "START:PREDICATE_TEST");
        Predicate<ChargeSession> wattsOver400 = cs -> cs.getWatts() > 400;
        List<ChargeSession> over400 = ChargeSession.SESSIONS.stream()
            .filter(wattsOver400)
            .toList();
        System.out.println(over400);
        System.out.println("//" + "END:PREDICATE_TEST");

        System.out.println("//" + "START:ANY_MATCH");
        boolean bAnyMatch = ChargeSession.SESSIONS.stream()
            .anyMatch(wattsOver400);
        System.out.println(bAnyMatch);
        System.out.println("//" + "END:ANY_MATCH");

        System.out.println("//" + "START:ALL_MATCH");
        boolean bAllMatch = ChargeSession.SESSIONS.stream()
            .allMatch(wattsOver400);
        System.out.println(bAllMatch);
        System.out.println("//" + "END:ALL_MATCH");

        System.out.println("//" + "START:PREDICATE_CHAIN");
        Predicate<ChargeSession> wattsOver400v2 = cs -> cs.getWatts() > 400;
        Predicate<ChargeSession> makeStartsWithT = cs -> cs.getMake().startsWith("T");
        Predicate<ChargeSession> makeStartsWithF = cs -> cs.getMake().startsWith("F");
        Predicate<ChargeSession> complexPred = wattsOver400v2.and(makeStartsWithT).or(makeStartsWithF);
        List<ChargeSession> complexResult = ChargeSession.SESSIONS.stream()
            .filter(complexPred)
            .toList();
        System.out.println(complexResult);
        System.out.println("//" + "END:PREDICATE_CHAIN");
    }

    public void grouping() {
        System.out.println("//" + "START:GROUP_MODEL");
        Map<String, List<ChargeSession>> makeModelObjs =
            ChargeSession.SESSIONS.stream()
                .collect(Collectors.groupingBy(ChargeSession::getMake));
        System.out.println("Grouped by model: " + makeModelObjs);
        System.out.println("//" + "END:GROUP_MODEL");

        System.out.println("//" + "START:GROUP_MODEL_LIST");
        Map<String, List<String>> makeModel =
            ChargeSession.SESSIONS.stream()
                .collect(groupingBy(ChargeSession::getMake, mapping(ChargeSession::getModel, toList())));
        System.out.println("Grouped by model: " + makeModel);
        System.out.println("//" + "END:GROUP_MODEL_LIST");
    }

    public void statistics() {
        System.out.println("//" + "START:AVG_WATTS");
        double avg = ChargeSession.SESSIONS.stream()
            .mapToInt(ChargeSession::getWatts)
            .average()
            .orElse(0);
        System.out.println("Average watts: " + avg);
        System.out.println("//" + "END:AVG_WATTS");
    }

    public void joining() {
        System.out.println("//" + "START:MAKE_MODEL_JOINING");
        String makeModels = ChargeSession.SESSIONS.stream()
            .map(cs -> cs.getMake() + " " + cs.getModel())
            .collect(Collectors.joining(","));
        System.out.println(makeModels);
        System.out.println("//" + "END:MAKE_MODEL_JOINING");

        System.out.println("//" + "START:MAP_MODELS_LENGTH");
        Map<Integer, List<String>> mapModels = ChargeSession.SESSIONS.stream()
            .map(ChargeSession::getModel)
            .collect(Collectors.groupingBy(String::length));
        System.out.println(mapModels);
        System.out.println("//" + "END:MAP_MODELS_LENGTH");
    }

    public static void main(String[] args) {
        Main main = new Main();

        main.imperative();
        main.streams();
        main.print();
        main.stringIterators();
        main.runCollectionTests();
        main.iterate();
        main.transform();
        main.comparator();

        main.runPredicateTests();
        main.grouping();
        main.statistics();
        main.joining();
    }
}

package by.clevertec;

import by.clevertec.model.House;
import by.clevertec.model.Student;
import by.clevertec.model.Animal;
import by.clevertec.model.Person;
import by.clevertec.model.Car;
import by.clevertec.model.Flower;
import by.clevertec.model.Examination;
import by.clevertec.util.Util;

import java.time.LocalDate;
import java.time.Period;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        task1();
        task2();
        task3();
        task4();
        task5();
        task6();
        task7();
        task8();
        task9();
        task10();
        task11();
        task12();
        task13();
        task14();
        task15();
        task16();
        task17();
        task18();
        task19();
        task20();
        task21();
        task22();
    }

    public static void task1() {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(a -> a.getAge() >= 10 && a.getAge() < 20)
                .sorted(Comparator.comparingInt(Animal::getAge))
                .skip(14)
                .limit(7)
                .forEach(System.out::println);
    }

    public static void task2() {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(a -> "Japanese".equalsIgnoreCase(a.getOrigin()))
                .peek(a -> {
                    if ("Female".equalsIgnoreCase(a.getGender())) a.setBread(a.getBread().toUpperCase());
                })
                .map(Animal::getBread)
                .forEach(System.out::println);
    }

    public static void task3() {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(a -> a.getAge() > 30)
                .map(Animal::getOrigin)
                .distinct()
                .filter(a -> a.startsWith("A"))
                .forEach(System.out::println);
    }

    public static void task4() {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .filter(a -> "Female".equalsIgnoreCase(a.getGender()))
                .count()
        );
    }

    public static void task5() {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .filter(a -> a.getAge() >= 20 && a.getAge() <= 30)
                .anyMatch(a -> "Hungarian".equalsIgnoreCase(a.getOrigin()))
        );
    }

    public static void task6() {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .allMatch(a -> "Male".equalsIgnoreCase(a.getGender()) || "Female".equalsIgnoreCase(a.getGender()))
        );
    }

    public static void task7() {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .noneMatch(a -> "Oceania".equalsIgnoreCase(a.getOrigin()))
        );
    }

    public static void task8() {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .sorted(Comparator.comparing(Animal::getBread))
                .limit(100)
                .map(Animal::getAge)
                .max(Integer::compare)
                .orElse(-1)
        );
    }

    public static void task9() {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .map(Animal::getBread)
                .map(String::toCharArray)
                .mapToInt(a -> a.length)
                .min()
        );
    }

    public static void task10() {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .map(Animal::getAge)
                .reduce(Integer::sum)
                .orElse(-1)
        );
    }

    public static void task11() {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .filter(a -> "Indonesia".equalsIgnoreCase(a.getOrigin()))
                .mapToInt(Animal::getAge)
                .average()
                .orElse(0.0)
        );
    }

    public static void task12() {
        List<Person> persons = Util.getPersons();
        persons.stream()
                .filter(p -> "Male".equalsIgnoreCase(p.getGender()))
                .filter(person -> getPeriod(person) >= 18 && getPeriod(person) <= 27)
                .sorted(Comparator.comparingInt(Person::getRecruitmentGroup))
                .limit(200)
                .forEach(System.out::println);
    }

    public static void task13() {
        List<House> houses = Util.getHouses();
        Predicate<House> hospitals = house -> "Hospital".equalsIgnoreCase(house.getBuildingType());
        Stream<Person> peopleFilter = houses.stream()
                .filter(hospitals.negate())
                .flatMap(h -> h.getPersonList()
                        .stream()
                        .sorted(Comparator.comparingInt((Person p) -> getPeriod(p) < 18 || getPeriod(p) > 65 ? 0 : 1).thenComparing(Main::getPeriod))
                );
        Stream.concat(houses.stream().filter(hospitals).flatMap(h -> h.getPersonList().stream()), peopleFilter)
                .limit(500)
                .forEach(System.out::println);
    }

    public static void task14() {
        List<Car> cars = Util.getCars();

        Predicate<Car> firstTrain = c -> "Jaguar".equalsIgnoreCase(c.getCarMake()) || "white".equalsIgnoreCase(c.getColor());
        Predicate<Car> secondTrain = c -> c.getMass() < 1500 || Arrays.asList(new String[]{"bmw", "lexus", "chrysler", "toyota"}).contains(c.getCarMake().toLowerCase());
        Predicate<Car> thirdTrain = c -> ("Black".equalsIgnoreCase(c.getColor()) && c.getMass() > 4000)
                || "GMC".equalsIgnoreCase(c.getCarMake())
                || "Dodge".equalsIgnoreCase(c.getCarMake());
        Predicate<Car> fourthTrain = c -> c.getReleaseYear() < 1982
                || "Civic".equalsIgnoreCase(c.getCarMake())
                || "Cherokee".equalsIgnoreCase(c.getCarMake());
        Predicate<Car> fifthTrain = c -> !(Arrays.asList(new String[]{"yellow", "red", "green", "blue"}).contains(c.getColor().toLowerCase()))
                || c.getPrice() > 40000;
        Predicate<Car> sixthTrain = c -> c.getVin().contains("59");

        List<Car> first = cars.stream().filter(firstTrain).collect(Collectors.toList());
        List<Car> second = cars.stream().filter(firstTrain.negate().and(secondTrain)).collect(Collectors.toList());
        List<Car> third = cars.stream().filter(firstTrain.negate().and(secondTrain.negate()).and(thirdTrain)).collect(Collectors.toList());
        List<Car> fourth = cars.stream().filter(firstTrain.negate().and(secondTrain.negate()).and(thirdTrain.negate()).and(fourthTrain)).collect(Collectors.toList());
        List<Car> fifth = cars.stream().filter(firstTrain.negate().and(secondTrain.negate()).and(thirdTrain.negate()).and(fourthTrain.negate()).and(fourthTrain)).collect(Collectors.toList());
        List<Car> sixth = cars.stream().filter(firstTrain.negate().and(secondTrain.negate()).and(thirdTrain.negate()).and(fourthTrain.negate()).and(fifthTrain.negate()).and(sixthTrain)).collect(Collectors.toList());

        ToDoubleFunction<Car> function = c -> c.getMass() * 0.00174;
        Map<String, Double> result = new LinkedHashMap<>();
        result.put("Туркменистан", first.stream().mapToDouble(function).reduce((Double::sum)).getAsDouble());
        result.put("Узбекистан", second.stream().mapToDouble(function).reduce(Double::sum).getAsDouble());
        result.put("Казахстан", third.stream().mapToDouble(function).reduce(Double::sum).getAsDouble());
        result.put("Кыргызстан", fourth.stream().mapToDouble(function).reduce(Double::sum).getAsDouble());
        result.put("Россия", fifth.stream().mapToDouble(function).reduce(Double::sum).getAsDouble());
        result.put("Монголия", sixth.stream().mapToDouble(function).reduce(Double::sum).getAsDouble());
        result.forEach((key, value) -> System.out.printf("%s : $%.2f\n", key, value));

        System.out.println(
                Stream.of(
                        first.stream().mapToInt(Car::getPrice).reduce(Integer::sum).getAsInt(),
                        second.stream().mapToInt(Car::getPrice).reduce(Integer::sum).getAsInt(),
                        third.stream().mapToInt(Car::getPrice).reduce(Integer::sum).getAsInt(),
                        fourth.stream().mapToInt(Car::getPrice).reduce(Integer::sum).getAsInt(),
                        fifth.stream().mapToInt(Car::getPrice).reduce(Integer::sum).getAsInt(),
                        sixth.stream().mapToInt(Car::getPrice).reduce(Integer::sum).getAsInt()
                ).reduce(Integer::sum).get()
        );
    }

    public static void task15() {
        List<Flower> flowers = Util.getFlowers();
        Comparator<Flower> comparator = Comparator.comparing(Flower::getOrigin).reversed()
                .thenComparing(Flower::getPrice)
                .thenComparing((f1, f2) -> Double.compare(f2.getWaterConsumptionPerDay(), f1.getWaterConsumptionPerDay())).reversed();
        Predicate<Flower> inputCharFilter = f -> Pattern.matches("[C-S]", Character.toString(f.getCommonName().charAt(0)));
        Predicate<Flower> flowerVaseMaterialFilter = f -> f.isShadePreferred() && (
                f.getFlowerVaseMaterial().contains("Aluminum")
                        || f.getFlowerVaseMaterial().contains("Glass")
                        || f.getFlowerVaseMaterial().contains("Steel")
        );
        System.out.printf("%.3f", flowers
                .stream()
                .sorted(comparator)
                .filter(inputCharFilter)
                .filter(flowerVaseMaterialFilter)
                .mapToDouble(f -> f.getPrice() + f.getWaterConsumptionPerDay() * 1825 / 1000 * 1.39)    // 1825 - five years to days  // 1000 - 1 liter to meter cubed
                .reduce(Double::sum)
                .getAsDouble()
        );
    }

    public static void task16() {
        List<Student> students = Util.getStudents();
        students.stream()
                .filter(student -> student.getAge() < 20)
                .sorted((s1, s2) -> s1.getSurname().compareToIgnoreCase(s2.getSurname()))
                .forEach(s -> System.out.printf("\n%s - %d лет", s.getSurname(), s.getAge()));
    }

    public static void task17() {
        List<Student> students = Util.getStudents();
        students.stream()
                .map(Student::getGroup)
                .distinct()
                .forEach(System.out::println);
    }

    public static void task18() {
        List<Student> students = Util.getStudents();
        students.stream().collect(Collectors.groupingBy(
                        Student::getFaculty,
                        Collectors.averagingInt(Student::getAge)))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
    }

    public static void task19() {
        List<Student> students = Util.getStudents();
        List<Examination> examinations = Util.getExaminations();
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter group:");
            String group = scanner.nextLine();
            students.stream()
                    .filter(student -> student.getGroup().equals(group))
                    .filter(student -> examinations.stream()
                            .filter(exam -> exam.getStudentId() == student.getId())
                            .anyMatch(exam -> exam.getExam3() > 4)
                    )
                    .forEach(System.out::println);
        }
    }

    public static void task20() {
        List<Student> students = Util.getStudents();
        List<Examination> examinations = Util.getExaminations();
        Optional<Map.Entry<String, Double>> maxEntry =
                students.stream()
                        .collect(Collectors.groupingBy(Student::getFaculty,
                                Collectors.averagingInt(student -> examinations.stream()
                                        .filter(exam -> exam.getStudentId() == student.getId())
                                        .map(Examination::getExam1)
                                        .findFirst()
                                        .orElse(0))))
                        .entrySet()
                        .stream()
                        .max(Map.Entry.comparingByValue());
        maxEntry.ifPresent(entry -> System.out.printf("%s : Средняя оценка по первому экзамену: %f", entry.getKey(), entry.getValue()));
    }

    public static void task21() {
        List<Student> students = Util.getStudents();
        students.stream()
                .collect(
                        Collectors.groupingBy(
                                Student::getGroup,
                                Collectors.counting()
                        )
                )
                .entrySet()
                .forEach(System.out::println);
    }

    public static void task22() {
        List<Student> students = Util.getStudents();
        students.stream().collect(Collectors.toMap(
                        Student::getFaculty,
                        student -> students.stream()
                                .filter(s -> s.getFaculty().equals(student.getFaculty()))
                                .min(Comparator.comparingInt(Student::getAge))
                                .orElse(new Student())
                                .getAge(),
                        Math::min
                ))
                .forEach((key, val) -> System.out.printf("%s - min age: %d \n", key, val));
    }

    private static int getPeriod(Person person) {
        return Period.between(person.getDateOfBirth(), LocalDate.now()).getYears();
    }
}

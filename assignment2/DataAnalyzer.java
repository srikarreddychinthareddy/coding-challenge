package assignment2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class DataAnalyzer {


    public static List<Employee> readEmployees(String filePath) throws IOException {
        List<Employee> employees = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String header = reader.readLine(); // skip header
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length < 8) {
                    continue;
                }
                String firstName = parts[0].trim();
                String gender = parts[1].trim();
                String startDate = parts[2].trim();
                String lastLogin = parts[3].trim();
                double salary = 0.0;
                try {
                    String salaryStr = parts[4].replace(",", "").trim();
                    salary = salaryStr.isEmpty() ? 0.0 : Double.parseDouble(salaryStr);
                } catch (NumberFormatException e) {
                    salary = 0.0;
                }
                double bonus = 0.0;
                try {
                    String bonusStr = parts[5].replace(",", "").trim();
                    bonus = bonusStr.isEmpty() ? 0.0 : Double.parseDouble(bonusStr);
                } catch (NumberFormatException e) {
                    bonus = 0.0;
                }
                boolean senior = parts[6].trim().equalsIgnoreCase("true");
                String team = parts[7].trim();
                employees.add(new Employee(firstName, gender, startDate, lastLogin, salary, bonus, senior, team));
            }
        }
        return employees;
    }


    public static Map<String, Long> countByTeam(List<Employee> employees) {
        return employees.stream()
                .collect(Collectors.groupingBy(e -> e.team == null || e.team.isEmpty() ? "(No Team)" : e.team,
                        Collectors.counting()));
    }


    public static Map<String, Double> averageSalaryByGender(List<Employee> employees) {
        Map<String, Double> averages = new LinkedHashMap<>();
        Map<String, List<Employee>> grouped = employees.stream()
                .collect(Collectors.groupingBy(e -> e.gender == null || e.gender.isEmpty() ? "Unknown" : e.gender));
        for (Map.Entry<String, List<Employee>> entry : grouped.entrySet()) {
            double sum = entry.getValue().stream().mapToDouble(e -> e.salary).sum();
            long count = entry.getValue().size();
            averages.put(entry.getKey(), count > 0 ? sum / count : 0.0);
        }
        return averages;
    }


    public static Map<String, Double> averageSalaryByTeam(List<Employee> employees) {
        Map<String, Double> averages = new LinkedHashMap<>();
        Map<String, List<Employee>> grouped = employees.stream()
                .collect(Collectors.groupingBy(e -> e.team == null || e.team.isEmpty() ? "(No Team)" : e.team));
        for (Map.Entry<String, List<Employee>> entry : grouped.entrySet()) {
            double sum = entry.getValue().stream().mapToDouble(e -> e.salary).sum();
            long count = entry.getValue().size();
            averages.put(entry.getKey(), count > 0 ? sum / count : 0.0);
        }
        return averages;
    }


    public static List<Map.Entry<String, Double>> topNSalaries(List<Employee> employees, int n) {
        return employees.stream()
                .sorted(Comparator.comparingDouble((Employee e) -> e.salary).reversed())
                .limit(n)
                .map(e -> Map.entry(e.firstName, e.salary))
                .collect(Collectors.toList());
    }


    public static Map<String, Double> averageBonusByTeam(List<Employee> employees) {
        Map<String, Double> averages = new LinkedHashMap<>();
        Map<String, List<Employee>> grouped = employees.stream()
                .collect(Collectors.groupingBy(e -> e.team == null || e.team.isEmpty() ? "(No Team)" : e.team));
        for (Map.Entry<String, List<Employee>> entry : grouped.entrySet()) {
            double sum = entry.getValue().stream().mapToDouble(e -> e.bonusPercent).sum();
            long count = entry.getValue().size();
            averages.put(entry.getKey(), count > 0 ? sum / count : 0.0);
        }
        return averages;
    }


    public static void printAnalysis(List<Employee> employees) {
        Map<String, Long> counts = countByTeam(employees);
        System.out.println("Count of employees by team:");
        counts.entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .forEach(e -> System.out.println("  " + e.getKey() + ": " + e.getValue()));

        System.out.println("\nAverage salary by gender:");
        averageSalaryByGender(employees).entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> System.out.printf("  %s: $%,.2f%n", e.getKey(), e.getValue()));

        System.out.println("\nAverage salary by team:");
        averageSalaryByTeam(employees).entrySet().stream()
                .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                .forEach(e -> System.out.printf("  %s: $%,.2f%n", e.getKey(), e.getValue()));

        System.out.println("\nTop 5 employees by salary:");
        topNSalaries(employees, 5).forEach(e ->
                System.out.printf("  %s: $%,.2f%n", e.getKey(), e.getValue()));

        System.out.println("\nAverage bonus percentage by team:");
        averageBonusByTeam(employees).entrySet().stream()
                .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                .forEach(e -> System.out.printf("  %s: %.3f%%%n", e.getKey(), e.getValue()));
    }
}
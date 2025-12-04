package assignment2;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class DataAnalysisDemo {
    public static void main(String[] args) {
        try {
            Path path = Paths.get("assignment2", "employees.csv");
            List<Employee> employees = DataAnalyzer.readEmployees(path.toString());
            DataAnalyzer.printAnalysis(employees);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
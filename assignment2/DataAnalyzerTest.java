package assignment2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class DataAnalyzerTest {

    private final List<Employee> sampleEmployees = Arrays.asList(
            new Employee("Alice", "Female", "2020-01-01", "09:00", 100_000, 10.0, false, "Engineering"),
            new Employee("Bob", "Male", "2019-06-15", "10:30", 80_000, 12.5, true, "Engineering"),
            new Employee("Carol", "Female", "2018-03-20", "11:00", 120_000, 15.0, false, "Sales"),
            new Employee("Dave", "Male", "2017-07-10", "08:45", 90_000, 8.0, false, "Sales"),
            new Employee("Eve", "Female", "2016-11-05", "13:20", 70_000, 5.0, false, "")
    );

    @Test
    public void testCountByTeam() {
        Map<String, Long> counts = DataAnalyzer.countByTeam(sampleEmployees);
        Assertions.assertEquals(2L, counts.get("Engineering"));
        Assertions.assertEquals(2L, counts.get("Sales"));
        Assertions.assertEquals(1L, counts.get("(No Team)"));
    }

    @Test
    public void testAverageSalaryByGender() {
        Map<String, Double> averages = DataAnalyzer.averageSalaryByGender(sampleEmployees);
        double femaleAvg = (100_000 + 120_000 + 70_000) / 3.0;
        double maleAvg = (80_000 + 90_000) / 2.0;
        Assertions.assertEquals(femaleAvg, averages.get("Female"), 1e-2);
        Assertions.assertEquals(maleAvg, averages.get("Male"), 1e-2);
    }

    @Test
    public void testAverageSalaryByTeam() {
        Map<String, Double> averages = DataAnalyzer.averageSalaryByTeam(sampleEmployees);
        Assertions.assertEquals((100_000 + 80_000) / 2.0, averages.get("Engineering"), 1e-2);
        Assertions.assertEquals((120_000 + 90_000) / 2.0, averages.get("Sales"), 1e-2);
        Assertions.assertEquals(70_000.0, averages.get("(No Team)"), 1e-2);
    }

    @Test
    public void testTopNSalaries() {
        List<Map.Entry<String, Double>> top = DataAnalyzer.topNSalaries(sampleEmployees, 3);
        Assertions.assertEquals("Carol", top.get(0).getKey());
        Assertions.assertEquals(120_000, top.get(0).getValue());
        Assertions.assertEquals("Alice", top.get(1).getKey());
        Assertions.assertEquals(100_000, top.get(1).getValue());
        Assertions.assertEquals("Dave", top.get(2).getKey());
        Assertions.assertEquals(90_000, top.get(2).getValue());
    }

    @Test
    public void testAverageBonusByTeam() {
        Map<String, Double> averages = DataAnalyzer.averageBonusByTeam(sampleEmployees);
        Assertions.assertEquals((10.0 + 12.5) / 2.0, averages.get("Engineering"), 1e-2);
        Assertions.assertEquals((15.0 + 8.0) / 2.0, averages.get("Sales"), 1e-2);
        Assertions.assertEquals(5.0, averages.get("(No Team)"), 1e-2);
    }

    @Test
    public void testReadEmployees() throws IOException {
        // Write a temporary CSV file
        String header = "First Name,Gender,Start Date,Last Login Time,Salary,Bonus %,Senior Management,Team\n";
        String row1 = "Alice,Female,2020-01-01,09:00,100000,10.0,false,Engineering\n";
        String row2 = "Bob,Male,2019-06-15,10:30,80000,12.5,true,Engineering\n";
        Path temp = Files.createTempFile("employees", ".csv");
        Files.write(temp, (header + row1 + row2).getBytes());
        List<Employee> list = DataAnalyzer.readEmployees(temp.toString());
        Assertions.assertEquals(2, list.size());
        Assertions.assertEquals("Alice", list.get(0).firstName);
        Assertions.assertTrue(list.get(1).seniorManagement);
        Files.deleteIfExists(temp);
    }
}
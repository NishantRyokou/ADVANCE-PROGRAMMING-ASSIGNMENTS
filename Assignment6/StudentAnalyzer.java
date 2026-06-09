import java.util.*;
import java.util.stream.*;

class Student {
    private final int id;
    private final String name;
    private final List<String> courses;
    private final Map<String, Integer> scores;

    public Student(int id, String name, List<String> courses, Map<String, Integer> scores) {
        this.id = id;
        this.name = name;
        this.courses = courses;
        this.scores = scores;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public List<String> getCourses() { return courses; }
    public Map<String, Integer> getScores() { return scores; }

    public double getAverageScore() {
        if (courses.isEmpty()) return 0.0;
        return courses.stream()
            .mapToInt(course -> scores.getOrDefault(course, 0))
            .average()
            .orElse(0.0);
    }

    @Override
    public String toString() {
        return String.format("Student[ID=%d, Name=%s, Avg=%.2f]", id, name, getAverageScore());
    }
}

public class StudentAnalyzer {
    public List<Student> getTopNStudents(List<Student> students, int n) {
        return students.stream()
            .sorted(Comparator.comparingDouble(Student::getAverageScore).reversed())
            .limit(n)
            .collect(Collectors.toList());
    }

    public Map<String, Double> getAverageScorePerCourse(List<Student> students) {
        return students.stream()
            .flatMap(student -> student.getCourses().stream()
                .map(course -> new AbstractMap.SimpleEntry<>(course, student.getScores().getOrDefault(course, 0))))
            .collect(Collectors.groupingBy(
                Map.Entry::getKey,
                Collectors.averagingDouble(Map.Entry::getValue)
            ));
    }

    public Set<String> getAllUniqueCourses(List<Student> students) {
        return students.stream()
            .flatMap(student -> student.getCourses().stream())
            .collect(Collectors.toCollection(HashSet::new));
    }

    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();

        Map<String, Integer> scores1 = new HashMap<>();
        scores1.put("Math", 90);
        scores1.put("Science", 85);
        students.add(new Student(1, "Alice", Arrays.asList("Math", "Science"), scores1));

        Map<String, Integer> scores2 = new HashMap<>();
        scores2.put("Math", 75);
        scores2.put("History", 80);
        students.add(new Student(2, "Bob", Arrays.asList("Math", "History"), scores2));

        Map<String, Integer> scores3 = new HashMap<>();
        scores3.put("Science", 95);
        scores3.put("History", 90);
        scores3.put("Math", 85);
        students.add(new Student(3, "Charlie", Arrays.asList("Math", "Science", "History"), scores3));

        Map<String, Integer> scores4 = new HashMap<>();
        scores4.put("Math", 60);
        students.add(new Student(4, "David", Arrays.asList("Math", "Science"), scores4));

        StudentAnalyzer analyzer = new StudentAnalyzer();

        System.out.println("Unique Courses:");
        Set<String> uniqueCourses = analyzer.getAllUniqueCourses(students);
        for (String course : uniqueCourses) {
            System.out.println("- " + course);
        }

        System.out.println("\nAverage Score Per Course:");
        Map<String, Double> courseAverages = analyzer.getAverageScorePerCourse(students);
        courseAverages.forEach((course, avg) -> System.out.printf("- %s: %.2f\n", course, avg));

        System.out.println("\nTop 2 Students:");
        List<Student> topStudents = analyzer.getTopNStudents(students, 2);
        for (Student student : topStudents) {
            System.out.println("- " + student);
        }

        System.out.println("\nComplexity Analysis:");
        System.out.println("1. Time complexity of computing course averages: O(S * K)");
        System.out.println("   Where S is the number of students and K is the average number of courses per student.");
        System.out.println("   Each student's courses are flatMapped and grouped using a HashMap.");
        System.out.println("2. Complexity of sorting top N students: O(S * log S + S * K)");
        System.out.println("   Computing student averages takes O(S * K) and sorting them takes O(S * log S) comparisons.");
    }
}

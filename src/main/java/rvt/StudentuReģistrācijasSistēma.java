package rvt;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {

    
    static class Student {
        private String name;
        private String surname;
        private String email;
        private String personalCode;
        private String registeredAt;

        public Student(String name, String surname, String email, String personalCode, String registeredAt) {
            this.name = name;
            this.surname = surname;
            this.email = email;
            this.personalCode = personalCode;
            this.registeredAt = registeredAt;
        }

        public String getName() { return name; }
        public String getSurname() { return surname; }
        public String getEmail() { return email; }
        public String getPersonalCode() { return personalCode; }
        public String getRegisteredAt() { return registeredAt; }

        public void setName(String name) { this.name = name; }
        public void setSurname(String surname) { this.surname = surname; }
        public void setEmail(String email) { this.email = email; }

        public String toCSV() {
            return name + "," + surname + "," + email + "," + personalCode + "," + registeredAt;
        }

        public static Student fromCSV(String line) {
            String[] p = line.split(",");
            return new Student(p[0], p[1], p[2], p[3], p[4]);
        }
    }

   
    static class Validator {

        public static boolean validateName(String name) {
            return name.matches("[A-Za-zĀ-ž]{3,}");
        }

        public static boolean validateEmail(String email) {
            return email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
        }

        public static boolean validatePersonalCode(String code) {
            return code.matches("^[0-9]{6}-[0-9]{5}$");
        }
    }

    static class FileHandler {
        private static final String FILE = "students.csv";

        public static List<Student> load() {
            List<Student> list = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
                String line;
                while ((line = br.readLine()) != null) {
                    list.add(Student.fromCSV(line));
                }
            } catch (Exception e) {}
            return list;
        }

        public static void save(List<Student> list) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(FILE))) {
                for (Student s : list) {
                    pw.println(s.toCSV());
                }
            } catch (Exception e) {
                System.out.println("Neizdevās saglabāt failu.");
            }
        }
    }

   
    static Student register(Scanner sc, List<Student> list) {

        System.out.print("Vārds: ");
        String name = sc.nextLine();
        if (!Validator.validateName(name)) {
            System.out.println("Vārds nav derīgs!");
            return null;
        }

        System.out.print("Uzvārds: ");
        String surname = sc.nextLine();
        if (!Validator.validateName(surname)) {
            System.out.println("Uzvārds nav derīgs!");
            return null;
        }

        System.out.print("E-pasts: ");
        String email = sc.nextLine();
        if (!Validator.validateEmail(email)) {
            System.out.println("E-pasts nav derīgs!");
            return null;
        }
        for (Student s : list) {
            if (s.getEmail().equals(email)) {
                System.out.println("E-pasts jau eksistē!");
                return null;
            }
        }

        System.out.print("Personas kods (XXXXXX-XXXXX): ");
        String code = sc.nextLine();
        if (!Validator.validatePersonalCode(code)) {
            System.out.println("Personas kods nav derīgs!");
            return null;
        }
        for (Student s : list) {
            if (s.getPersonalCode().equals(code)) {
                System.out.println("Personas kods jau eksistē!");
                return null;
            }
        }

        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return new Student(name, surname, email, code, time);
    }

       public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        List<Student> list = FileHandler.load();

        System.out.println("Studentu reģistrācijas sistēma");

        while (true) {
            System.out.println("\nPieejamās komandas: register, show, remove, edit, exit");
            System.out.print("> ");
            String cmd = sc.nextLine();

            switch (cmd) {

                case "register":
                    Student s = register(sc, list);
                    if (s != null) {
                        list.add(s);
                        FileHandler.save(list);
                        System.out.println("Students reģistrēts!");
                    }
                    break;

                case "show":
                    System.out.println("+----------------+----------------+----------------------+-----------------+---------------------+");
                    System.out.println("| Vārds          | Uzvārds        | E-pasts              | Personas kods   | Reģistrēts         |");
                    System.out.println("+----------------+----------------+----------------------+-----------------+---------------------+");
                    for (Student st : list) {
                        System.out.printf("| %-14s | %-14s | %-20s | %-15s | %-19s |\n",
                                st.getName(), st.getSurname(), st.getEmail(), st.getPersonalCode(), st.getRegisteredAt());
                    }
                    System.out.println("+----------------+----------------+----------------------+-----------------+---------------------+");
                    break;

                case "remove":
                    System.out.print("Ievadi personas kodu: ");
                    String code = sc.nextLine();
                    list.removeIf(x -> x.getPersonalCode().equals(code));
                    FileHandler.save(list);
                    System.out.println("Lietotājs dzēsts (ja eksistēja).");
                    break;

                case "edit":
                    System.out.print("Ievadi personas kodu: ");
                    String pc = sc.nextLine();
                    Student target = null;
                    for (Student st : list) {
                        if (st.getPersonalCode().equals(pc)) target = st;
                    }
                    if (target == null) {
                        System.out.println("Lietotājs nav atrasts.");
                        break;
                    }

                    System.out.print("Jauns vārds: ");
                    target.setName(sc.nextLine());

                    System.out.print("Jauns uzvārds: ");
                    target.setSurname(sc.nextLine());

                    System.out.print("Jauns e-pasts: ");
                    target.setEmail(sc.nextLine());

                    FileHandler.save(list);
                    System.out.println("Dati atjaunoti.");
                    break;

                case "exit":
                    System.out.println("Programma apturēta.");
                    return;

                default:
                    System.out.println("Nezināma komanda.");
            }
        }
    }
}

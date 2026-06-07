package rvt;

import java.io.*;
import java.util.ArrayList;

public class ToDoList {

    private ArrayList<String> tasks;
    private final String filePath = "todo.csv";

    public ToDoList() {
        this.tasks = new ArrayList<>();
        loadFromFile();
    }

   
    private void loadFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); 

            while ((line = br.readLine()) != null) {
                tasks.add(line); 
            }

        } catch (Exception e) {
            System.out.println("Neizdevās nolasīt failu.");
        }
    }

  
    private int getLastId() {
        if (tasks.isEmpty()) return 0;

        String last = tasks.get(tasks.size() - 1);
        String[] parts = last.split(",");
        return Integer.parseInt(parts[0]);
    }

 
    public void add(String task) {

        if (!checkEventString(task)) {
            System.out.println("Uzdevums nav derīgs!");
            return;
        }

        int newId = getLastId() + 1;
        String newLine = newId + "," + task;

        tasks.add(newLine);
        updateFile();

        System.out.println("Uzdevums pievienots!");
    }

  
    private boolean updateFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {

            pw.println("id,task"); 

            for (String line : tasks) {
                pw.println(line);
            }

            return true;

        } catch (Exception e) {
            System.out.println("Neizdevās atjaunot failu.");
            return false;
        }
    }


    public void remove(int id) {

        boolean removed = tasks.removeIf(line -> {
            String[] p = line.split(",");
            return Integer.parseInt(p[0]) == id;
        });

        if (removed) {
            updateFile();
            System.out.println("Uzdevums dzēsts.");
        } else {
            System.out.println("ID nav atrasts.");
        }
    }


    public boolean checkEventString(String value) {
        return value.matches("[A-Za-z0-9Ā-ž ]{3,}");
    }


    public void show() {
        System.out.println("ID | Uzdevums");
        System.out.println("---------------");

        for (String line : tasks) {
            String[] p = line.split(",");
            System.out.println(p[0] + "  | " + p[1]);
        }
    }


    public static void main(String[] args) {
        ToDoList todo = new ToDoList();

        todo.show();
        todo.add("go running");
        todo.remove(2);
        todo.show();
    }
}

package interpreter.view;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.IStmt;
import interpreter.repository.Repository;

import java.util.Scanner;

public class View {
    private final Scanner scanner = new Scanner(System.in);
    private IStmt selectedProgram;

    public void run() {
        boolean running = true;
        while (running) {
            System.out.println("Toy Lang Interpreter");
            System.out.println("0. Exit");
            System.out.println("1. Select program");
            System.out.println("2. Run selected program");


            if (!scanner.hasNextLine()) {
                break;
            }

            switch (scanner.nextLine().trim()) {
                case "0" -> running = false;
                case "1" -> selectProgram();
                case "2" -> runSelectedProgram();
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void selectProgram() {
        System.out.println("1. Ex1 : int v; v = 2; print(v)");
        System.out.println("2. Ex2 : int a; int b; a = 2 + 3 * 5; b = a + 1; print(b)");
        System.out.println("3. Ex3 : bool a; int v; a = true; (if a then v = 2 else v = 3); print(v)");
        System.out.println("4. Ex4 : bool a; a = 2; print(a)");
        System.out.print("Select program: ");

        if (!scanner.hasNextLine()) {
            return;
        }

        IStmt choice = switch (scanner.nextLine().trim()) {
            case "1" -> Examples.ex1;
            case "2" -> Examples.ex2;
            case "3" -> Examples.ex3;
            case "4" -> Examples.ex4;
            default -> null;
        };

        if (choice == null ) {
            System.out.println("Invalid program number");
            return;
        }

        selectedProgram = choice;
    }

    private void runSelectedProgram() {
        if (selectedProgram == null) {
            System.out.println("Select a program first!");
            return;
        }

        PrgState state = PrgState.of(selectedProgram);

        Controller controller = new Controller(
                new Repository(state, "log1.txt"),
                true
        );

        try {
            controller.allStep();
            System.out.println("Program execution finished: " + state.getOut());
        } catch (MyException e) {
            System.out.println("Interpreter error: " + e.getMessage());
        }
    }

}

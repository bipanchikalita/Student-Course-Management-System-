package com.vityarthi.utils;

import java.util.Scanner;

public class InputUtil {
    private final Scanner scanner;

    public InputUtil(Scanner scanner) {
        this.scanner = scanner;
    }

    public int nextInt() {
        while (true) {
            String line = scanner.nextLine();
            try { return Integer.parseInt(line.trim()); } catch (Exception e) { System.out.print("Please enter a valid integer: "); }
        }
    }

    public double nextDouble() {
        while (true) {
            String line = scanner.nextLine();
            try { return Double.parseDouble(line.trim()); } catch (Exception e) { System.out.print("Please enter a valid number: "); }
        }
    }

    public String nextLine() {
        return scanner.nextLine().trim();
    }
}

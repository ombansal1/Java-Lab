import java.io.*;
import java.util.*;

public class AcademicLedger {
    private static final String DATA_FILE = "ClassLedger.csv";
    private static final Scanner INPUT = new Scanner(System.in);

    private static void ensureFileWithHeader() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("rollNo,fullName,dept,score1,score2,score3,score4,score5,average");
                writer.newLine();
            } catch (IOException e) {
                System.out.println("[ERROR] Could not initialize data file: " + e.getMessage());
            }
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(INPUT.nextLine().trim());
            } catch (NumberFormatException ex) {
                System.out.println("[WARN] Enter a valid whole number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(INPUT.nextLine().trim());
            } catch (NumberFormatException ex) {
                System.out.println("[WARN] Enter a valid numeric value.");
            }
        }
    }

    private static void addStudentEntry() {
        try {
            int rollNo = readInt("  Roll Number      : ");
            System.out.print("  Student Full Name: ");
            String fullName = INPUT.nextLine().trim();
            System.out.print("  Department       : ");
            String department = INPUT.nextLine().trim();

            double test1 = readDouble("  Internal 1 (0-100): ");
            double test2 = readDouble("  Internal 2 (0-100): ");
            double test3 = readDouble("  Internal 3 (0-100): ");
            double test4 = readDouble("  Internal 4 (0-100): ");
            double test5 = readDouble("  Internal 5 (0-100): ");

            double avg = computeAverage(test1, test2, test3, test4, test5);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE, true))) {
                writer.write(rollNo + "," + fullName + "," + department + "," +
                        test1 + "," + test2 + "," + test3 + "," + test4 + "," + test5 + "," +
                        String.format("%.2f", avg));
                writer.newLine();
            }

            System.out.println("  Record saved successfully.\n");
        } catch (IOException e) {
            System.out.println("[ERROR] Unable to write into data file: " + e.getMessage());
        }
    }

    private static void displayAllEntries() {
        ensureFileWithHeader();

        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String row;
            boolean hasData = false;

            System.out.println("\n  --------------------------------------------------------------------------------------------");
            System.out.println("  | Roll | Name            | Dept      | S1    | S2    | S3    | S4    | S5    | Avg       |");
            System.out.println("  --------------------------------------------------------------------------------------------");

            while ((row = reader.readLine()) != null) {
                if (row.startsWith("rollNo")) {
                    continue;
                }

                String[] cols = row.split(",");
                if (cols.length == 9) {
                    System.out.printf("  | %-4s | %-15s | %-9s | %-5s | %-5s | %-5s | %-5s | %-5s | %-9s |%n",
                            cols[0], cols[1], cols[2], cols[3], cols[4], cols[5], cols[6], cols[7], cols[8]);
                    hasData = true;
                }
            }

            if (!hasData) {
                System.out.println("  No student entries available.");
            }

            System.out.println("  --------------------------------------------------------------------------------------------\n");
        } catch (IOException e) {
            System.out.println("[ERROR] Could not read records: " + e.getMessage());
        }
    }

    private static void modifyEntryByRoll(int rollToEdit) {
        ensureFileWithHeader();
        File csv = new File(DATA_FILE);
        List<String> updatedRows = new ArrayList<>();
        boolean recordFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(csv))) {
            String row;
            String firstLine = reader.readLine();
            if (firstLine != null) {
                updatedRows.add(firstLine);
            }

            while ((row = reader.readLine()) != null) {
                String[] cols = row.split(",");

                if (cols.length == 9 && Integer.parseInt(cols[0]) == rollToEdit) {
                    System.out.print("  Updated Full Name : ");
                    String fullName = INPUT.nextLine().trim();
                    System.out.print("  Updated Department: ");
                    String department = INPUT.nextLine().trim();

                    double s1 = readDouble("  Updated Score 1   : ");
                    double s2 = readDouble("  Updated Score 2   : ");
                    double s3 = readDouble("  Updated Score 3   : ");
                    double s4 = readDouble("  Updated Score 4   : ");
                    double s5 = readDouble("  Updated Score 5   : ");
                    double avg = computeAverage(s1, s2, s3, s4, s5);

                    updatedRows.add(rollToEdit + "," + fullName + "," + department + "," +
                            s1 + "," + s2 + "," + s3 + "," + s4 + "," + s5 + "," +
                            String.format("%.2f", avg));
                    recordFound = true;
                } else {
                    updatedRows.add(row);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("[ERROR] Failed while updating entry: " + e.getMessage());
            return;
        }

        if (!recordFound) {
            System.out.println("  No entry found for roll number " + rollToEdit + ".\n");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csv))) {
            for (String r : updatedRows) {
                writer.write(r);
                writer.newLine();
            }
            System.out.println("  Entry updated successfully.\n");
        } catch (IOException e) {
            System.out.println("[ERROR] Could not rewrite file: " + e.getMessage());
        }
    }

    private static void removeEntryByRoll(int rollToDelete) {
        ensureFileWithHeader();
        File csv = new File(DATA_FILE);
        List<String> remainingRows = new ArrayList<>();
        boolean recordFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(csv))) {
            String row;
            String firstLine = reader.readLine();
            if (firstLine != null) {
                remainingRows.add(firstLine);
            }

            while ((row = reader.readLine()) != null) {
                String[] cols = row.split(",");
                if (cols.length == 9 && Integer.parseInt(cols[0]) == rollToDelete) {
                    recordFound = true;
                } else {
                    remainingRows.add(row);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("[ERROR] Failed while deleting entry: " + e.getMessage());
            return;
        }

        if (!recordFound) {
            System.out.println("  No entry found for roll number " + rollToDelete + ".\n");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csv))) {
            for (String r : remainingRows) {
                writer.write(r);
                writer.newLine();
            }
            System.out.println("  Entry deleted successfully.\n");
        } catch (IOException e) {
            System.out.println("[ERROR] Could not rewrite file: " + e.getMessage());
        }
    }

    private static double computeAverage(double a, double b, double c, double d, double e) {
        return (a + b + c + d + e) / 5.0;
    }

    private static void recalculateAverageForEveryone() {
        ensureFileWithHeader();
        File csv = new File(DATA_FILE);
        List<String> refreshed = new ArrayList<>();
        boolean modified = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(csv))) {
            String row;
            String firstLine = reader.readLine();
            if (firstLine != null) {
                refreshed.add(firstLine);
            }

            while ((row = reader.readLine()) != null) {
                String[] cols = row.split(",");
                if (cols.length == 9) {
                    try {
                        double s1 = Double.parseDouble(cols[3]);
                        double s2 = Double.parseDouble(cols[4]);
                        double s3 = Double.parseDouble(cols[5]);
                        double s4 = Double.parseDouble(cols[6]);
                        double s5 = Double.parseDouble(cols[7]);
                        double avg = computeAverage(s1, s2, s3, s4, s5);

                        refreshed.add(cols[0] + "," + cols[1] + "," + cols[2] + "," +
                                cols[3] + "," + cols[4] + "," + cols[5] + "," + cols[6] + "," + cols[7] + "," +
                                String.format("%.2f", avg));
                        modified = true;
                    } catch (NumberFormatException ex) {
                        refreshed.add(row);
                    }
                } else {
                    refreshed.add(row);
                }
            }
        } catch (IOException e) {
            System.out.println("[ERROR] Could not process records: " + e.getMessage());
            return;
        }

        if (!modified) {
            System.out.println("  No valid records found for recalculation.\n");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csv))) {
            for (String r : refreshed) {
                writer.write(r);
                writer.newLine();
            }
            System.out.println("  Average marks recalculated for all students.\n");
        } catch (IOException e) {
            System.out.println("[ERROR] Could not rewrite file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        ensureFileWithHeader();

        System.out.println("========================================");
        System.out.println("      Academic Ledger Console v1");
        System.out.println("========================================");

        while (true) {
            System.out.println("  1) Insert New Student");
            System.out.println("  2) Show All Students");
            System.out.println("  3) Edit Student By Roll");
            System.out.println("  4) Recalculate All Averages");
            System.out.println("  5) Remove Student By Roll");
            System.out.println("  0) Quit Program");

            int option = readInt("  Enter option number: ");
            System.out.println();

            switch (option) {
                case 1:
                    System.out.println("--- ADD NEW STUDENT ---");
                    addStudentEntry();
                    break;
                case 2:
                    System.out.println("--- STUDENT LIST ---");
                    displayAllEntries();
                    break;
                case 3:
                    System.out.println("--- EDIT STUDENT ---");
                    int rollToEdit = readInt("  Roll number to edit: ");
                    modifyEntryByRoll(rollToEdit);
                    break;
                case 4:
                    System.out.println("--- RECALCULATE AVERAGES ---");
                    recalculateAverageForEveryone();
                    break;
                case 5:
                    System.out.println("--- DELETE STUDENT ---");
                    int rollToDelete = readInt("  Roll number to delete: ");
                    removeEntryByRoll(rollToDelete);
                    break;
                case 0:
                    System.out.println("Exiting Academic Ledger. Goodbye!");
                    INPUT.close();
                    return;
                default:
                    System.out.println("Invalid option. Choose from the menu.\n");
            }
        }
    }
}

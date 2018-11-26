package Aufgabe1;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class Einlesen extends Component {

    public static Scanner input = new Scanner(System.in);
    public static Dictionary<String, String> dict;
    public static JFileChooser fc;


    public static void main(String[] args) {

        dict = new SortedArrayDictionary<>();
        System.out.println("Einlesen für Dictionary, h für help");
        String s = input.next();
        while (s.equals("exit") == false) {
            switch (s) {
                 case "create": create(); break;
                 case "read": read(); break;
                 case "p" : print(); break;
                 case "s" : search(); break;
                 case "i" : insert(); break;
                 case "r" : remove(); break;
                 case "h" : help(); break;
                 case "z" : zeitMessung(); break;
                 default:
                     System.out.println("Falsche Eingabe!");
                     input.nextLine();
            }
            s = input.next();
        }
    }

    private static void help() {
        System.out.println("create: Legt ein Dictionary an. SortedArrayDictionary ist voreingestellt.");
        System.out.println("read [n]:  Liest die ersten n Einträge der Datei in das Dictionary ein." +
                " Wird n weggelassen, dann werden alle Einträge eingelesen.");
        System.out.println("p: Gibt alle Einträge des Dictionary in der Konsole aus");
        System.out.println("s deutsch: Gibt das entsprechende englische Wort aus");
        System.out.println("i deutsch englisch: Fügt ein neues Wortpaar in das Dictionary ein");
        System.out.println("r deutsch: Löscht einen Eintrag");
        System.out.println("exit: beendet das Programm");
    }

    @SuppressWarnings("unchecked")
    private static void create() {
        String s = input.next();
        switch (s) {
            case "SortedArrayDictionary":
            case "SAD":
            case "sad":
                dict = SortedArrayDictionary();
                System.out.println("SortedArray Erstellt");
                break;
            case "HashDictionary":
            case "HD":
            case "hd":
                dict = HashDictionary();
                System.out.println("HashArray Erstellt");
                break;
            case "BinaryTreeDictionary":
            case "BTD":
            case "btd":
                dict = BinaryTreeDictionary();
                System.out.println("BinaryTreeDictionary Erstellt");
                break;
            default:
                dict = SortedArrayDictionary();
                System.out.println("SortedArray Erstellt, weil falsche Eingabe!");
                break;
        }

    }

    private static void read() {
        System.out.print("Read File");
        long finalTime = 0;
        fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int tmp = fc.showOpenDialog(fc.getParent());
        if (tmp == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            try {
                Scanner sc2 = new Scanner(file);
                if (input.hasNextInt()) {
                    int n = input.nextInt();
                    for (int i = 0; i < n;i++) {
                        if (!sc2.hasNext()) break;
                        String k = sc2.next();
                        System.out.println(k);
                        String v = sc2.next();
                        System.out.println(v);
                        final long timeStart = System.nanoTime();
                        dict.insert(k, v);
                        final long timeEnd = System.nanoTime();
                        finalTime += (timeEnd - timeStart);

                    }
                } else {
                    while (sc2.hasNextLine()) {
                        if (!sc2.hasNext()) break;
                        String k = sc2.next();
                        System.out.println(k);
                        String v = sc2.next();
                        System.out.println(v);
                        final long timeStart = System.nanoTime();
                        dict.insert(k, v);
                        final long timeEnd = System.nanoTime();
                        finalTime += (timeEnd - timeStart);
                    }
                }
            } catch(FileNotFoundException e) {
                System.out.println("Fehler!" + e.getMessage());
            }
            System.out.println("(" + (finalTime / 1e+9) + " Sekunden)/("
                    + (finalTime / 1e+6) + " Millisekunden)");
        }

    }

    private static void print() {
        System.out.println("Print");
        System.out.println(dict.toString());
    }

    private static void search() {
        System.out.println("Suche Englisches Wort");
        String s = input.next();
        final long timeStart = System.nanoTime();
        String x = dict.search(s);
        final long timeEnd = System.nanoTime();
        if (x == null) {
            System.out.println("Suche nicht erfolgreich!");
        } else {
            System.out.print(s + ": " + x + " wurde gefunden!");
        }
        System.out.println("(" + ((timeEnd - timeStart) / 1e+9)+ " Sekunden)/("
                + ((timeEnd - timeStart) / 1e+6) + " Millisekunden)");
    }

    private static void insert() {
        System.out.println("Insert");
        System.out.print("Deutsches Wort: ");
        String s = input.next();
        System.out.println(s);
        System.out.print("Englisches Wort: " );
        String s2 = input.next();
        System.out.println(s2);
        dict.insert(s,s2);
        System.out.println(s + ": " + s2 + " wurde hinzugefügt!");
    }

    private static void remove() {
        System.out.println("Remove");
        String s = input.next();
        String s2 = dict.remove(s);
        if (s2 != null) {
            System.out.println(s + ": " + s2 + " wurde removed! ");
        } else {
            System.out.println(s + " wurde nicht gefunden!");
        }

    }

    private static Dictionary SortedArrayDictionary() {
        return new SortedArrayDictionary<>();
    }

    private static Dictionary HashDictionary() {
        return new HashDictionary<>(3);
    }

    private static Dictionary BinaryTreeDictionary() {
        return new BinaryTreeDictionary<>();
    }


    private static void zeitMessung() {
        LinkedList<String> testD = new LinkedList<String>();
        LinkedList<String> testE = new LinkedList<String>();
        for (Dictionary.Entry x : dict) {
            testD.add((String) x.getKey());
        }
        for (Dictionary.Entry x : dict) {
            testE.add((String) x.getValue());
        }
        long finalTime = 0;
        int y = input.nextInt();
        if(y == 0) {
            System.out.print("Gefundene Wörter!");
            for (String x : testD) {
                final long startTime = System.nanoTime();
                dict.search(x);
                final long endTime = System.nanoTime();
                finalTime += (endTime - startTime);
            }
        } else {
            System.out.print("Nicht gefundene Wörter!");
            for (String x : testE) {
                final long startTime = System.nanoTime();
                dict.search(x);
                final long endTime = System.nanoTime();
                finalTime += (endTime - startTime);
            }
        }
         System.out.println("(" + (finalTime / 1e+9) + " Sekunden)/("
                + (finalTime / 1e+6) + " Millisekunden)");
    }
}

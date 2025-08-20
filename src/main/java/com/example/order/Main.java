package com.example.order;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        String filePath = (args.length > 0) ? args[0] : "events.txt";
        EventProcessor processor = new EventProcessor();
        processor.registerObserver(new LoggerObserver());
        processor.registerObserver(new AlertObserver());

        System.out.println("Reading events from: " + filePath);

        try (BufferedReader br = Files.newBufferedReader(Path.of(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    Event event = EventParser.parse(line);
                    if (event != null) {
                        processor.processEvent(event);
                    }
                } catch (Exception ex) {
                    System.out.println("[Error] Failed to parse/process line: " + line);
                    ex.printStackTrace(System.out);
                }
            }
        } catch (IOException e) {
            System.out.println("[Fatal] Could not read file: " + filePath);
            e.printStackTrace(System.out);
        }
    }
}

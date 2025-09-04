package core.basesyntax;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SalaryInfo {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public String getSalaryInfo(String[] names, String[] data, String dateFrom, String dateTo) {
        DateTimeFormatter formatter = FORMATTER;
        LocalDate from = LocalDate.parse(dateFrom, formatter);
        LocalDate to = LocalDate.parse(dateTo, formatter);
        Map<String, Integer> totals = new HashMap<>();
        for (String record : data) {
            String[] values = record.split("\\s");
            LocalDate date = LocalDate.parse(values[0], formatter);
            if (date.isBefore(from) || date.isAfter(to)) {
                continue;
            }
            String name = values[1];
            if (!Arrays.asList(names).contains(name)) {
                continue;
            }
            int hours = Integer.parseInt(values[2]);
            int salary = Integer.parseInt(values[3]);
            int total = hours * salary;
            if (totals.containsKey(name)) {
                total += totals.get(name);
            }
            totals.put(name, total);
        }
        StringBuilder builder = new StringBuilder("Report for period ");
        builder.append(dateFrom).append(" - ").append(dateTo);
        for (String name : names) {
            builder.append(System.lineSeparator()).append(name).append(" - ").append(totals.get(name));
        }
        return builder.toString();
    }
}

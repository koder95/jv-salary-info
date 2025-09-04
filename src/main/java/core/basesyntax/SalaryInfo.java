package core.basesyntax;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SalaryInfo {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final int DATE_INDEX = 0;
    private static final int HOURS_INDEX = 2;
    private static final int SALARY_INDEX = 3;

    public String getSalaryInfo(String[] names, String[] data, String dateFrom, String dateTo) {
        DateTimeFormatter formatter = FORMATTER;
        LocalDate from = LocalDate.parse(dateFrom, formatter);
        LocalDate to = LocalDate.parse(dateTo, formatter);
        Map<String, Integer> totals = new HashMap<>();
        for (String record : data) {
            String[] values = record.split("\\s");
            LocalDate date = LocalDate.parse(values[DATE_INDEX], formatter);
            if (date.isBefore(from) || date.isAfter(to)) {
                continue;
            }
            String name = values[1];
            if (!Arrays.asList(names).contains(name)) {
                continue;
            }
            int hours = Integer.parseInt(values[HOURS_INDEX]);
            int salary = Integer.parseInt(values[SALARY_INDEX]);
            int total = hours * salary;
            if (totals.containsKey(name)) {
                total += totals.get(name);
            }
            totals.put(name, total);
        }
        StringBuilder builder = new StringBuilder("Report for period ");
        builder.append(dateFrom).append(" - ").append(dateTo);
        for (String name : names) {
            Integer total = totals.get(name);
            builder.append(System.lineSeparator()).append(name)
                    .append(" - ").append(total == null ? 0 : total);
        }
        return builder.toString();
    }
}

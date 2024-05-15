package ru.job4j.ood.isp.menu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Printer implements MenuPrinter {
    public static final String ROW_LEVEL = "--";

    @Override
    public void print(Menu menu) {
        for (Menu.MenuItemInfo menuItemInfo : menu) {
            String name = menuItemInfo.getName();
            StringBuilder stringBuilder = new StringBuilder();
            String lineSeparator = System.lineSeparator();
            String level = menuItemInfo.getNumber();

            replaceLevel(stringBuilder, level);
            stringBuilder.append(name).append(lineSeparator);

            System.out.print(stringBuilder);
        }
    }

    private void replaceLevel(StringBuilder stringBuilder, String level) {
        Pattern pattern = Pattern.compile("(\\d+\\W)");
        Matcher matcher = pattern.matcher(level);
        int matchCount = 0;
        while (matcher.find()) {
            matchCount++;
            String replaceString = matchCount == 1 ? "" : ROW_LEVEL;
            matcher.appendReplacement(stringBuilder, replaceString);
        }
        matcher.appendTail(stringBuilder);
    }
}

package ru.job4j.ood.isp.menu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberMenuPrinter extends Printer {

    @Override
    public void print(Menu menu) {
        for (Menu.MenuItemInfo menuItemInfo : menu) {
            String name = menuItemInfo.getName();
            StringBuilder stringBuilder = new StringBuilder();
            String lineSeparator = System.lineSeparator();
            String level = menuItemInfo.getNumber();

            addRowLevel(stringBuilder, level);
            stringBuilder.append(level).append(name).append(lineSeparator);

            System.out.print(stringBuilder);
        }
    }

    private void addRowLevel(StringBuilder stringBuilder, String level) {
        Pattern pattern = Pattern.compile("(\\d+\\W)");
        Matcher matcher = pattern.matcher(level);
        while (matcher.find()) {
            matcher.appendReplacement(stringBuilder, ROW_LEVEL);
        }
        matcher.appendTail(stringBuilder);
    }
}

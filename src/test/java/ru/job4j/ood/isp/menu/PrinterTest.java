package ru.job4j.ood.isp.menu;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.*;

class PrinterTest {
    @Test
    public void whenPrintFirstLevelMenu() {
        Menu menu = new SimpleMenu();
        ActionDelegate stubAction = System.out::println;
        menu.add(Menu.ROOT, "Сходить в магазин", stubAction);
        menu.add(Menu.ROOT, "Покормить собаку", stubAction);
        Printer printer = new Printer();
        String expectedOutput = "Сходить в магазин" + System.lineSeparator()
                + "Покормить собаку" + System.lineSeparator();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        printer.print(menu);

        assertThat(outContent.toString()).isEqualTo(expectedOutput);
    }

    @Test
    public void whenPrintMultiLevelMenu() {
        Menu menu = new SimpleMenu();
        ActionDelegate stubAction = System.out::println;
        menu.add(Menu.ROOT, "Сходить в магазин", stubAction);
        menu.add(Menu.ROOT, "Покормить собаку", stubAction);
        menu.add("Сходить в магазин", "Купить продукты", stubAction);
        menu.add("Купить продукты", "Купить хлеб", stubAction);
        menu.add("Купить продукты", "Купить молоко", stubAction);
        Printer printer = new Printer();
        String expectedOutput = "Сходить в магазин" + System.lineSeparator()
                + "--Купить продукты" + System.lineSeparator()
                + "----Купить хлеб" + System.lineSeparator()
                + "----Купить молоко" + System.lineSeparator()
                + "Покормить собаку" + System.lineSeparator();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        printer.print(menu);

        assertThat(outContent.toString()).isEqualTo(expectedOutput);
    }

    @Test
    public void whenPrintEmptyMenu() {
        Menu menu = new SimpleMenu();
        Printer printer = new Printer();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        printer.print(menu);

        assertThat(outContent.toString()).isEmpty();
    }
}
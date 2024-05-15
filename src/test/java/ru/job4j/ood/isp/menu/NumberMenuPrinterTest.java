package ru.job4j.ood.isp.menu;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.*;

class NumberMenuPrinterTest {

    @Test
    public void whenPrintFirstLevelMenu() {
        Menu menu = new SimpleMenu();
        ActionDelegate stubAction = System.out::println;
        menu.add(Menu.ROOT, "Сходить в магазин", stubAction);
        menu.add(Menu.ROOT, "Покормить собаку", stubAction);
        MenuPrinter printer = new NumberMenuPrinter();
        String expectedOutput = "--1.Сходить в магазин" + System.lineSeparator()
                                + "--2.Покормить собаку" + System.lineSeparator();
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
        MenuPrinter printer = new NumberMenuPrinter();
        String expectedOutput = "--1.Сходить в магазин" + System.lineSeparator()
                + "----1.1.Купить продукты" + System.lineSeparator()
                + "------1.1.1.Купить хлеб" + System.lineSeparator()
                + "------1.1.2.Купить молоко" + System.lineSeparator()
                + "--2.Покормить собаку" + System.lineSeparator();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        printer.print(menu);

        assertThat(outContent.toString()).isEqualTo(expectedOutput);
    }

    @Test
    public void whenPrintEmptyMenu() {
        Menu menu = new SimpleMenu();
        MenuPrinter printer = new NumberMenuPrinter();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        printer.print(menu);

        assertThat(outContent.toString()).isEmpty();
    }

}
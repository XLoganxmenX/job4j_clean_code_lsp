package ru.job4j.ood.isp.menu;

public class NumberMenuPrinter implements MenuPrinter {
    @Override
    public void print(Menu menu) {
        menu.forEach(i -> System.out.println(i.getNumber() + i.getName()));
    }
}

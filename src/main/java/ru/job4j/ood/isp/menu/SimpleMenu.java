package ru.job4j.ood.isp.menu;

import java.util.*;

public class SimpleMenu implements Menu {

    private final List<MenuItem> rootElements = new ArrayList<>();

    @Override
    public boolean add(String parentName, String childName, ActionDelegate actionDelegate) {
        boolean result = false;

        if (Objects.equals(parentName, Menu.ROOT)) {
            MenuItem newMenuItem = new SimpleMenuItem(childName, actionDelegate);
            rootElements.add(newMenuItem);
            result = true;
        } else {
            Optional<ItemInfo> existItemInfo = findItem(parentName);
            if (existItemInfo.isPresent()) {
                MenuItem children = new SimpleMenuItem(childName, actionDelegate);
                existItemInfo.get().menuItem.getChildren().add(children);
                result = true;
            }
        }

        return result;
    }

    @Override
    public Optional<MenuItemInfo> select(String itemName) {
        Optional<ItemInfo> itemInfo = findItem(itemName);
        Menu.MenuItemInfo menuItemInfo = null;

        if (itemInfo.isPresent()) {
            MenuItem menuItem = itemInfo.get().menuItem;
            String number = itemInfo.get().number;
            menuItemInfo = new Menu.MenuItemInfo(menuItem, number);
        }

        return Optional.ofNullable(menuItemInfo);
    }

    @Override
    public Iterator<MenuItemInfo> iterator() {
        List<MenuItemInfo> menuItemInfoList = new LinkedList<>();
        DFSIterator itemInfoIterator = new DFSIterator();

        while (itemInfoIterator.hasNext()) {
            ItemInfo itemInfo = itemInfoIterator.next();
            MenuItemInfo menuItemInfo = new MenuItemInfo(itemInfo.menuItem, itemInfo.number);
            menuItemInfoList.add(menuItemInfo);
        }

        return menuItemInfoList.iterator();
    }

    private Optional<ItemInfo> findItem(String name) {
        ItemInfo desiredItem = null;
        DFSIterator iterator = new DFSIterator();
        while (iterator.hasNext()) {
            desiredItem = iterator.next();
            String itemInfoName = desiredItem.menuItem.getName();
            if (name.equals(itemInfoName)) {
                break;
            }
        }
        return Optional.ofNullable(desiredItem);
    }

    private static class SimpleMenuItem implements MenuItem {

        private String name;
        private List<MenuItem> children = new ArrayList<>();
        private ActionDelegate actionDelegate;

        public SimpleMenuItem(String name, ActionDelegate actionDelegate) {
            this.name = name;
            this.actionDelegate = actionDelegate;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public List<MenuItem> getChildren() {
            return children;
        }

        @Override
        public ActionDelegate getActionDelegate() {
            return actionDelegate;
        }
    }

    private class DFSIterator implements Iterator<ItemInfo> {

        private Deque<MenuItem> stack = new LinkedList<>();

        private Deque<String> numbers = new LinkedList<>();

        DFSIterator() {
            int number = 1;
            for (MenuItem item : rootElements) {
                stack.addLast(item);
                numbers.addLast(String.valueOf(number++).concat("."));
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public ItemInfo next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            MenuItem current = stack.removeFirst();
            String lastNumber = numbers.removeFirst();
            List<MenuItem> children = current.getChildren();
            int currentNumber = children.size();
            for (var i = children.listIterator(children.size()); i.hasPrevious();) {
                stack.addFirst(i.previous());
                numbers.addFirst(lastNumber.concat(String.valueOf(currentNumber--)).concat("."));
            }
            return new ItemInfo(current, lastNumber);
        }
    }

    private class ItemInfo {

        private MenuItem menuItem;
        private String number;

        public ItemInfo(MenuItem menuItem, String number) {
            this.menuItem = menuItem;
            this.number = number;
        }
    }
}
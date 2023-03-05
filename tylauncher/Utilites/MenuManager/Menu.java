package tylauncher.Utilites.MenuManager;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import tylauncher.Utilites.Constants.Menus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Menu {

    private ContextMenu contextMenu;

    private final List<MenuItem> items;

    private int index;


    public Menu(MenuItem[] items) {
        this.contextMenu = new ContextMenu();
        this.items = new ArrayList<>();
        for(MenuItem item : items){
            this.addMenuItem(item);
        }
    }
    public Menu() {
        this.contextMenu = new ContextMenu();
        this.items = new ArrayList<>();
    }

    public void confirm(){
        contextMenu.getItems().clear();
        items.forEach(item -> contextMenu.getItems().add(item));
    }
    public void addMenuItem(MenuItem item){
        items.add(item);
        index++;
    }
    public void addMenuItem(String text){
        items.add(new MenuItem(text));
        index++;
    }

    public void delMenuitem(int index){
        items.remove(index);
    }

    public MenuItem getMenuItem(int index){
        return items.get(index);
    }

    public ContextMenu getContextMenu() {
        return contextMenu;
    }
}

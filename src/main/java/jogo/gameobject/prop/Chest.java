package jogo.gameobject.prop;

import jogo.framework.math.Vec3;
import jogo.gameobject.item.Item; // Importa a classe Item para poder armazenar itens
import java.util.ArrayList;
import java.util.List;

public class Chest extends Prop {
    private boolean isOpen;
    private List<Item> inventory; // O baú pode conter uma lista de itens

    public Chest(String name, Vec3 position) {
        super(name);
        this.setPosition(position);
        this.isOpen = false;
        this.inventory = new ArrayList<>();
    }

    public void addItem(Item item) {
        this.inventory.add(item);
    }

    public List<Item> getInventory() {
        return inventory;
    }

    @Override
    public void onInteract() {
        if (!isOpen) {
            System.out.println("Abrindo o " + this.getName() + "...");
            if (inventory.isEmpty()) {
                System.out.println("O " + this.getName() + " está vazio.");
            } else {
                System.out.println("Conteúdo do " + this.getName() + ":");
                for (Item item : inventory) {
                    System.out.println("- " + item.getName());
                }
            }
            this.isOpen = true;
        } else {
            System.out.println("O " + this.getName() + " já está aberto.");
        }
    }

    public boolean isOpen() {
        return isOpen;
    }
}
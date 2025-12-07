package jogo.gameobject.inventory;

import jogo.gameobject.item.Item;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    
    private final int capacity;
    private final List<ItemStack> slots;
    private final int stackLimit = 64; // Limite por stack
    
    public Inventory(int capacity) {
        this.capacity = capacity;
        this.slots = new ArrayList<>();
    }
    public boolean addItem(Item item, int amount) {
        //Tentar adicionar a stacks existentes
        for (ItemStack stack : slots) {
            if (stack.getItem().getClass().equals(item.getClass())) {
                int space = stackLimit - stack.getQuantity();
                if (space > 0) {
                    int toAdd = Math.min(space, amount);
                    stack.add(toAdd);
                    amount -= toAdd;
                    if (amount == 0) return true;
                }
            }
        }
        
        //Criar novos stacks se houver espaço
        if (amount > 0 && slots.size() < capacity) {
            slots.add(new ItemStack(item, amount));
            return true;
        }
        
        return amount == 0; // Retorna false se não conseguiu guardar tudo
    }
    
    public List<ItemStack> getSlots() {
        return slots;
    }
}
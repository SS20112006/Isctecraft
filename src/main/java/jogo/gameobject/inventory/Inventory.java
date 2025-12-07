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

    public boolean removeItem(Item item, int amount) {
        // Verificar primeiro se temos items suficientes
        if (!hasItem(item, amount)) return false;
        int remainingToRemove = amount;
        
        // Iterar de trás para a frente para remover stacks vazios com segurança se usássemos índices,
        // mas com foreach e remove do iterador seria mais complexo.
        // Vamos usar uma lista temporária ou iterar e modificar.
        
        // Estratégia simples: Iterar pelos slots
        for (int i = 0; i < slots.size(); i++) {
            ItemStack stack = slots.get(i);
            if (stack.getItem().getClass().equals(item.getClass())) {
                int take = Math.min(remainingToRemove, stack.getQuantity());
                stack.remove(take);
                remainingToRemove -= take;
                
                if (stack.getQuantity() <= 0) {
                    slots.remove(i);
                    i--; // Ajustar índice após remoção
                }
                
                if (remainingToRemove <= 0) return true;
            }
        }
        return remainingToRemove <= 0;
    }
    
    public boolean hasItem(Item item, int amount) {
        int count = 0;
        for (ItemStack stack : slots) {
            if (stack.getItem().getClass().equals(item.getClass())) {
                count += stack.getQuantity();
            }
        }
        return count >= amount;
    }

    public boolean canAddItem(Item item, int amount) {
        int remainingAmount = amount;
        
        //Verificar espaço em stacks existentes
        for (ItemStack stack : slots) { // ':' serve para for each 
            if (stack.getItem().getClass().equals(item.getClass())) {
                int space = stackLimit - stack.getQuantity();
                if (space > 0) {
                    remainingAmount -= space;
                    if (remainingAmount <= 0) return true;
                }
            }
        }
        
        //Verificar se há slots vazios para o que sobra
        if (remainingAmount > 0) {
            //Quantos stacks novos precisamos?
            int stacksNeeded = (int) Math.ceil((double) remainingAmount / stackLimit);
            int emptySlots = capacity - slots.size();
            return emptySlots >= stacksNeeded;
        }
        
        return true;
    }
    
    public List<ItemStack> getSlots() {
        return slots;
    }
}
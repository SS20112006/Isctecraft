package jogo.gameobject.inventory;
import jogo.gameobject.item.Item;
public class ItemStack {
    private Item item;
    private int quantity;
    
    public ItemStack(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }
    
    public Item getItem() { 
        return item; 
    }
    public int getQuantity() { 
        return quantity; 
    }
    public void setQuantity(int quantity) { 
        this.quantity = quantity; 
    }
    
    public void add(int amount) { 
        this.quantity += amount; 
    }
    public void remove(int amount) { 
        this.quantity -= amount; 
    }
}

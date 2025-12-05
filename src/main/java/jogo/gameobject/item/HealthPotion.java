package jogo.gameobject.item;

import jogo.framework.math.Vec3;

public class HealthPotion extends Item {
    private int healAmount;

    public HealthPotion(String name, Vec3 position, int healAmount) {
        super(name);
        this.setPosition(position);
        this.healAmount = healAmount;
    }

    public int getHealAmount() {
        return healAmount;
    }

    @Override
    public void onInteract() {
        System.out.println(this.getName() + " foi usada! Cura: " + this.healAmount + ".");
        // Lógica para aplicar a cura num personagem
    }

    @Override
    public String toString() {
        return "HealthPotion{" +
                "name='" + name + '\'' +
                ", position=" + position +
                ", healAmount=" + healAmount +
                '}';
    }
}
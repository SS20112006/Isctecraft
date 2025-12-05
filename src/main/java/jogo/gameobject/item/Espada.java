package jogo.gameobject.item;
import jogo.framework.math.Vec3;
public class Espada extends Item {
    private int damage;
    private int durabilidade;

    public Espada(String name, int damage, int durabilidade) {
        super(name);
        this.damage = damage;
        this.durabilidade = durabilidade;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDurabilidade() {
        return durabilidade;
    }

    public void setDurabilidade(int durabilidade) {
        this.durabilidade = durabilidade;
    }
    public void usoDurabilidade(int amount) {
        this.durabilidade -= amount;
        if(this.durabilidade <= 0) {
            this.durabilidade = 0;
        };
    }// parte a espada (mesma logica que a vida)


    public void UsoDaArma() {
        usoDurabilidade(1);
    }

    @Override
    public String toString() {
        return "Espada{" +
                "damage=" + damage +
                ", durabilidade=" + durabilidade +
                ", name='" + name + '\'' +
                ", position=" + position +
                '}';
    }
}

package jogo.gameobject.prop;

import jogo.framework.math.Vec3;

public class Tree extends Prop {
    private int health; // Representa a "vida" da árvore antes de ser partida

    public Tree(String name, Vec3 position, int health) {
        super(name);
        this.setPosition(position);
        this.health = health;
    }

    public void takeDamage(int amount) {
        this.health = Math.max(0, this.health - amount);
        if (this.health == 0) {
            System.out.println("A " + this.getName() + " foi partida!");
            // Lógica para dropar itens ou remover a árvore do mundo pode ser adicionada aqui
        }
    }

    @Override
    public void onInteract() {
        System.out.println("Interagindo com a " + this.getName() + ". Vida restante: " + this.health);
        // Exemplo: ao interagir, a árvore pode perder vida
        takeDamage(1);
    }

    public int getHealth() {
        return health;
    }
}
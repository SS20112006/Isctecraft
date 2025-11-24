package jogo.gameobject.item;

import jogo.framework.math.Vec3;

public class Stick extends Item{
    public Stick(String name, Vec3 position) {
        super(name);
        this.setPosition(position);
    }

    @Override
    public String toString() {
        return "Stick{" +
                "name='" + name + '\'' +
                ", position=" + position +
                '}';
    }

}

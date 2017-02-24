package GameLoop.Units;

import GameLoop.Graphics.Screen;
import GameLoop.Graphics.Sprite;
import java.io.Serializable;

public class Pokemon implements Serializable {

    enum Type {
        Grass, Fire, Electric, Water
    }
    String name;
    int id;
    Type myType;
    Type weakness;
    int currHP, maxHP;
    int exp;
    int level;
    Sprite select;
    Sprite battle;

    public Pokemon(String name) {
        select = new Sprite("graphics/Pokemons/" + name + "/select.png", 96);
        battle = new Sprite("graphics/Pokemons/" + name + "/battle.png", 96);
    }

    public void render(Screen screen) {
        screen.renderSprite(select, 20, 20);
    }

}

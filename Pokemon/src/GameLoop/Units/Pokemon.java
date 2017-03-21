package GameLoop.Units;

import GameLoop.Graphics.Screen;
import GameLoop.Graphics.Sprite;
import Utilities.Attack;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Pokemon implements Serializable {

    public static final long serialVersionUID = 2L;

    public static Map<String, Pokemon> pokemon = new HashMap<>();

    public enum Type {
        Grass, Fire, Electric, Water, Normal
    }
    public String name;
    public int id;
    public Type myType;
    public Type weakness;
    public int currHP, maxHP;
    public int exp;
    public int level;
    public Sprite select;
    public Sprite battle;

    public Pokemon(String name) {
        select = new Sprite("graphics/Pokemons/" + name.toLowerCase() + "/select.png", 96);
        battle = new Sprite("graphics/Pokemons/" + name.toLowerCase() + "/battle.png", 96);
    }

    public Pokemon(String name, String type, String id, String weakness, String maxHP) {
        this.name = name;
        this.myType = Type.valueOf(type);
        this.weakness = Type.valueOf(weakness);
        this.id = Integer.valueOf(id);
        this.maxHP = Integer.valueOf(maxHP);
        this.currHP = this.maxHP;
        this.exp = 0;
        this.level = 1;
        select = new Sprite("graphics/Pokemons/" + name.toLowerCase() + "/select.png", 96);
        battle = new Sprite("graphics/Pokemons/" + name.toLowerCase() + "/battle.png", 96);
    }

    public static void readPokemonsJSON() {

        try {
            FileReader file = new FileReader("data/pokemons.json");

            JSONObject jsonObject = (JSONObject) new JSONParser().parse(file);
            JSONArray array = (JSONArray) jsonObject.get("array"); // it should be any array name

            Iterator<Object> iterator = array.iterator();
            while (iterator.hasNext()) {
                JSONObject currEle = (JSONObject) new JSONParser().parse(iterator.next().toString());
                Pokemon currPokemon = new Pokemon(
                        currEle.get("name").toString(),
                        currEle.get("type").toString(),
                        currEle.get("ID").toString(),
                        currEle.get("weakness").toString(),
                        currEle.get("maxHP").toString());
                Pokemon.pokemon.put(currPokemon.name, currPokemon);
                //AttackJSON.attacks.add(currAttack);
                //System.out.println("If iterator have next element: " + currEle);
            }
        } catch (IOException | ParseException ex) {
            Logger.getLogger(Attack.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void render(Screen screen) {
        screen.renderSprite(select, 20, 20);
    }

}

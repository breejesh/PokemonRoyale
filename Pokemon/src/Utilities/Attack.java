package Utilities;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Attack {
    
    public static Map<String, Attack> attacks = new HashMap<>();
    
    enum Type {
        Grass, Fire, Electric, Water, Normal
    }
    private String name;
    private Type pokemonType;
    private Type type;
    private int iniDmg;
    private int currCount;
    private int maxCount;
    
    public Attack() {
        
    }
    
    public Attack(String name, String pType, String type, String iniDmg, String cCount, String maxCount) {
        this.name = name;
        this.pokemonType = Type.valueOf(pType);
        this.type = Type.valueOf(type);
        this.iniDmg = Integer.valueOf(iniDmg);
        this.currCount = Integer.valueOf(cCount);
        this.maxCount = Integer.valueOf(maxCount);
    }
    
    public static void readAttacksJSON() {

        try {
            FileReader file = new FileReader("data/attacks.json");

            JSONObject jsonObject = (JSONObject) new JSONParser().parse(file);
            JSONArray array = (JSONArray) jsonObject.get("array"); // it should be any array name

            Iterator<Object> iterator = array.iterator();
            while (iterator.hasNext()) {
                JSONObject currEle = (JSONObject)new JSONParser().parse(iterator.next().toString());
                Attack currAttack = new Attack(
                        currEle.get("name").toString(), 
                        currEle.get("pType").toString(), 
                        currEle.get("type").toString(),
                        currEle.get("iniDmg").toString(),
                        currEle.get("currCount").toString(),
                        currEle.get("maxCount").toString());
                Attack.attacks.put(currAttack.name, currAttack);
                //AttackJSON.attacks.add(currAttack);
                //System.out.println("If iterator have next element: " + currEle);
            }
        } catch (IOException | ParseException ex) {
            Logger.getLogger(Attack.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void printInfo() {
        System.out.println("Name: " + this.name);
        System.out.println("  Type: " + this.type.toString());
        System.out.println("  Pokemon Type: " + this.pokemonType.toString());
        System.out.println("  Initial Damage: " + this.iniDmg);
        System.out.println("  Current Count: " + this.currCount);
        System.out.println("  Max Count: " + this.maxCount);
    }
}

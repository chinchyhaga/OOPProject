package Code;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Pokemon {

	private String name;                    //name of pokemon
	private int num;                        //amount in storage
	private String[] type;                  //type of pokemon
	private String ability;                 //ability of pokemon
	private String nature;                  //nature of pokemon: increase or decrease some stat
	private int level;                      //level of pokemon
	private int[] baseStats = new int[6];   //base stats: hp, attack, defense, sattack, sdefense, speed
	private int[] IVs = new int[6];         //multipile this when levelup: hp, attack, defense, sattack, sdefense, speed
	private int[] EVs = new int[6];         //hidden stat: when level reaches max: calculate this: hp, attack, defense, sattack, sdefense, speed
	private int[] stats = new int[6];       //stats after self-calculated: hp, attack, defense, sattack, sdefense, speed
	private int[] currentStats;             //final stats: hp, attack, defense, sattack, sdefense, speed
	private int natureVal = 1;              //temp int, until implement nature
	private int state;                      //1 for alive and 0 for fainted

	private String[] strMoves;              //move of Pokemon
	private Move[] moves = new Move[4];     //read move file
//	private int HP;
//	private int attack;
//	private int defense;
//	private int spAttack;
//	private int spDefense;
//	private int speed;

    //constructor
    //pokemon types and four moves
	public Pokemon(String nam, String nat, int hpEV, int atkEV, int defEV, int spAEV, int spDEV, int speEV, String[] inMoves){

		JSONParser parser = new JSONParser();
		JSONObject jsonObject = null;
		try {
			jsonObject = (JSONObject) parser.parse(new FileReader("Data/pokemondex.txt"));
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		name = nam;
		String jsonName = name.toLowerCase().replace("-","");

		int numOfTypes = ((JSONArray)((JSONObject) jsonObject.get(jsonName)).get("types")).size();
		type = new String[numOfTypes];

		for(int i = 0; i < numOfTypes; i++){
			type[i] = (String) ((JSONArray)((JSONObject) jsonObject.get(jsonName)).get("types")).get(i);
		}
		num = (int) (long) ((JSONObject) jsonObject.get(jsonName)).get("num");
		nature = nat;
		level = 100;
		state = 1;

		baseStats[0] = (int) (long) ((JSONObject)((JSONObject) jsonObject.get(jsonName)).get("baseStats")).get("hp");
		baseStats[1] = (int) (long) ((JSONObject)((JSONObject) jsonObject.get(jsonName)).get("baseStats")).get("atk");
		baseStats[2] = (int) (long) ((JSONObject)((JSONObject) jsonObject.get(jsonName)).get("baseStats")).get("def");
		baseStats[3] = (int) (long) ((JSONObject)((JSONObject) jsonObject.get(jsonName)).get("baseStats")).get("spa");
		baseStats[4] = (int) (long) ((JSONObject)((JSONObject) jsonObject.get(jsonName)).get("baseStats")).get("spd");
		baseStats[5] = (int) (long) ((JSONObject)((JSONObject) jsonObject.get(jsonName)).get("baseStats")).get("spe");

		for(int i = 0; i < IVs.length; i++){
			IVs[i] = 31;
		}

		EVs[0] = hpEV;
		EVs[1] = atkEV;
		EVs[2] = defEV;
		EVs[3] = spAEV;
		EVs[4] = spDEV;
		EVs[5] = speEV;


		calculateStats();
		currentStats = stats;

		setMoves(inMoves);
	}
    //pokemon state in battle
	public Pokemon(String nam, String[] t, String nat, int lv, int hp, int atk, int def, int spA, int spD, int spe, int hpIV, int atkIV, int defIV, int spAIV, int spDIV, int speIV, int hpEV, int atkEV, int defEV, int spAEV, int spDEV, int speEV){

		name = nam;
		type = t;
		nature = nat;
		level = lv;

		baseStats[0] = hp;
		baseStats[1] = atk;
		baseStats[2] = def;
		baseStats[3] = spA;
		baseStats[4] = spD;
		baseStats[5] = spe;

		IVs[0] = hpIV;
		IVs[1] = atkIV;
		IVs[2] = defIV;
		IVs[3] = spAIV;
		IVs[4] = spDIV;
		IVs[5] = speIV;

		EVs[0] = hpEV;
		EVs[1] = atkEV;
		EVs[2] = defEV;
		EVs[3] = spAEV;
		EVs[4] = spDEV;
		EVs[5] = speEV;


		calculateStats();
		currentStats = stats;


		state = 1;
	}

	public void calculateStats(){

		stats[0] = (int) (((2 * baseStats[0] + IVs[0] + ((double) EVs[0]/4)) * level)/100) + level + 10;

		for(int i = 1; i < stats.length; i++){
			stats[i] = (int) ((((2 * baseStats[i] + IVs[i] + ((double) EVs[i]/4)) * level)/100) + 5) * natureVal;
		}

		calculateNature();

	}

	public void calculateNature(){

		try {
			JSONParser parser = new JSONParser();
			JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("Data/nature.txt"));

			String jsonNature = nature.toLowerCase();
			String plus = (String) ((JSONObject) jsonObject.get(jsonNature)).get("plus");
			String minus = (String) ((JSONObject) jsonObject.get(jsonNature)).get("minus");

			if(plus.equals("atk")){setAtk((int)(getAtk() * 1.1));}
			if(plus.equals("def")){setDef((int)(getDef() * 1.1));}
			if(plus.equals("spa")){setSpA((int)(getSpA() * 1.1));}
			if(plus.equals("spd")){setSpD((int)(getSpD() * 1.1));}
			if(plus.equals("spe")){setSpe((int)(getSpe() * 1.1));}

			if(minus.equals("atk")){setAtk((int)(getAtk() * 0.9));}
			if(minus.equals("def")){setDef((int)(getDef() * 0.9));}
			if(minus.equals("spa")){setSpA((int)(getSpA() * 0.9));}
			if(minus.equals("spd")){setSpD((int)(getSpD() * 0.9));}
			if(minus.equals("spe")){setSpe((int)(getSpe() * 0.9));}



		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    //getter and setter
	public String getName(){
		return name;
	}

	public String[] getType(){
		return type;
	}

	public String getNature(){
		return nature;
	}

	public int getLevel(){
		return level;
	}

	public int getHP(){
		return stats[0];
	}

	public int getAtk(){
		return stats[1];
	}

	public int getDef(){
		return stats[2];
	}

	public int getSpA(){
		return stats[3];
	}

	public int getSpD(){
		return stats[4];
	}

	public int getSpe(){
		return stats[5];
	}

	public void getSpe(int inSpe){
		stats[5] = inSpe;
	}

	public void setHP(int inHP){
		stats[0] = inHP;
	}

	public void setAtk(int inAtk){
		stats[1] = inAtk;
	}

	public void setDef(int inDef){
		stats[2] = inDef;
	}

	public void setSpA(int inSpA){
		stats[3] = inSpA;
	}

	public void setSpD(int inSpD){
		stats[4] = inSpD;
	}

	public void setSpe(int inSpe){
		stats[5] = inSpe;
	}

	public int getCurrentHP(){
		return currentStats[0];
	}

	public int getCurrentAtk(){
		return currentStats[1];
	}

	public int getCurrentDef(){
		return currentStats[2];
	}

	public int getCurrentSpA(){
		return currentStats[3];
	}

	public int getCurrentSpD(){
		return currentStats[4];
	}

	public int getCurrentSpe(){
		return currentStats[5];
	}

	public void setCurrentHP(int currHP){
		currentStats[0] = Math.max(0, currHP); //prevents from getting lower than 0
	}

	public void setCurrentAtk(int currAtk){
		currentStats[1] = currAtk;
	}
	public void setCurrentDef(int currDef){
		currentStats[1] = currDef;
	}
	public void setCurrentSpA(int currSpA){
		currentStats[1] = currSpA;
	}
	public void setCurrentSpD(int currSpD){
		currentStats[1] = currSpD;
	}
	public void setCurrentSpe(int currSpe){
		currentStats[1] = currSpe;
	}

	public void boostAtk(int stage){
		setCurrentAtk(getCurrentAtk()*stage);
	}


	public void setStrMoves(String[] inMoves){

		strMoves = inMoves;
	}

	public void setMoves(String[] inMoves){

		for(int i = 0; i < moves.length; i++){
			moves[i] = new Move(inMoves[i]);
		}
	}

	public Move getMove(int num){

		return moves[num];

	}
    
    //troSting()
	public String movesToString(){

		String movesStr = "";
		for(int i = 0; i < moves.length; i++){
			movesStr = movesStr + " (" + i + ") " + moves[i].getName();
		}

		return movesStr;

	}

	public int getState(){
		return state;
	}

	public void checkState(){

		if(getCurrentHP() == 0){
			System.out.println(name + " has fainted!");
			state = 0;
		}else{
			state = 1;
		}

	}

	public void statsToString(){

		for(int i : stats){
		System.out.print(i + " ");}
		System.out.println();
	}
}

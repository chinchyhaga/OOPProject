package Code;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Move {

	private String name;            //name of move
	private String type;            //type of move-use for calculate the effectiveness of moves
	private String category;        //physical or special
	private int basePower;          //power without any boost 
	private int pp;                 //time to use moves
	//private int accuracyInt;        //the accuracy to land debuff
	//private boolean accuracyBool;   //to test whether it can land debuff or not
	private String desc;            //description 

	private String jsonName;

	public Move(String inName){
        //begin to read move file
		try {
			JSONParser parser = new JSONParser();
			JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("Data/move.txt")); //File is store in JSON format

			name = inName;
			jsonName = name.toLowerCase();
			jsonName = jsonName.replace("-","").replace("[","").replace("]","").replace(" ","");

			type = (String) ((JSONObject) jsonObject.get(jsonName)).get("type");
			category = (String) ((JSONObject) jsonObject.get(jsonName)).get("category");
			basePower = (int) (long) ((JSONObject) jsonObject.get(jsonName)).get("basePower");
			pp = (int) (long) ((JSONObject) jsonObject.get(jsonName)).get("pp");
			desc = (String) ((JSONObject) jsonObject.get(jsonName)).get("desc");


		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Move does not exist");
		}
	}

    //getter
	public String getName(){
		return name;
	}
	public String getType(){
		return type;
	}
	public String getCategory(){
		return category;
	}
	public int getBasePower(){
		return basePower;
	}
	public int getPP(){
		return pp;
	}
	public String getDesc(){
		return desc;
    }
    
    //toString()
	public String toString(){
		String str = name + " " + type + " " + category + " " + basePower + " " + pp + " " + desc;
		return str;
	}

}

package Code;

import java.util.ArrayList;

public class Team {

	private ArrayList<Pokemon> team;

	public Team(){
		team = new ArrayList<Pokemon>();
	}

	public void addPokemon(Pokemon p){

		if(team.size() < 6){
			team.add(p);
		}else {
			System.out.println("Full party of 6!");
		}
	}

	public Pokemon getPokemon(int i){

		return team.get(i);
	}

	public int nonFaintedTeam(){

		int nonFainted = 0;
		for(Pokemon p : team){
			if(p.getState() == 1){
				nonFainted++;
			}
		}
		return nonFainted;

	}

	public boolean isFaintedTeam(){

		boolean isFainted = true;

		for(Pokemon p : team){
			if(p.getState() == 1){
				isFainted = false;
			}
		}
		return isFainted;
	}

	public String toString(){

		String str = "";

		for(int i = 0; i < team.size(); i++){
			str = str + " (" + i + ") " + team.get(i).getName() + " ";
		}
		return str;
	}


}

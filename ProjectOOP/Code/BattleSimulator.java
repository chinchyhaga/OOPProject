package Code;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class BattleSimulator {

	public void runSimulator(){

		System.out.println("Welcome to the 2016 Pokemon Battle Simulator!!!");
		displayArt();
		System.out.println("Play or not? (Type \"1\" for play, \"2\" for not)");

		boolean isGoodInput = false;
		Scanner sc = new Scanner(System.in);
		int in = 0;

		while(!isGoodInput){ //get input, loop until input is good
			try {
				in = Integer.valueOf(sc.nextLine());
				isGoodInput = true;
				if(in != 1 && in != 2){
					isGoodInput = false;
				}
			} catch(Exception e) {
				System.out.println("Sorry, bad input. Make sure you are typing in a number.");
			}
		}

		if(in == 1){
			Battle b = new Battle();
			b.startBattle();
			b.battle();
		}
	}

	public void displayArt(){

		try {
			BufferedReader reader = new BufferedReader(new FileReader("Data/art.txt"));
			String currLine = null;

			System.out.println();

			while((currLine = reader.readLine()) != null){
				System.out.println(currLine);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}




}


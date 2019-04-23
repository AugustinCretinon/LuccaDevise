package com.lucca.devise;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MyFile {
	
	public InitProperty initProperty = new InitProperty();;
	public ArrayList<ChangeRate> tabChangeRate = new ArrayList<ChangeRate>();
	
	public void readFile (String filePath) throws IOException {
		try {
			Scanner scn = new Scanner(new File (filePath));
			String[] initPropertyTemp = new String[3];
			int nbChangeRate = 0;
			// on s'assure que la ligne existe bien
			if (scn.hasNextLine()) {
				// on lit la ligne puis on crée un tableau de chaine
				// on supprime les espaces superflues et on met tout en majuscule
				initPropertyTemp = scn.nextLine().trim().toUpperCase().split(";");
			}
			//on remplis notre objet
			System.out.println(initPropertyTemp[1]);
			initProperty.setsInitCurrency(initPropertyTemp[0]);
			initProperty.setsInitAmount(initPropertyTemp[1]);
			initProperty.setsGoalCurrency(initPropertyTemp[2]);
			
			if(scn.hasNextLine()) {
				nbChangeRate = scn.nextInt();
				// clearer le buffer et etre sur qu'au prochain nextline() il y ait bien une valeur
				scn.nextLine();
			}
			for (int i = 0; i <nbChangeRate; i++) {
				String [] changeRateTemp = new String[3];
				ChangeRate changeRate = new ChangeRate();
				// on supprime les espaces superflues et on met tout en majuscule
				changeRateTemp = scn.nextLine().trim().toUpperCase().split(";");
				//on remplis les objets
				changeRate.setsInitCurrency(changeRateTemp[0]);
				changeRate.setsEndCurrency(changeRateTemp[1]);
				changeRate.setsChangeRate(changeRateTemp[2]);
				this.tabChangeRate.add(changeRate);
			}
			scn.close();
		}
		catch (IOException e) {
			System.out.println(e);
		}
	}
}

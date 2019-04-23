package com.lucca.devise;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadFile {
	
	public void luccaDevises (String filePath) throws IOException {
		try {
			Scanner scn = new Scanner(new File (filePath));
			String [] initAmount = new String [3];
			String [] changeRate = new String [3];
			int nbChangeRate = 0;
			// on s'assure que la ligne existe bien
			if (scn.hasNextLine()) {
				// on lit la ligne puis on crée un tableau de chaine
				// on supprime les espaces superflues et on met tout en majuscule
				 initAmount = scn.nextLine().trim().toUpperCase().split(";");
			}
			if(scn.hasNextLine()) {
				nbChangeRate = scn.nextInt();
				// clearer le buffer et etre sur qu'au prochain nextline() il y ait bien une valeur
				scn.nextLine();
			}
			// on cree une liste de tableaux de chaine pour avoir tous les taux de change
			ArrayList<String[]> strTabChangeRate = new ArrayList<String[]>();
			for (int i = 0; i <nbChangeRate; i++) {
				// on supprime les espaces superflues et on met tout en majuscule
				changeRate = scn.nextLine().trim().toUpperCase().split(";");
				strTabChangeRate.add(changeRate);
			}
			scn.close();
		}
		catch (IOException e) {
			System.out.println(e);
		}
	}
}

package com.lucca.devise;

import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Calculation {
	public double findAmount(String[] initAmount, ArrayList<String[]> strTabChangeRate) {
	DecimalFormat df = new DecimalFormat("#.####");
	df.setRoundingMode(RoundingMode.HALF_UP);
	double dReverseRate = 0.0;
	String strBufferValue = "";
	String strBufferFormat = "";
		for(int i = 0; i<strTabChangeRate.size(); i++) {
			// on parcours la liste des taux de change et on regarde si notre devise de départ y est présente
			if (initAmount[0].equals(strTabChangeRate.get(i)[0]) || initAmount[0].equals(strTabChangeRate.get(i)[1])){
				// si notre devise de départ correspond à la devise de fin du taux de change on prend l'inverse du taux de change
				if(initAmount[0].equals(strTabChangeRate.get(i)[1])) {
					strBufferValue = strTabChangeRate.get(i)[1];
					strTabChangeRate.get(i)[1]= strTabChangeRate.get(i)[0];
					strTabChangeRate.get(i)[0] = strBufferValue;
					// calcul de l'inverse du taux de change
					dReverseRate = 1/(Double.parseDouble(strTabChangeRate.get(i)[2]));
					// ensuite on le met au bon format
					strBufferFormat = df.format(dReverseRate).replace(",",".");
					strTabChangeRate.get(i)[2] = strBufferFormat;
				}
				// on regarde si on a le taux de change qu'il nous faut cad taux de change de départ voulu et taux de change de fin voulu
				if (initAmount[2].equals(strTabChangeRate.get(i)[1])){
					double finalValue= Double.valueOf(initAmount[1])*Double.valueOf(strTabChangeRate.get(i)[2]);
					return finalValue;					
				}
				else {
					// on applique le taux de change
					double newAmount = Double.valueOf(initAmount[1])*Double.valueOf(strTabChangeRate.get(i)[2]);
					// on met le nouveau montant dans le bon format
					strBufferFormat = df.format(newAmount).replace(",",".");
					// on cree le nouveau tableau correspondant aux nouvelles valeurs de départ
					String[] newInitAmount = {strTabChangeRate.get(i)[1],strBufferFormat,initAmount[2]};
					// on supprime ce cas de figure puisqu'on va le traiter
					strTabChangeRate.remove(i);
					// on relance la procedure afin de savoir si avec cette devise de départ on peut faire qqchose
					double possibleResult = findAmount(newInitAmount,strTabChangeRate);
					if(possibleResult>0){
						return possibleResult;
					}
				}
			}
		}
		return -1;
	}
	
	public void luccaDevises (String filePath) throws IOException {
		int iResult = 0;
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
			iResult = (int) findAmount(initAmount,strTabChangeRate);
			if(iResult>0) {
				System.out.println(iResult);
			}
			else {
				System.out.println("Aucune conversion possible");
			}
			scn.close();
		}
		catch (IOException e) {
			System.out.println(e);
		}
	}
}

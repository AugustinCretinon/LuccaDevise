package com.lucca.devise;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Calculation {
	public double findAmount(MyFile myFile) {
	DecimalFormat df = new DecimalFormat("#.####");
	df.setRoundingMode(RoundingMode.HALF_UP);
	double dReverseRate = 0.0;
	String strBufferValue = "";
	String strBufferFormat = "";
		for(int i = 0; i<myFile.tabChangeRate.size(); i++) {
			// on parcours la liste des taux de change et on regarde si notre devise de départ y est présente
			if (myFile.initProperty.getsInitCurrency().equals(myFile.tabChangeRate.get(i).getsInitCurrency()) || myFile.initProperty.getsInitCurrency().equals(myFile.tabChangeRate.get(i).getsEndCurrency())){
				// si notre devise de départ correspond à la devise de fin du taux de change on prend l'inverse du taux de change
				if(myFile.initProperty.getsInitCurrency().equals(myFile.tabChangeRate.get(i).getsEndCurrency())) {
					strBufferValue = myFile.tabChangeRate.get(i).getsEndCurrency();
					myFile.tabChangeRate.get(i).setsEndCurrency(myFile.tabChangeRate.get(i).getsInitCurrency());
					myFile.tabChangeRate.get(i).setsInitCurrency(strBufferValue);
					// calcul de l'inverse du taux de change
					dReverseRate = 1/(Double.parseDouble(myFile.tabChangeRate.get(i).getsChangeRate()));
					// ensuite on le met au bon format
					strBufferFormat = df.format(dReverseRate).replace(",",".");
					myFile.tabChangeRate.get(i).setsChangeRate(strBufferFormat);
				}
				// on regarde si on a le taux de change qu'il nous faut cad taux de change de départ voulu et taux de change de fin voulu
				System.out.println(i + " " + myFile.tabChangeRate.get(0).getsEndCurrency() + " " + myFile.initProperty.getsGoalCurrency());
				if (myFile.initProperty.getsGoalCurrency().equals(myFile.tabChangeRate.get(i).getsEndCurrency())){
					double finalValue= Double.valueOf(myFile.initProperty.getsInitAmount())*Double.valueOf(myFile.tabChangeRate.get(i).getsChangeRate());
					return finalValue;					
				}
				else {
					// on applique le taux de change
					double newAmount = Double.valueOf(myFile.initProperty.getsInitAmount())*Double.valueOf(myFile.tabChangeRate.get(i).getsChangeRate());
					// on met le nouveau montant dans le bon format
					strBufferFormat = df.format(newAmount).replace(",",".");
					// on cree le nouvel objet correspondant aux nouvelles valeurs de départ
					MyFile newStartingFile = new MyFile();
					newStartingFile.initProperty.setsInitCurrency(myFile.tabChangeRate.get(i).getsEndCurrency());
					newStartingFile.initProperty.setsInitAmount(strBufferFormat);
					newStartingFile.initProperty.setsGoalCurrency(myFile.initProperty.getsGoalCurrency());
					newStartingFile.tabChangeRate = myFile.tabChangeRate;
					// on supprime ce cas de figure puisqu'on va le traiter
					newStartingFile.tabChangeRate.remove(i);
					// on relance la procedure afin de savoir si avec cette devise de départ on peut faire qqchose
					double possibleResult = findAmount(newStartingFile);
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
		MyFile myFile = new MyFile();
		myFile.readFile(filePath);
		iResult = (int) findAmount(myFile);
		if(iResult>0) {
			System.out.println(iResult);
		}
		else {
			System.out.println("Aucune conversion possible");
		}
	}
}

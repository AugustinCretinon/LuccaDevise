package com.lucca.devise;

import java.io.IOException;
import java.util.*;

public class Solution {
	public static void main(String[] args) throws IOException {
		ReadFile readFile = new ReadFile();
		Scanner scn = new Scanner(System.in);
		// on demande à l'utilisateur d'entrer le chemin de son fichier
		System.out.print("Enter your file path: ");
		String filePath = scn.nextLine();
		readFile.luccaDevises(filePath);
		scn.close();
	}
}

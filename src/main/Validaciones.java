package main;

import java.util.Scanner;

public class Validaciones {
	
	Scanner leer;
	
	public Validaciones() {
		leer = new Scanner(System.in);
	}
	
	public String getString(String titulo) {
		String s1 = "";
		
		while(s1.equals("")) {
			System.out.println(titulo);
			s1 = leer.nextLine();
		}
		
		return s1;
	}
	
	public int getInt(String titulo) {
		String s1 = "";
		
		while(s1.equals("")) {
			System.out.println(titulo);
			s1 = leer.nextLine();
		}
		
		return Integer.parseInt(s1);
	}
	
}

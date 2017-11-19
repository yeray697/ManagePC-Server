package com.ncatz.yeray697.managepc.utils;

import java.util.Random;

public class Utils {
	
	public static String generatePassword() {
    	String pass = "";
    	Random rand = new Random();
        int PASS_LENGTH = 5;
        int MIN_NUMBER_PASS = 0;
        int MAX_NUMBER_PASS = 9;
        for (int i = 0; i < PASS_LENGTH; i++) {
            pass += String.valueOf(rand.nextInt((MAX_NUMBER_PASS - MIN_NUMBER_PASS) + 1) + MIN_NUMBER_PASS);
		}
    	return pass;
    }
}

package com.example.xmlScientificPublicationEditor.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class MyFileReader {

	public static String readFile(String fileName) throws Exception {
		File file = new File(fileName);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String result = "";
		String st;
		while ((st = br.readLine()) != null)
			result += st;
		br.close();
		return result;
	}

}

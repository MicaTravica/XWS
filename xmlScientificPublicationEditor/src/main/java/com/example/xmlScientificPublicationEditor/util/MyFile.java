package com.example.xmlScientificPublicationEditor.util;

import java.io.*;

import org.springframework.web.multipart.MultipartFile;

public class MyFile {

	public static String readFile(MultipartFile file) throws Exception {
		BufferedReader br;
		StringBuilder sb = new StringBuilder();
		try {
			String line;
			InputStream is = file.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
		} catch (IOException e) {
			throw new Exception(e.getMessage());
		}
		return sb.toString();
	}

	public static String readFile(String filePath) throws Exception {
		File file = new File(filePath);
		BufferedReader br = new BufferedReader(new FileReader(file));
		StringBuilder sb = new StringBuilder();
		try {
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
		} catch (IOException e) {
			throw new Exception(e.getMessage());
		}
		return sb.toString();
	}
}

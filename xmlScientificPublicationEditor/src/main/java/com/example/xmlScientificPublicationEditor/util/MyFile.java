package com.example.xmlScientificPublicationEditor.util;

import java.io.*;

import org.exist.xmldb.EXistResource;
import org.springframework.web.multipart.MultipartFile;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

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
	
	public static String resourceSetToString(ResourceSet resultSet) throws XMLDBException {
		String retVal = "";
		if (resultSet == null || (resultSet.getSize() == 0 )) {
			return retVal;
		}
		ResourceIterator i = resultSet.getIterator();
		XMLResource res = null;
		while (i.hasMoreResources()) {
			try {
				res = (XMLResource) i.nextResource();
				retVal += res.getContent().toString();
			} finally {
				// don't forget to cleanup resources
				try {
					if(res != null) {
						((EXistResource) res).freeResources();
					}
				} catch (XMLDBException xe) {
					xe.printStackTrace();
				}
			}
		}
		return retVal;
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

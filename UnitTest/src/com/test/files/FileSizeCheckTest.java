package com.test.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSizeCheckTest {
	public static void main(String[] args) throws Exception {
		Path p=Paths.get("D:\\\\TOOL\\\\Notepad++\\\\change.log");
		File f= p.toFile();
		System.out.println(Files.size(p));

		System.out.println(f.length());
		
	}
}

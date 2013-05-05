package client;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.amazonaws.services.sqs.AmazonSQS;

public class TaskLoader {
	
	public static String[] loadfromFile(String workloadfile){
		
		String[] tasks = null;
		
		try {
			BufferedReader file = new BufferedReader(new FileReader(workloadfile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Goes through a file and processes each line to be formatted as a task
		
		return tasks;
		
	}

}

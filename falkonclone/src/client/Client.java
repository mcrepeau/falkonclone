package client;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


import com.amazonaws.services.sqs.AmazonSQS;


public class Client {
	
	static boolean verbose = false;
	
	public static void main(String[] args) throws FileNotFoundException {

		String workloadfile = args[0];
		ArrayList<String> tasks = new ArrayList<String>();

		AmazonSQS sqs = QueueManager.initSQS(); //Initializes the queues and stuff from SQS
		tasks = TaskLoader.loadfromFile(workloadfile); //Loads tasks into the queue
		System.out.println(tasks.size() + " tasks loaded from the workload file");
		for (int i = 0; i < tasks.size(); i++){
			QueueManager.sendToQueue(tasks.get(i), sqs, QueueManager.SubmittedTasksQueue); //Loads every task in the file into the queue
		}
		
		while(true){
			System.out.println("\nPress Enter to get the status of the queues\n");
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(System.in);
		    sc.nextLine();
			TaskMonitor.displayTasksStatus(sqs, verbose); //Displays the status of the tasks
			//sc.close();
		}

	}
	

}

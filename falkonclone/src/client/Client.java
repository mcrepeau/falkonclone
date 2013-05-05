package client;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.CreateQueueRequest;

public class Client {
	
	static boolean verbose = false;
	
	public static void main(String[] args) {

		String workloadfile = args[2];
		String[] tasks = null;
		
		AmazonSQS sqs = QueueManager.initSQS(); //Initializes the queues and stuff from SQS
		tasks = TaskLoader.loadfromFile(workloadfile); //Loads tasks into the queue
		for (int i = 0; i == tasks.length; i++){
			QueueManager.sendToQueue(tasks[i], sqs, QueueManager.SubmittedTasksQueue); //Loads every task in the file into the queue
		}
		
		while(true){
			TaskMonitor.displayTasksStatus(sqs, verbose); //Displays the status of the tasks
		}

	}
	

}

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

	static String SubmittedTasksQueue;
	static String InprocessTaskQueue;
	static String CompletedTasksQueue;
	
	static boolean verbose = false;
	
public static AmazonSQS initSQS(){
		
		//Connects to Amazon AWS
		AmazonSQS sqs = new AmazonSQSClient(new ClasspathPropertiesFileCredentialsProvider());
		Region usWest2 = Region.getRegion(Regions.US_WEST_2);
		sqs.setRegion(usWest2);

		try {
			// Creates the queues (something should be added in case the queue already exists

			System.out.println("Creating a new queue for submitted tasks\n");
			CreateQueueRequest createQueueRequest_submitted = new CreateQueueRequest("SubmittedTasks");
			SubmittedTasksQueue = sqs.createQueue(createQueueRequest_submitted).getQueueUrl();

			System.out.println("Creating a new queue for tasks being processed\n");
			CreateQueueRequest createQueueRequest_inprocess = new CreateQueueRequest("InprocessTask");
			InprocessTaskQueue = sqs.createQueue(createQueueRequest_inprocess).getQueueUrl();

			System.out.println("Creating a new queue for completed tasks\n");
			CreateQueueRequest createQueueRequest_completed = new CreateQueueRequest("CompletedTasks");
			CompletedTasksQueue = sqs.createQueue(createQueueRequest_completed).getQueueUrl();


		} catch (AmazonServiceException ase) {
			System.out.println("Caught an AmazonServiceException, which means your request made it " +
					"to Amazon SQS, but was rejected with an error response for some reason.");
			System.out.println("Error Message:    " + ase.getMessage());
			System.out.println("HTTP Status Code: " + ase.getStatusCode());
			System.out.println("AWS Error Code:   " + ase.getErrorCode());
			System.out.println("Error Type:       " + ase.getErrorType());
			System.out.println("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			System.out.println("Caught an AmazonClientException, which means the client encountered " +
					"a serious internal problem while trying to communicate with SQS, such as not " +
					"being able to access the network.");
			System.out.println("Error Message: " + ace.getMessage());
		}
		return sqs;
		
	}
	
	public static void main(String[] args) {

		String workloadfile = args[2];
		String[] tasks = null;
		
		AmazonSQS sqs = initSQS(); //Initializes the queues and stuff from SQS
		tasks = TaskLoader.loadfromFile(workloadfile); //Loads tasks into the queue
		for (int i = 0; i == tasks.length; i++){
			TaskLoader.sendtoqueue(tasks[i], sqs); //Loads every task in the file into the queue
		}
		
		while(true){
			TaskMonitor.displayTasksStatus(sqs, verbose); //Displays the status of the tasks
		}

	}
	

}

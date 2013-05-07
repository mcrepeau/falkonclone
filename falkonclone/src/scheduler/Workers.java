package scheduler;

import java.io.IOException;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;

public class Workers {
	
	static int LOCAL_WORKERS_MAX = 5; //Number of workers to be instanciated by the Scheduler to process tasks
	static int REMOTE_WORKERS_MAX = 32;
	static int NB_REMOTE_WORKERS = 0;
	
	public static boolean checkifAvailable(){
		boolean areAvailable = true;
			
		//Checks if the worker is available or not
		
		return areAvailable;
	}
	
	public static String getAvailableWorker(){
		String nextAvailableWorker = new String();
			
		
		
		return nextAvailableWorker;
	}
	
	public static void createWorker(){
		
		//Create the worker on Amazon EC2
		Workers.NB_REMOTE_WORKERS++;
	}
	
	public static void work(AmazonSQS sqs, Message message){
		System.out.println("Task received by the worker\n");
		
		try {

		    /*Process child =*/ Runtime.getRuntime().exec(message.getBody());
		   
		    
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		QueueManager.removeTaskfromQueue(sqs, QueueManager.InprocessTasksQueue, message);//Remove task from submitted queue
		QueueManager.putTaskinQueue(sqs, QueueManager.CompletedTasksQueue, message);//Put task in completed queue
		System.out.println("Task completed\n");
	}
	
}

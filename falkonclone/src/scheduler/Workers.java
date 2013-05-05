package scheduler;

import com.amazonaws.services.sqs.AmazonSQS;

public class Workers {
	
	int N_LOCAL_WORKERS = 5; //Number of workers to be instanciated by the Scheduler to process tasks

	public static boolean checkifAvailable(){
		boolean areAvailable = true;
			
		//Checks if the message queue is empty or not
		
		return areAvailable;
	}
	
	public static void work (String task){
		
	}
	
}

package scheduler;

import com.amazonaws.services.sqs.AmazonSQS;

public class TaskPoller {
	
	public static boolean checkifEmpty(AmazonSQS sqs){
		boolean isEmpty = true;
			
		//Checks if the message queue is empty or not
		
		return isEmpty;
	}
	
	public static String getNextTask(AmazonSQS sqs){
		
		String task = null;
		
			//We return the next task in the queue
		
		return task;
	}

}

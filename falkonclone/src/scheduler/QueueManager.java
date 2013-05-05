package scheduler;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;

public class QueueManager {
	
	public static AmazonSQS initSQS(){
			
			//Connects to Amazon AWS
			AmazonSQS sqs = new AmazonSQSClient(new ClasspathPropertiesFileCredentialsProvider());
			Region usWest2 = Region.getRegion(Regions.US_WEST_2);
			sqs.setRegion(usWest2);	
			return sqs;
			
	}
	
	public static boolean checkifTaskWaiting(AmazonSQS sqs){
		boolean isEmpty = true;
			
		//Checks if the message queue is empty or not
		
		return isEmpty;
	}
	
	public static String getNextTask(AmazonSQS sqs){
		
		String task = null;
		
			//We return the next task in the queue
		
		return task;
	}
	
	public static void removeTaskfromQueue(AmazonSQS sqs, String queue){
		
	}
	
	public static void putTaskinQueue(AmazonSQS sqs, String queue){
		
	}

}

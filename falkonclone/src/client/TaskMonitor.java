package client;

import java.util.List;
import java.util.Map.Entry;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;

public class TaskMonitor {

	public static void displayTasksStatus(AmazonSQS sqs, boolean verbose){
		
		int num_submittedTasks = 0;
		int num_inprocessTasks = 0;
		int num_completedTasks = 0;
		
		// Lists the submitted tasks
        System.out.println("Listing the submitted tasks...\n");
        ReceiveMessageRequest receiveMessageRequest_submitted = new ReceiveMessageRequest(QueueManager.SubmittedTasksQueue);
        List<Message> SubmittedTasks = sqs.receiveMessage(receiveMessageRequest_submitted).getMessages();
        for (Message message : SubmittedTasks) {
            
        	if (verbose){
	        	System.out.println("  Message");
	            System.out.println("    MessageId:     " + message.getMessageId());
	            System.out.println("    ReceiptHandle: " + message.getReceiptHandle());
	            System.out.println("    MD5OfBody:     " + message.getMD5OfBody());
	            System.out.println("    Body:          " + message.getBody());
	            for (Entry<String, String> entry : message.getAttributes().entrySet()) {
	                System.out.println("  Attribute");
	                System.out.println("    Name:  " + entry.getKey());
	                System.out.println("    Value: " + entry.getValue());
	            }
        	}
        	num_submittedTasks++;
        }
        System.out.println("\nNumber of submitted tasks : " + num_submittedTasks);
        
     // Lists the tasks being processed
        System.out.println("Listing the tasks being processed...\n");
        ReceiveMessageRequest receiveMessageRequest_inprocess = new ReceiveMessageRequest(QueueManager.InprocessTaskQueue);
        List<Message> InprocessTasks = sqs.receiveMessage(receiveMessageRequest_inprocess).getMessages();
        for (Message message : InprocessTasks) {
            
        	if(verbose){
	        	System.out.println("  Message");
	            System.out.println("    MessageId:     " + message.getMessageId());
	            System.out.println("    ReceiptHandle: " + message.getReceiptHandle());
	            System.out.println("    MD5OfBody:     " + message.getMD5OfBody());
	            System.out.println("    Body:          " + message.getBody());
	            for (Entry<String, String> entry : message.getAttributes().entrySet()) {
	                System.out.println("  Attribute");
	                System.out.println("    Name:  " + entry.getKey());
	                System.out.println("    Value: " + entry.getValue());
	            }
        	}
        	num_inprocessTasks++;
        }
        System.out.println("\nNumber of tasks being processed : " + num_inprocessTasks);
        
     // Lists the completed tasks
        System.out.println("Listing the completed tasks...\n");
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(QueueManager.CompletedTasksQueue);
        List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();
        for (Message message : messages) {
            
        	if(verbose){
	        	System.out.println("  Message");
	            System.out.println("    MessageId:     " + message.getMessageId());
	            System.out.println("    ReceiptHandle: " + message.getReceiptHandle());
	            System.out.println("    MD5OfBody:     " + message.getMD5OfBody());
	            System.out.println("    Body:          " + message.getBody());
	            for (Entry<String, String> entry : message.getAttributes().entrySet()) {
	                System.out.println("  Attribute");
	                System.out.println("    Name:  " + entry.getKey());
	                System.out.println("    Value: " + entry.getValue());
	            }
        	}
        	num_completedTasks++;
        }
        System.out.println("\nNumber of completed tasks : " + num_completedTasks);
	}

}

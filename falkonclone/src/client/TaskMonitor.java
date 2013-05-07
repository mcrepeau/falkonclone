package client;

import java.util.List;
import java.util.Map.Entry;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;

public class TaskMonitor {

	public static void displayTasksStatus(AmazonSQS sqs, boolean verbose){
		
		// Lists the submitted tasks
        System.out.println("Listing the submitted tasks...\n");
        ReceiveMessageRequest receiveMessageRequest_submitted = new ReceiveMessageRequest(QueueManager.SubmittedTasksQueue);
        List<Message> SubmittedTasks = sqs.receiveMessage(receiveMessageRequest_submitted).getMessages();
        System.out.println(SubmittedTasks.size() + " task(s) in the queue\n");
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
        	System.out.println();
        }
        
     // Lists the tasks being processed
        System.out.println("Listing the tasks being processed...\n");
        ReceiveMessageRequest receiveMessageRequest_inprocess = new ReceiveMessageRequest(QueueManager.InprocessTasksQueue);
        List<Message> InprocessTasks = sqs.receiveMessage(receiveMessageRequest_inprocess).getMessages();
        System.out.println(InprocessTasks.size() + " task(s) in the queue\n");
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
        	System.out.println();
        }
        
     // Lists the completed tasks
        System.out.println("Listing the completed tasks...\n");
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(QueueManager.CompletedTasksQueue);
        List<Message> CompletedTasks = sqs.receiveMessage(receiveMessageRequest).getMessages();
        System.out.println(CompletedTasks.size() + " task(s) in the queue\n");
        for (Message message : CompletedTasks) {
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
        }
        System.out.println();
	}
}

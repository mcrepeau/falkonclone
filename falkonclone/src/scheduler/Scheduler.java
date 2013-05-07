package scheduler;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;

public class Scheduler {
	
	static boolean USE_REMOTE_WORKERS = false;
	static boolean USE_LOCAL_WORKERS = true;
	
	public static void main(String[] args) {
		
		boolean areWorkersAvailable;
		Message message;
		
		if (args[0] == "-rw"){
			USE_REMOTE_WORKERS = true;
			USE_LOCAL_WORKERS = false;
			Workers.REMOTE_WORKERS_MAX = Integer.parseInt(args[3]);
			System.out.println("Use of remote workers");
		}
		else if (args[0] == "-lw"){
			USE_REMOTE_WORKERS = false;
			USE_LOCAL_WORKERS = true;
			Workers.LOCAL_WORKERS_MAX = Integer.parseInt(args[3]);
			System.out.println("Use of local workers");
		}
		
		AmazonSQS sqs = QueueManager.initSQS(); //Initializes the queues and stuff from SQS
		
		while(true){
			while ((message = QueueManager.getNextMessage(sqs)) != null){
				if(USE_REMOTE_WORKERS){
					areWorkersAvailable = Workers.checkifAvailable();//Check workers
					if (areWorkersAvailable == false && (Workers.NB_REMOTE_WORKERS < Workers.REMOTE_WORKERS_MAX)){
						Workers.createWorker(); //Create worker and wait for creation	
					}
					QueueManager.removeTaskfromQueue(sqs, QueueManager.SubmittedTasksQueue, message);//Remove task from submitted queue
					QueueManager.putTaskinQueue(sqs, QueueManager.InprocessTasksQueue, message);//Put task in inprocess queue
					TaskSender.sendTask(Workers.getAvailableWorker(), message.getBody());//Send task
					System.out.println("Task sent to the worker");
				}
				if(USE_LOCAL_WORKERS){
					QueueManager.removeTaskfromQueue(sqs, QueueManager.SubmittedTasksQueue, message);//Remove task from submitted queue
					QueueManager.putTaskinQueue(sqs, QueueManager.InprocessTasksQueue, message);//Put task in inprocess queue
					Workers.work(sqs,message); //Fetches message and instantiate worker with task
					
				}	
			}
		}
	}
}

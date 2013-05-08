package scheduler;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import worker.Sleep;

public class Scheduler {

	/**
	 * @param args
	 */
	static boolean USE_REMOTE_WORKERS = false;
	static boolean USE_LOCAL_WORKERS = true;
	static int LOCAL_WORKERS_MAX = 5; //Number of workers to be instanciated by the Scheduler to process tasks
	static int REMOTE_WORKERS_MAX = 32;
	static int NB_REMOTE_WORKERS = 0;
	static public SpotRequests openrequests;
	
	public static void main(String[] args) throws Exception {
		// TODO 
		//Receives task from TaskReceiver 
		//Processes the task
		//Updates the state of the task
		SpotRequests r = new SpotRequests();
		
		boolean areWorkersAvailable;
		Message message;

		if (args[0].equals("-rw")){
			USE_REMOTE_WORKERS = true;
			USE_LOCAL_WORKERS = false;
			REMOTE_WORKERS_MAX = Integer.parseInt(args[1]);
			
			System.out.println("Use of remote workers");
		}
		else if (args[0].equals("-lw")){
			USE_REMOTE_WORKERS = false;
			USE_LOCAL_WORKERS = true;
			LOCAL_WORKERS_MAX = Integer.parseInt(args[1]);
				
			System.out.println("Use of local workers");
		}
		
		AmazonSQS sqs = QueueManager.initSQS(); //Initializes the queues and stuff from SQS
		
		
			while ((message = QueueManager.getNextMessage(sqs)) != null){
				if(USE_REMOTE_WORKERS){
					areWorkersAvailable = r.areAnyOpen();//Check workers
					System.out.println(areWorkersAvailable);
					if (areWorkersAvailable == false && (NB_REMOTE_WORKERS < REMOTE_WORKERS_MAX)){						
						//Create worker and wait for creation						
						r.submitRequests();
						openrequests = r;
						NB_REMOTE_WORKERS++; 	
					}
					
				}
				if(USE_LOCAL_WORKERS){
					
					System.out.println("Task received by the worker\n");
					
					Thread t = new Thread();	
					Runnable task = new Sleep(message.getBody(), t);
					task.run();

					QueueManager.removeTaskfromQueue(sqs, QueueManager.InprocessTasksQueue, message);//Remove task from submitted queue
					QueueManager.putTaskinQueue(sqs, QueueManager.CompletedTasksQueue, message);//Put task in completed queue
					System.out.println("Task completed\n");

				}	
			
		}
	}

	

}

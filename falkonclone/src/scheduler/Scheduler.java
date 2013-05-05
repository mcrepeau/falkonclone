package scheduler;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;

public class Scheduler {
	
	static boolean USE_REMOTE_WORKERS = false;
	static boolean USE_LOCAL_WORKERS = true;
	static int REMOTE_WORKERS_MAX = 32;
	static int LOCAL_WORKERS_MAX = 5;
	static int NB_REMOTE_WORKERS = 0;

	public static void main(String[] args) {
		
		boolean areSubmittedTasksWaiting;
		boolean areWorkersAvailable;
		
		if (args[2] == "-rw"){
			USE_REMOTE_WORKERS = true;
			USE_LOCAL_WORKERS = false;
			REMOTE_WORKERS_MAX = Integer.parseInt(args[3]);
		}
		else if (args[2] == "-lw"){
			USE_REMOTE_WORKERS = false;
			USE_LOCAL_WORKERS = true;
			LOCAL_WORKERS_MAX = Integer.parseInt(args[3]);
		}
		
		AmazonSQS sqs = QueueManager.initSQS(); //Initializes the queues and stuff from SQS
		
		while(true){
			areSubmittedTasksWaiting = QueueManager.checkifTaskWaiting(sqs); //Checks queue
			while (areSubmittedTasksWaiting == false){
				areWorkersAvailable = Workers.checkifAvailable();//Check workers
				if (areWorkersAvailable){
					//Send task to first worker available
					//Remove task from submitted queue
					//Put task in inprocess queue
				}
				if(USE_REMOTE_WORKERS){
					if (NB_REMOTE_WORKERS < REMOTE_WORKERS_MAX){
						//Create worker and wait for creation
						
						NB_REMOTE_WORKERS++;
						//Send task
					}
					else {
						//Send task to first worker available
					}
					//Send task
					//Remove task from submitted queue
					//Put task in inprocess queue
				}
				if(USE_LOCAL_WORKERS){
					Workers.work(QueueManager.getNextTask(sqs)); //Fetches task and instanciate worker with task
					QueueManager.removeTaskfromQueue(sqs, "");//Remove task from submitted queue
					QueueManager.putTaskinQueue(sqs, "");//Put task in inprocess queue
				}	
			}
		}
	}
}

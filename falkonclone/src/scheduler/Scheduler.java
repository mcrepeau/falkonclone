package scheduler;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;

public class Scheduler {

public static AmazonSQS initSQS(){
		
		//Connects to Amazon AWS
		AmazonSQS sqs = new AmazonSQSClient(new ClasspathPropertiesFileCredentialsProvider());
		Region usWest2 = Region.getRegion(Regions.US_WEST_2);
		sqs.setRegion(usWest2);
		
		return sqs;
		
}
	
	public static void main(String[] args) {
		

	}

}

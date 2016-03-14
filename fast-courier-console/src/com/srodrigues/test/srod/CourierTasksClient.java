package com.srodrigues.test.srod;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import com.srodrigues.srod.ClientLocator;
import com.srodrigues.srod.layer.java.mqtt.MQTTInitialContext;
import com.srodrigues.srod.layer.marshalling.json.JSONMarshalling;
import com.srodrigues.test.Constants;
import com.srodrigues.test.srod.CourierTasks;

public class CourierTasksClient {
	
	public static void print(List<String> list){
		for (String task : list) {
			System.out.println(task );
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		
		//Magic begins here
		ClientLocator.addContext( new MQTTInitialContext(Constants.BROKER, Constants.NAMESPACE, new JSONMarshalling() ) ) ; 
		final CourierTasks courier = ClientLocator.getService(Constants.SERVICE, CourierTasks.class);
		//Some Magic has been done
		
		List<String> list = courier.get();
		print(list);
		
		courier.add( "batatas");
		list = courier.get();
		print(list);
		
		courier.add("bananas");
		list = courier.get();
		print(list);
		
		for (int i = 0; i < 50; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					courier.add("tbananas" + this.hashCode());
					try {
						Thread.sleep((int)(new Random().nextDouble()*100 ) );
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					List<String> list = courier.get();
					print(list);
				}
			}).run();
		}
		
		list = courier.get();
		print(list);

		
	}
}

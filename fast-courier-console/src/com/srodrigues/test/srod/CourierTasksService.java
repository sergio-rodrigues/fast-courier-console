package com.srodrigues.test.srod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.srodrigues.srod.ServiceProvider;
import com.srodrigues.srod.layer.java.mqtt.MQTTInitialContext;
import com.srodrigues.srod.layer.marshalling.json.JSONMarshalling;
import com.srodrigues.test.Constants;
import com.srodrigues.test.srod.CourierTasks;

public class CourierTasksService implements CourierTasks {
	
	private static final long serialVersionUID = 1L;

	private List<String> tasks = new ArrayList<String>();

	@Override
	public boolean add(String task) {
		System.err.println("add: " + task);
		return tasks.add(task);
	}

	@Override
	public boolean delete(String task) {
		System.err.println("remove: " + task);
		return tasks.remove(task);
	}

	@Override
	public List<String> get() {
		return tasks;
	}
	
	
	public static void main(String[] args) throws IOException {
		ServiceProvider.register(Constants.SERVICE, new CourierTasksService());
		new MQTTInitialContext(Constants.BROKER, Constants.NAMESPACE, new JSONMarshalling() ).getProtocol().startServer(Constants.SERVICE);		
	}

}

package dev.geminileft.TEGameEngine;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

public class TEGameObject extends TEManagerComponent {
	private HashMap<String, Vector<TEComponent>> mEventSubscribers = new HashMap<String, Vector<TEComponent>>(); 
	
	public Size size;
	public Point position;
	
	public TEGameObject() {
		super();
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub	
	}
	
	@Override
	public void addComponent(TEComponent component) {
		super.addComponent(component);
		Set<String> keys = component.getEventSubscriptions();
		Iterator<String> iterator = keys.iterator();
		while (iterator.hasNext()) {
			String event = iterator.next();
			Vector<TEComponent> subscribers = mEventSubscribers.get(event);
			if (subscribers == null) {
				subscribers = new Vector<TEComponent>();
			}
			subscribers.add(component);
			mEventSubscribers.put(event, subscribers);
		}
		component.setParent(this);
	}

	public void invokeEvent(String event) {
		Vector<TEComponent> subscribers = mEventSubscribers.get(event);
		if (subscribers != null) {
			Iterator<TEComponent> iterator = subscribers.iterator();
			while (iterator.hasNext()) {
				TEComponent component = iterator.next();
				component.eventNotification(event);
			}
		}
	}
}

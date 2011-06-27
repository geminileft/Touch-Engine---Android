package dev.geminileft.TEGameEngine;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

public abstract class TEComponent {
	public interface EventListener {
		public abstract void invoke();
	}

	private TEGameObject mParent;
	private HashMap<String, EventListener> mEventSubscriptions = new HashMap<String, EventListener>();
	private Vector<String> mEventNotifications = new Vector<String>();

	public void update() {
		Iterator<String> iterator = mEventNotifications.iterator();
		while (iterator.hasNext()) {
			String event = iterator.next();
			EventListener listener = mEventSubscriptions.get(event);
			listener.invoke();
		}
		mEventNotifications.clear();
	}
	
	public final void setParent(TEGameObject parent) {
		mParent = parent;
	}
	
	public final TEGameObject getParent() {
		return mParent;
	}
	
	public final void addEventSubscription(String event, EventListener listener) {
		mEventSubscriptions.put(event, listener);
	}
	
	public final Set<String> getEventSubscriptions() {
		return mEventSubscriptions.keySet();
	}
	
	public final void eventNotification(String event) {
		mEventNotifications.add(event);
	}
}

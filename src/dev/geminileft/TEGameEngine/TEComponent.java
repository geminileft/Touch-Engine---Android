package dev.geminileft.TEGameEngine;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

public abstract class TEComponent {
	public interface EventListener {
		public abstract void invoke();
	}
	
	public static enum Event {
		EVENT_TOUCH_STARTED
		, EVENT_TOUCH_REJECT
		, EVENT_TOUCH_ACCEPT
		, EVENT_TOUCH_ENDED
		, EVENT_MOVE_TO_TOP
		, EVENT_ACCEPT_MOVE
		, EVENT_REJECT_MOVE
	}
	
	private TEGameObject mParent;
	private TEManagerComponent mManager;
	
	private HashMap<TEComponent.Event, EventListener> mEventSubscriptions = new HashMap<TEComponent.Event, EventListener>();
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
	
	public final void setManager(TEManagerComponent manager) {
		mManager = manager;
	}
	
	public final TEGameObject getParent() {
		return mParent;
	}
	
	public final TEManagerComponent getManager() {
		return mManager;
	}
	
	public final void addEventSubscription(TEComponent.Event event, EventListener listener) {
		mEventSubscriptions.put(event, listener);
	}
	
	public final HashMap<TEComponent.Event, EventListener> getEventSubscriptions() {
		return mEventSubscriptions;
	}
	
	public final void eventNotification(String event) {
		mEventNotifications.add(event);
	}
}

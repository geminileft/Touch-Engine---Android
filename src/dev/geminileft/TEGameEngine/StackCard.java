package dev.geminileft.TEGameEngine;

public class StackCard extends TEComponentStack {
	private boolean mMoving = false;
	private Point mPreviousPosition;
	private TEComponentStack mPreviousStack;
	
	private TEComponent.EventListener mTouchStartedListener = new TEComponent.EventListener() {
		
		@Override
		public void invoke() {
			boolean isGoodStack = true;
			TEComponentStack component = StackCard.this;
			int cardCount = 1;
			TEComponentStack childStack = component.getChildStack();
			if (childStack != null) {
				PlayingCard oldCard = component.getPlayingCard();
				while (childStack != null) {
					PlayingCard card = childStack.getPlayingCard();
					++cardCount;
					if (!oldCard.canStack(card)) {
						isGoodStack = false;
						break;
					}
					oldCard = card;
					childStack = childStack.getChildStack();
				}
			}
			if (isGoodStack && (cardCount <= getPickupCount(TEComponentStack.openFreeCellCount, TEComponentStack.openTableCellCount))) {
				getParent().invokeEvent(Event.EVENT_TOUCH_ACCEPT);
				mMoving = true;
				TEGameObject parent = component.getParent();
				mPreviousPosition = new Point(parent.position.x, parent.position.y);
				mPreviousStack = component.getParentStack();
				if (mPreviousStack != null) {
					mPreviousStack.popStack(component);				
				}
				parent.invokeEvent(Event.EVENT_MOVE_TO_TOP);
				childStack = component.getChildStack();
				while (childStack != null) {
					childStack.getParent().invokeEvent(Event.EVENT_MOVE_TO_TOP);
					childStack = childStack.getChildStack();
				}
			} else {
				getParent().invokeEvent(Event.EVENT_TOUCH_REJECT);
			}
		}
	};
	
	private TEComponent.EventListener mTouchEndedListener = new TEComponent.EventListener() {
		
		@Override
		public void invoke() {
			mMoving = false;
			StackCard.this.evaluate();
		}
	};
	
	private TEComponent.EventListener mRejectMoveListener = new TEComponent.EventListener() {
		
		@Override
		public void invoke() {
			StackCard component = StackCard.this;
			if (mPreviousStack != null) {
				mPreviousStack.pushStack(component);
			} else {
				component.getParent().position = new Point(mPreviousPosition.x, mPreviousPosition.y);
				adjustStackPositions();
			}
		}
	};
	
	public StackCard(PlayingCard card) {
		super(StackType.Card);
		setPlayingCard(card);
		addEventSubscription(Event.EVENT_TOUCH_STARTED, mTouchStartedListener);
		addEventSubscription(Event.EVENT_TOUCH_ENDED, mTouchEndedListener);
		addEventSubscription(Event.EVENT_REJECT_MOVE, mRejectMoveListener);
	}
	
	@Override
	public void update() {
		super.update();
		if (mMoving) {
			adjustStackPositions();			
		}
	}
	
	@Override
	public final int getStackOffset(boolean isFirst) {
		return CARD_OFFSET;
	}
	
	@Override
	public final boolean doesAccept(TEComponentStack stack) {
		return true;
	}
}

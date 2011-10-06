package dev.geminileft.TEGameEngine;

import java.util.LinkedList;

import android.util.Log;

public class TEUtilNode {
	private final static int MAX_INT = 2147483647;
	public static int GRID_SIZE = 64;
	private static TEUtilNode mEligibleNodes[] = new TEUtilNode[GRID_SIZE * GRID_SIZE];
	private static TEUtilNode mUsedNodes[] = new TEUtilNode[GRID_SIZE * GRID_SIZE];
	private static TEUtilNode mNodes[][] = new TEUtilNode[GRID_SIZE][GRID_SIZE];
	private static int mEligibleSize = 0;
	private static int mUsedSize = 0;
	private int mX;
	private int mY;
	private TEUtilNode mTop;
	private TEUtilNode mLeft;
	private TEUtilNode mRight;
	private TEUtilNode mBottom;
	public float mProbability;
	private boolean mEligible;
	public boolean mUsed;
	private int mEligibleIndex = -1;
	
	
	public TEUtilNode(int x, int y) {
		mX = x;
		mY = y;
		mEligible = false;
		mUsed = false;
	}
	
	public void calculateProbability() {
		mProbability = 0;
		if (!mUsed) {
			mProbability = mTop.mProbability
				+ mBottom.mProbability
				+ mLeft.mProbability
				+ mRight.mProbability;
			mProbability /= 4;
		}
	}
	
	public boolean canAdd() {
		return (!mEligible && !mUsed);
	}
	
	public void markEligible() {
		mEligible = true;
		mEligibleNodes[mEligibleSize] = this;
		mEligibleIndex = mEligibleSize;
		++mEligibleSize;
	}
	
	public void setSiblings(TEUtilNode left, TEUtilNode right, TEUtilNode top, TEUtilNode bottom) {
		mLeft = left;
		mRight = right;
		mTop = top;
		mBottom = bottom;
	}
	
	static public void addEligibleNode(TEUtilNode node) {
		mEligibleNodes[mEligibleSize] = node;
		node.mEligible = true;
		++mEligibleSize;
	}
	
	static public void reset() {
		for (int y = 0;y < GRID_SIZE;++y) {
			for (int x = 0; x < GRID_SIZE;++x) {
				mNodes[x][y] = new TEUtilNode(x,y);
			}
		}
		
		TEUtilNode zeroNode = new TEUtilNode(-1, -1);
		zeroNode.mProbability = 0;
		zeroNode.mUsed = true;
		TEUtilNode oneNode = new TEUtilNode(-1, -1);
		oneNode.mProbability = 1;
		oneNode.mUsed = true;
		
		//far corners
		mNodes[0][0].setSiblings(zeroNode, mNodes[0][1], mNodes[1][0], zeroNode);
		mNodes[0][GRID_SIZE-1].setSiblings(mNodes[0][GRID_SIZE-2], zeroNode, mNodes[1][GRID_SIZE-1], zeroNode);
		mNodes[GRID_SIZE-1][0].setSiblings(zeroNode, mNodes[GRID_SIZE-1][1], zeroNode, mNodes[GRID_SIZE-2][0]);
		mNodes[GRID_SIZE-1][GRID_SIZE-1].setSiblings(mNodes[GRID_SIZE-1][GRID_SIZE-2], zeroNode, zeroNode, mNodes[GRID_SIZE-2][GRID_SIZE-1]);
		
		//inner edges
		final int maxSize = GRID_SIZE - 1;
		for (int i = 1;i < maxSize;++i) {
			mNodes[0][i].setSiblings(mNodes[0][i-1], mNodes[0][i+1], mNodes[2][i], oneNode);
			mNodes[GRID_SIZE-1][i].setSiblings(mNodes[GRID_SIZE-1][i-1], mNodes[GRID_SIZE-1][i+1], zeroNode, mNodes[GRID_SIZE-2][i]);
			mNodes[i][0].setSiblings(zeroNode, mNodes[i][1], mNodes[i+1][0], mNodes[i-1][0]);
			mNodes[i][GRID_SIZE-1].setSiblings(mNodes[i][GRID_SIZE-1], zeroNode, mNodes[i+1][GRID_SIZE-1], mNodes[i-1][GRID_SIZE-1]);
		}
		
		for (int y = 1;y < maxSize;y++) {
			for (int x = 1;x < maxSize;x++) {
				mNodes[y][x].setSiblings(mNodes[y][x-1], mNodes[y][x+1], mNodes[y+1][x], mNodes[y-1][x]);
			}
		}	

		mNodes[GRID_SIZE-1][GRID_SIZE / 2].useNode();
		mNodes[GRID_SIZE-2][GRID_SIZE / 2].useNode();
		mNodes[GRID_SIZE-3][GRID_SIZE / 2].useNode();
		
	}
	
	public final void useNode() {
		TEUtilNode node = mNodes[mY][mX];
		node.mUsed = true;
		mUsedNodes[mUsedSize] = node;
		++mUsedSize;
		LinkedList<TEUtilNode> list = node.getEligibleSiblings();
		final int size = list.size();
		for (int i = 0;i < size;++i) {
			TEUtilNode sibling = list.get(i);
			sibling.markEligible();
		}
		if (node.mEligibleIndex != -1) {
			Log.v("here", "her");
		}
	}
	
	public final LinkedList<TEUtilNode> getEligibleSiblings() {
		LinkedList<TEUtilNode> list = new LinkedList<TEUtilNode>();
		if (mLeft.canAdd())
			list.add(mLeft);
		if (mRight.canAdd())
			list.add(mRight);
		if (mTop.canAdd())
			list.add(mTop);
		if (mBottom.canAdd())
			list.add(mBottom);
		return list;
	}
	
	public static final TEPoint simulateGrowthStep() {
		TEPoint point = TEPoint.make(0, 0);
		for (int y = 0;y < GRID_SIZE;++y) {
			for (int x = 0;x < GRID_SIZE;++x) {
				mNodes[y][x].calculateProbability();
			}
		}
		TERandomizer rand = new TERandomizer(TEManagerTime.currentTime());
		int next = rand.next();
		long nextFixed = (long)next + MAX_INT;
		final long scale = (long)MAX_INT * 2 + 1;
		float limitScale = (float)nextFixed / (float)scale;
		
		float totalEligible = 0;
		for (int i = 0;i < mEligibleSize;++i) {
			TEUtilNode node = mEligibleNodes[i];
			totalEligible += node.mProbability;
		}
		
		int selectedIndex = 0;
		if (totalEligible > 0) {
			for (int i = 0;i < mEligibleSize;++i) {
				limitScale -= mEligibleNodes[i].mProbability;
				if (limitScale <= 0 || i + 1 == mEligibleSize) {
					selectedIndex = i;
				}
			}
		} else {
			selectedIndex = (int)(nextFixed % mEligibleSize);
			if (selectedIndex + 1 != mEligibleSize) {
				--mEligibleSize;
				mEligibleNodes[selectedIndex] = mEligibleNodes[mEligibleSize];
				selectedIndex = mEligibleSize;
			}
		}
		mEligibleNodes[selectedIndex].useNode();
		point = TEPoint.make(mEligibleNodes[selectedIndex].mX, mEligibleNodes[selectedIndex].mY);

		return point;
	}
}

package dev.geminileft.TEGameEngine;

public class StackTableCell extends TEComponentStack {

	public StackTableCell(StackType stackType) {
		super(stackType);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getStackOffset() {
		// TODO Auto-generated method stub
		return 40;
	}

	@Override
	public boolean doesAccept(TEComponentStack stack) {
		// TODO Auto-generated method stub
		return true;
	}

}

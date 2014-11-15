package Inventory;

import businesslogic.inventorybl.LossList;

public class MockLossList extends LossList{
	private String ID;
	private MockCommodity item;
	private int lossNum;
	
	public MockLossList(String ID, MockCommodity item, int lossNum){
		this.ID = ID;
		this.item = item;
		this.lossNum = lossNum;
	}
	
	public String getID(){
		return ID;
	}
	
	public MockCommodity item(){
		return item;
	}
	
	public int alarmNum(){
		return lossNum;
	}
}
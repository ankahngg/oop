package com.badlogic.drop;

public class MapUserData {
	static public String CrMap;
	static public int CrStage = 0;
	static public int CrStagePass = -1;
	
	static public void saveData(int x, int y) {
		CrStage = x;
		CrStagePass = y;
	}
	
	static public void clearData() {
		CrStage = 0;
		CrStagePass = -1;
	}
}

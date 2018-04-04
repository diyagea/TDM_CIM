package com.model;

public class ToolGroup {
	private String drawing;
	private String name;
	private String toolGroupID;
	
	public String getDrawing() {
		return drawing;
	}
	public void setDrawing(String drawing) {
		this.drawing = drawing;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getToolGroupID() {
		return toolGroupID;
	}
	public void setToolGroupID(String toolGroupID) {
		this.toolGroupID = toolGroupID;
	}
	
	@Override
	public String toString() {
		return "ToolGroup [drawing=" + drawing + ", name=" + name + ", toolGroupID=" + toolGroupID + "]";
	}
	
}

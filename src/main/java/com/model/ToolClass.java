package com.model;

import java.util.List;

public class ToolClass {

	private String drawing;
	private String name;
	private String toolClassID;
	
	private List<ToolGroup> toolGroup;
	
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
	public String getToolClassID() {
		return toolClassID;
	}
	public void setToolClassID(String toolClassID) {
		this.toolClassID = toolClassID;
	}
	@Override
	public String toString() {
		return "ToolClass [drawing=" + drawing + ", name=" + name + ", toolClassID=" + toolClassID + "]";
	}
	public List<ToolGroup> getToolGroup() {
		return toolGroup;
	}
	public void setToolGroup(List<ToolGroup> toolGroup) {
		this.toolGroup = toolGroup;
	}

}

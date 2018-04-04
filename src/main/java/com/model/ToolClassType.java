package com.model;

import java.util.List;

public class ToolClassType {

	private String drawing;
	private String name;
	private String toolClassType;
	
	private List<ToolClass> toolClass;
	
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
	public String getToolClassType() {
		return toolClassType;
	}
	public void setToolClassType(String toolClassType) {
		this.toolClassType = toolClassType;
	}
	
	@Override
	public String toString() {
		return "ToolClassType [drawing=" + drawing + ", name=" + name + ", toolClassType=" + toolClassType + "]";
	}
	public List<ToolClass> getToolClass() {
		return toolClass;
	}
	public void setToolClass(List<ToolClass> toolClass) {
		this.toolClass = toolClass;
	}
	
}

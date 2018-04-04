package com.model;

import java.util.List;

public class Nodes {
	private String text;
	private String href;
	private String tags;
	private List<Nodes> nodes;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public List<Nodes> getNodes() {
		return nodes;
	}
	public void setNodes(List<Nodes> nodes) {
		this.nodes = nodes;
	}
	@Override
	public String toString() {
		return "Nodes [text=" + text + ", href=" + href + ", tags=" + tags + ", nodes=" + nodes + "]";
	}
	
}

package com.ywc.notification.pincodes.dto;

public class SearchColumn {

	private String columnKey;
	private String columnValue;

	public SearchColumn() {
	}

	public SearchColumn(String columnKey, String columnValue) {
		super();
		this.columnKey = columnKey;
		this.columnValue = columnValue;
	}

	public String getColumnKey() {
		return columnKey;
	}

	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}

	public String getColumnValue() {
		return columnValue;
	}

	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}

}

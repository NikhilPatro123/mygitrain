package com.ywc.notification.pincodes.constants;

public enum CustomSearchColumns {

	zone("zone"), city("city"), state("state");

	private String columnName;

	CustomSearchColumns(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnName() {
		return columnName;
	}

}

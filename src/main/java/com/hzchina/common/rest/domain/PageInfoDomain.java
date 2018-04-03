package com.hzchina.common.rest.domain;

public class PageInfoDomain {
	public int total;     // 总数量
	public int pageIndex; // 当前页数
	public int pageSize;  // 每页显示数量
	
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}

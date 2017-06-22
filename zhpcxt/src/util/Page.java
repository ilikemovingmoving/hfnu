package util;

public class Page {
	
	private int pageIndex;//当前页,默认为1
	private int pageSize;//每页显示的条数，默认为3
	private int totalCount;//总记录数
	private int totalPage;//总页数
	
	public Page(){
		this(1, 15, 0, 1);
	}
	
	public Page(int pageIndex, int pageSize, int totalCount, int totalPage) {
		super();
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		this.totalPage = totalPage;
	}
	//计算得到总页数
	public int getTotalPage() {
		totalPage=totalCount%pageSize==0?totalCount/pageSize:
					totalCount/pageSize+1;
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
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
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	
	

}

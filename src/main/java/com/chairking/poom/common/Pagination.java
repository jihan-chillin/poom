package com.chairking.poom.common;

public class Pagination {
	/** 1. 페이지 당 보여지는 게시글의 최대 개수 **/
	private int numPerpage = 5;

	/** 2. 페이징된 버튼의 블럭당 최대 개수 **/
	private int pageBarSize = 5;

	/** 3. 현재 페이지 **/
	private int cPage = 1;

	/** 4. 현재 블럭 **/
	private int block = 1;

	/** 5. 총 게시글 수 **/
	private int totalData;

	/** 6. 총 페이지 수 **/
	private int totalPage;

	/** 7. 총 블럭 수 **/
	private int totalBlockCnt;

	/** 8. 블럭 시작 페이지 **/
	private int startPage = 1;

	/** 9. 블럭 마지막 페이지 **/
	private int endPage = 1;

	/** 10. DB 접근 시작 index **/
	private int startIndex = 0;

	/** 11. 이전 블럭의 마지막 페이지 **/
	private int prevBlock;

	/** 12. 다음 블럭의 시작 페이지 **/
	private int nextBlock;

	public int getnumPerpage() {
		return numPerpage;
	}

	public void setnumPerpage(int numPerpage) {
		this.numPerpage = numPerpage;
	}

	public int getpageBarSize() {
		return pageBarSize;
	}

	public void setpageBarSize(int pageBarSize) {
		this.pageBarSize = pageBarSize;
	}

	public int getPage() {
		return cPage;
	}

	public void setPage(int cPage) {
		this.cPage = cPage;
	}

	public int getBlock() {
		return block;
	}

	public void setBlock(int block) {
		this.block = block;
	}

	public int gettotalData() {
		return totalData;
	}

	public void settotalData(int totalData) {
		this.totalData = totalData;
	}

	public int gettotalPage() {
		return totalPage;
	}

	public void settotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalBlockCnt() {
		return totalBlockCnt;
	}

	public void setTotalBlockCnt(int totalBlockCnt) {
		this.totalBlockCnt = totalBlockCnt;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getPrevBlock() {
		return prevBlock;
	}

	public void setPrevBlock(int prevBlock) {
		this.prevBlock = prevBlock;
	}

	public int getNextBlock() {
		return nextBlock;
	}

	public void setNextBlock(int nextBlock) {
		this.nextBlock = nextBlock;
	}

	public Pagination(int totalData, int cPage) {

		// 총 게시물 수와 현재 페이지를 Controller로 부터 받아온다.

		// 총 게시물 수	- totalData
		// 현재 페이지	- cPage
		
		
		/** 3. 현재 페이지 **/
		setPage(cPage);
		
        
		/** 5. 총 게시글 수 **/
		settotalData(totalData);


		/** 6. 총 페이지 수 **/
		// 한 페이지의 최대 개수를 총 게시물 수 * 1.0로 나누어주고 올림 해준다.
		// 총 페이지 수 를 구할 수 있다.
		settotalPage((int) Math.ceil(totalData * 1.0 / numPerpage));


		/** 7. 총 블럭 수 **/
		// 한 블럭의 최대 개수를 총  페이지의 수 * 1.0로 나누어주고 올림 해준다.
		// 총 블럭 수를 구할 수 있다.
		setTotalBlockCnt((int) Math.ceil(totalPage * 1.0 / pageBarSize));
		
        
		/** 4. 현재 블럭 **/
		// 현재 페이지 * 1.0을 블록의 최대 개수로 나누어주고 올림한다.
		// 현재 블록을 구할 수 있다.
		setBlock((int) Math.ceil((cPage * 1.0)/pageBarSize)); 


		/** 8. 블럭 시작 페이지 **/
		setStartPage((block - 1) * pageBarSize + 1);
		
        
		/** 9. 블럭 마지막 페이지 **/
		setEndPage(startPage + pageBarSize - 1);
		
        
		/* === 블럭 마지막 페이지에 대한 validation ===*/
		if(endPage > totalPage){this.endPage = totalPage;}
		
        
		/** 11. 이전 블럭(클릭 시, 이전 블럭 마지막 페이지) **/
		setPrevBlock((block * pageBarSize) - pageBarSize);


		/* === 이전 블럭에 대한 validation === */
		if(prevBlock < 1) {this.prevBlock = 1;}


		/** 12. 다음 블럭(클릭 시, 다음 블럭 첫번째 페이지) **/
		setNextBlock((block * pageBarSize) + 1);
		
        
		/* === 다음 블럭에 대한 validation ===*/
		if(nextBlock > totalPage) {nextBlock = totalPage;}
		
        
		/** 10. DB 접근 시작 index **/
		setStartIndex((cPage-1) * numPerpage);
	
	}
	
}

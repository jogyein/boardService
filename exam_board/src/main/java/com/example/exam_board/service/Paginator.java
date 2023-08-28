package com.example.exam_board.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Paginator {

    // 생성
    private Integer pagesPerBlock;
    private Integer postsPerPage;
    private Long totalPostCount;

    private Integer totalLastPageNum;

    public Paginator(Integer pagesPerBlock, Integer postsPerPage, Long totalPostCount) {
        this.pagesPerBlock = pagesPerBlock;
        this.postsPerPage = postsPerPage;
        this.totalPostCount = totalPostCount;

        this.setTotalLastPageNum();
    }

    public Integer getPagesPerBlock() {
        return pagesPerBlock;
    }

    public Integer getPostsPerPage() {
        return postsPerPage;
    }

    public Long getTotalPostCount() {
        return totalPostCount;
    }

    public Integer getTotalLastPageNum() {
        return this.totalLastPageNum;
    }

    public void setPagesPerBlock(Integer pagesPerBlock) {
        this.pagesPerBlock = pagesPerBlock;
    }

    public void setPostsPerPage(Integer postsPerPage) {
        this.postsPerPage = postsPerPage;
        this.setTotalLastPageNum();
    }

    public void setTotalPostCount(Long totalPostCount) {
        this.totalPostCount = totalPostCount;
        this.setTotalLastPageNum();
    }

    private void setTotalLastPageNum() {
        // 총 게시글 수를 기준으로 한 마지막 페이지 번호 계산
        // totalPostCount 가 0인 경우 1페이지로 끝냄
        if(totalPostCount == 0) {
            this.totalLastPageNum = 1;
        } else {
            this.totalLastPageNum = (int) (Math.ceil((double)totalPostCount / postsPerPage));
        }
    }
    private Map<String, Object> getBlock(Integer currentPageNum,
                                         Boolean isFixed) {

        if(pagesPerBlock % 2 == 0 && !isFixed) {
            throw new IllegalStateException("getElasticBlock: pagesPerBlock은 홀수만 가능합니다.");
        }

        if(currentPageNum > totalLastPageNum && totalPostCount != 0) {
            throw new IllegalStateException("currentPage가 총 페이지 개수(" + totalLastPageNum + ") 보다 큽니다.");
        }

        // 블럭의 첫번째, 마지막 페이지 번호 계산
        Integer blockLastPageNum = totalLastPageNum;
        Integer blockFirstPageNum = 1;

        // 글이 없는 경우, 1페이지 반환.
        if(isFixed) {

            Integer mod = totalLastPageNum % pagesPerBlock;
            if(totalLastPageNum - mod >= currentPageNum) {
                blockLastPageNum = (int) (Math.ceil((float)currentPageNum / pagesPerBlock) * pagesPerBlock);
                blockFirstPageNum = blockLastPageNum - (pagesPerBlock - 1);
            } else {
                blockFirstPageNum = (int) (Math.ceil((float)currentPageNum / pagesPerBlock) * pagesPerBlock)
                        - (pagesPerBlock - 1);
            }

            // assert blockLastPageNum % pagesPerBlock == 0;
            // assert (blockFirstPageNum - 1) % pagesPerBlock == 0;
        } else {
            // 블록의 한가운데 계산 (예: 5->2, 9->4)
            Integer mid = pagesPerBlock / 2;

            // 블럭의 첫번째, 마지막 페이지 번호 계산
            if(currentPageNum <= pagesPerBlock) {
                blockLastPageNum = pagesPerBlock;
            } else if(currentPageNum < totalLastPageNum - mid) {
                blockLastPageNum = currentPageNum + mid;
            }

            blockFirstPageNum = blockLastPageNum - (pagesPerBlock - 1);

            if(totalLastPageNum < pagesPerBlock) {
                blockLastPageNum = totalLastPageNum;
                blockFirstPageNum = 1;
            }
            // assert blockLastPageNum == currentPageNum + mid;
            // assert (blockFirstPageNum - 1) % pagesPerBlock == 0;
        }

        // 페이지 번호 할당
        List<Integer> pageList = new ArrayList<>();
        for(int i = 0, val = blockFirstPageNum; val <= blockLastPageNum; i++, val++) {
            pageList.add(i, val);
        }


        Map<String, Object> result = new HashMap<>();
        result.put("isPrevExist", (int)currentPageNum > (int)pagesPerBlock);
        result.put("isNextExist", blockLastPageNum != 1 ? (int)blockLastPageNum != (int)totalLastPageNum : false);
        result.put("totalLastPageNum", totalLastPageNum);
        result.put("blockLastPageNum", blockLastPageNum);
        result.put("blockFirstPageNum", blockFirstPageNum);
        result.put("currentPageNum", currentPageNum);
        result.put("totalPostCount", totalPostCount);
        result.put("pagesPerBlock", pagesPerBlock);
        result.put("postsPerPage", postsPerPage);
        result.put("pageList", pageList);

        return result;
    }

    public Map<String, Object> getElasticBlock(Integer currentPageNum) {
        return this.getBlock(currentPageNum, false);
    }

    public Map<String, Object> getFixedBlock(Integer currentPageNum) {
        return this.getBlock(currentPageNum, true);
    }



    public static void main(String[] args) throws Exception {
        final int PAGES_PER_BLOCK = 5;
        final int POST_PER_PAGE = 10;

        // 총 게시글 수
        long totalPostCount = 446;

        // 인스턴스 생성
        Paginator paginator = new Paginator(PAGES_PER_BLOCK, POST_PER_PAGE, totalPostCount);

        try {
            for(int i = 1; i <= paginator.getTotalLastPageNum(); i++) {
                System.out.println(paginator.getElasticBlock(i));
            }
        } catch (Exception e) {
            System.err.println(e);
        }

    }
}

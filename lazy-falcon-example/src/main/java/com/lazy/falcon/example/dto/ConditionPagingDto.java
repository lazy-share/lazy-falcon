package com.lazy.falcon.example.dto;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * <p></p>
 *
 * @author laizhiyuan
 * @date 2018/6/10.
 */
public class ConditionPagingDto implements Serializable {
    private static final long serialVersionUID = 955594564563L;

    private Conditions conditions;
    private Paging paging;

    public Conditions getConditions() {
        return conditions;
    }

    public void setConditions(Conditions conditions) {
        this.conditions = conditions;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public static class Paging implements Serializable{

        private static final long serialVersionUID = 99999999994564563L;

        private String sortType;
        private String sortField;
        private Integer pageSize;
        private Integer currentPage;

        public String getSortType() {
            return sortType;
        }

        public void setSortType(String sortType) {
            this.sortType = sortType;
        }

        public String getSortField() {
            return sortField;
        }

        public void setSortField(String sortField) {
            this.sortField = sortField;
        }

        public Integer getPageSize() {
            return pageSize;
        }

        public void setPageSize(Integer pageSize) {
            this.pageSize = pageSize;
        }

        public Integer getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(Integer currentPage) {
            this.currentPage = currentPage;
        }
    }

    public static class Conditions implements Serializable{
        private static final long serialVersionUID = 99999994564563L;
        private String loginName;
        private String mobile;

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }

}


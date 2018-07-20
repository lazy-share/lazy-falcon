package com.lazy.falcon.core.model.output;

import com.lazy.falcon.core.tools.SetsUtils;

import java.util.List;

/**
 * <p>
 *     侧边栏树模型
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/6/2.
 */
public class SidebarTreeModel {

    private String id;
    private String text;
    private String url;

    private List<SidebarTreeModel> children = SetsUtils.list();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<SidebarTreeModel> getChildren() {
        return children;
    }

    public void setChildren(List<SidebarTreeModel> children) {
        this.children = children;
    }
}

package my.fast.admin.framework.utils;

import java.io.Serializable;

/**
 * @Author: ChenQingSong
 * @Date: 2018/6/26 11:22
 * @Description:
 */
public class Ztree implements Serializable {
    private static final long serialVersionUID = 514830730541164841L;
    private String id;
    private String pId;
    private String name;
    private String open;

    // 树根节点
    public static Ztree treeRoot(){
        Ztree root = new Ztree();
        root.id = "0";
        root.pId = "-1";
        root.name = "顶级目录";
        root.open = "true";
        return root;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

}

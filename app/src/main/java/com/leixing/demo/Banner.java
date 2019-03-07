package com.leixing.demo;

/**
 * description : xxx
 *
 * @author : leixing
 * email : leixing1012@qq.com
 * @date : 2019/3/7 17:img_02
 */
public class Banner {
    private int coverResId;
    private boolean isSelect;

    public Banner(int coverResId) {
        this.coverResId = coverResId;
    }

    public int getCoverResId() {
        return coverResId;
    }

    public Banner setCoverResId(int coverResId) {
        this.coverResId = coverResId;
        return this;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public Banner setSelect(boolean select) {
        isSelect = select;
        return this;
    }
}

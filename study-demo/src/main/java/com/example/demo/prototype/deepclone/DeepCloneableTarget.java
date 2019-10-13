package com.example.demo.prototype.deepclone;

import java.io.Serializable;

/**
 * 重写clone方法来实现深拷贝
 */
public class DeepCloneableTarget implements Serializable, Cloneable {

    private String cloneName;
    private String cloneClass;

    public DeepCloneableTarget(String cloneName, String cloneClass) {
        this.cloneName = cloneName;
        this.cloneClass = cloneClass;
    }

    /**
     * 因为该类的属性都是String，因此我们这里使用默认的clone完成即可
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

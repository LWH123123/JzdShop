package com.jzd.jzshop.entity;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

public class AreaAddressEntity implements IPickerViewData {

    /**
     * text : 北京市
     * children : [{"text":"北京辖区","children":["东城区","西城区","崇文区","宣武区","朝阳区","丰台区","石景山区","海淀区","门头沟区","房山区","通州区","顺义区","昌平区","大兴区","怀柔区","平谷区"]},{"text":"北京辖县","children":["密云县","延庆县"]}]
     */

    private String text;
    private List<ChildrenBean> children;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<ChildrenBean> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenBean> children) {
        this.children = children;
    }

    @Override
    public String getPickerViewText() {
        return this.text;
    }

    public static class ChildrenBean {
        /**
         * text : 北京辖区
         * children : ["东城区","西城区","崇文区","宣武区","朝阳区","丰台区","石景山区","海淀区","门头沟区","房山区","通州区","顺义区","昌平区","大兴区","怀柔区","平谷区"]
         */

        private String text;
        private List<String> children;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public List<String> getChildren() {
            return children;
        }

        public void setChildren(List<String> children) {
            this.children = children;
        }
    }
}

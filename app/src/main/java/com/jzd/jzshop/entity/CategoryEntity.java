package com.jzd.jzshop.entity;

import java.io.Serializable;
import java.util.List;

public class CategoryEntity implements Serializable{
    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable {

        private String id;
        private String name;
        private String thumb;
        private String isrecommand;
        private List<ChildrenBean> children;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getIsrecommand() {
            return isrecommand;
        }

        public void setIsrecommand(String isrecommand) {
            this.isrecommand = isrecommand;
        }

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        public static class ChildrenBean implements Serializable{
            /**
             * id : ythLoC1rjHtCQfGAsDTqEn7rm8x2yotMw
             * name : 小米
             * thumb : http://test.gtt20.com/attachment/images/2/2019/10/l1p311oo6ffZjPuP7p4mpuC756UU7k.png
             * isrecommand : 0
             */

            private String id;
            private String name;
            private String thumb;
            private String isrecommand;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            public String getIsrecommand() {
                return isrecommand;
            }

            public void setIsrecommand(String isrecommand) {
                this.isrecommand = isrecommand;
            }
        }
    }
}

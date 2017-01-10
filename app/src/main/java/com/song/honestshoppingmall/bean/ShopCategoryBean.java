package com.song.honestshoppingmall.bean;

import java.util.List;

/**
 * @Creator Administrator
 * @CreatedDate 2017/1/9 11:51
 * @Description ${TODO}
 * @Updater $Author$
 * @UpdateTime $Date$
 * @UpdateDesc ${TODO}
 */

public class ShopCategoryBean extends BaseBean {


    /**
     * id : 1
     * isLeafNode : false
     * name : 妈妈专区
     * parentId : 0
     * pic : /images/category/ym.png
     * tag : 妈妈内衣  祛纹纤体
     */

    private List<CategoryBean> category;

    public List<CategoryBean> getCategory() {
        return category;
    }

    public void setCategory(List<CategoryBean> category) {
        this.category = category;
    }

    public static class CategoryBean {
        private int id;
        private boolean isLeafNode;
        private String name;
        private int parentId;
        private String pic;
        private String tag;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isIsLeafNode() {
            return isLeafNode;
        }

        public void setIsLeafNode(boolean isLeafNode) {
            this.isLeafNode = isLeafNode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        @Override
        public String toString() {
            return "CategoryBean{" +
                    "id=" + id +
                    ", isLeafNode=" + isLeafNode +
                    ", name='" + name + '\'' +
                    ", parentId=" + parentId +
                    ", pic='" + pic + '\'' +
                    ", tag='" + tag + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ShopCategoryBean{" +
                "category=" + category +
                '}';
    }
}

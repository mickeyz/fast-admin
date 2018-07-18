package my.fast.admin.framework.utils;

/**
 * @Author: ChenQingSong
 * @Date: 2018/6/26 10:52
 * @Description:
 */
public class GlobalConst {

    public enum MenuType{
        /**
         * 目录
         */
        CATALOG("0"),
        /**
         * 菜单
         */
        MENU("1"),
        /**
         * 按钮
         */
        BUTTON("2");

        private String value;

        private MenuType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}

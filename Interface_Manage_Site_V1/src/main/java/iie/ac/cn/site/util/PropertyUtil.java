package iie.ac.cn.site.util;

public class PropertyUtil {

    /**
     * 将驼峰式转换为下划线表示的形式
     *
     * @param property
     * @return
     */
    public static String toUnderline(String property) {
        if (property == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < property.length(); i++) {
            char c = property.charAt(i);

            boolean nextUpperCase = true;

            if (i < (property.length() - 1)) {
                nextUpperCase = Character.isUpperCase(property.charAt(i + 1));
            }

            if ((i >= 0) && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    if (i > 0)
                        sb.append("_");
                }
                upperCase = true;
            } else {
                upperCase = false;
            }

            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }
}

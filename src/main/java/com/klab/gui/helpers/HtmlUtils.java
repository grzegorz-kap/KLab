package com.klab.gui.helpers;

import org.apache.commons.lang3.StringEscapeUtils;

public class HtmlUtils {
    private HtmlUtils() {
    }

    public static String formatCode(String code) {
        return StringEscapeUtils.escapeHtml4(code)
                .replaceAll("[\t]", "&nbsp;&nbsp;&nbsp;")
                .replaceAll("[ ]", "&nbsp;");
    }
}

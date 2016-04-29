package com.klab.gui;

import org.apache.commons.lang3.StringEscapeUtils;

public class HtmlUtils {
    public static String formatCode(String code) {
        return StringEscapeUtils.escapeHtml4(code)
                .replaceAll("[\t]", "&nbsp;&nbsp;&nbsp;")
                .replaceAll("[ ]", "&nbsp;");
    }

    private HtmlUtils() {
    }
}

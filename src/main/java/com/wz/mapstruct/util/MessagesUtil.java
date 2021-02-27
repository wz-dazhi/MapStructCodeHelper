package com.wz.mapstruct.util;

import com.intellij.openapi.ui.Messages;

/**
 * @package: com.wz.com.wz.mapstruct.util
 * @className: MessagesUtil
 * @description:
 * @author: zhi
 * @date: 2021/2/18
 * @version: 1.0
 */
public final class MessagesUtil {
    private MessagesUtil() {
    }

    public static void showMessageDialog(String title, String message) {
        Messages.showMessageDialog(ProjectUtil.getCurrentProject(), message, title, Messages.getInformationIcon());
    }

    public static void showInfoMessage(String title, String message) {
        Messages.showInfoMessage(ProjectUtil.getCurrentProject(), message, title);
    }

    public static void showErrorDialog(String title, String message) {
        Messages.showErrorDialog(ProjectUtil.getCurrentProject(), message, title);
    }
}

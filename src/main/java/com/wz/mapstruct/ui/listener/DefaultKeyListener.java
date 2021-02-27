package com.wz.mapstruct.ui.listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @projectName: map-struct-code-helper
 * @package: com.wz.com.wz.mapstruct.ui.listener
 * @className: DefaultKeyListener
 * @description:
 * @author: zhi
 * @date: 2021/1/29
 * @version: 1.0
 */
@FunctionalInterface
public interface DefaultKeyListener extends KeyListener {
    @Override
    default void keyTyped(KeyEvent e) {
    }

    @Override
    default void keyPressed(KeyEvent e) {
    }

    @Override
    void keyReleased(KeyEvent e);
}

/*
*This package contains layout managers of mine
 */
package customlayouts;

import java.awt.*;
/**
 * Block Layout manager places components in the vertical direction. Every component occupy all possible width, and its height is minimum of PrefSize and MinSize(if set!).
 * The boolean parameter indicates, the last component should occupy all remain height of the container or not.
 * The int parameter vGap sets vertical gap between components in pixels.
 *
 * @version 1.0.0 28 Apr 2022
 * @author Alexander Kononov
 * */
public class BlockLayout implements LayoutManager {
    /**
     * Inner enum MODE defines two lexical constants of BlockLayout layout managers mode.
     * LAST_FILL_CLIENTS means that last component occupies all remain height of the container.
     * LAST_STRICT means that last component occupies the height it set to be.
     */
    public enum MODE {
        LAST_FILL_CLIENT,
        LAST_STRICT
    }

    /**
     * Field MODE indicates what mode of BlockLayout layout manager is set.
     * By default, the value is LAST_FILL_CLIENT.
     */
    private MODE mode = MODE.LAST_FILL_CLIENT;

    /**
     * Sets the mode of the BlockLayout layout manager.
     *
     * @param mode the value that indicates the mode of the BlockLayout manager
     */
    public void setMode(MODE mode) {
        this.mode = mode;
    }

    /**
     * Returns the current mode of the BlockLayout manager.
     *
     * @return current mode of the BlockLayout manager
     */
    public MODE getMode() {
        return this.mode;
    }

    /**
     * Field, the vertical gap between components of the container in pixels.
     */
    private int vGap = 0;

    /**
     * Sets the vertical gap in pixels. It cannot be less than 0.
     *
     * @param vGap vertical gap in pixels
     */
    public void setVGap(int vGap) {
        this.vGap = Math.max(vGap, 0);
    }

    /**
     * Returns the vertical gap in pixels.
     *
     * @return current vertical gap in pixels
     */
    public int getVGap() {
        return this.vGap;
    }

    /**
     * Constructor with 2 parameters
     *
     * @param vGap vertical gap in pixels
     * @param mode mode of the BlockLayout manager
     */
    public BlockLayout(MODE mode, int vGap) {
        this.setMode(mode);
        this.setVGap(vGap);
    }

    /**
     * Constructor with single mode parameter. By default, vertical gap is 0.
     *
     * @param mode mode of the BlockLayout manager
     */
    public BlockLayout(MODE mode) {
        this.setMode(mode);
    }

    /**
     * Constructor with single vertical gap parameter. By default, mode is LAST_FILL_CLIENT.
     *
     * @param vGap vertical gap in pixels
     */
    public BlockLayout(int vGap) {
        this.setVGap(vGap);
    }

    /**
     * Constructor without parameters. By default, vertical gap is 0, and mode is LAST_FILL_CLIENT
     */
    public BlockLayout() {
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
    }

    @Override
    public void removeLayoutComponent(Component comp) {
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        if (parent == null) return null;
        int width = 0, height = 0;
        Dimension prefSize;
        for (Component component : parent.getComponents()) {
            prefSize = component.getPreferredSize();
            width = Math.max(width, prefSize.width);
            height += prefSize.height;
        }
        int components = parent.getComponentCount();
        if (components > 0) {
            height += this.getVGap() * (components - 1);
        }
        return new Dimension(width, height);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return this.preferredLayoutSize(parent);
    }

    @Override
    public void layoutContainer(Container parent) {
        if (parent == null) return;

        Component component;
        int componentCount = parent.getComponentCount();
        int x, y, width, height;
        Dimension minSize, prefSize;

        if (componentCount > 0) {
            component = parent.getComponent(0);
            prefSize = component.getPreferredSize();
            minSize = (component.isMinimumSizeSet()) ? component.getMinimumSize() : prefSize;
            x = 0;
            y = 0;
            width = parent.getWidth();
            height = Math.max(prefSize.height, minSize.height);
            component.setBounds(x, y, width, height);
            for (int i = 1; i < componentCount - 1; i++) {
                component = parent.getComponent(i);
                prefSize = component.getPreferredSize();
                minSize = (component.isMinimumSizeSet()) ? component.getMinimumSize() : prefSize;
                y += height + this.getVGap();
                height = Math.max(prefSize.height, minSize.height);
                component.setBounds(x, y, width, height);
            }
            if (componentCount > 1) {
                component = parent.getComponent(componentCount - 1);
                prefSize = component.getPreferredSize();
                minSize = (component.isMinimumSizeSet()) ? component.getMinimumSize() : prefSize;
                y += height + this.getVGap();
                height = switch (this.getMode()) {
                    case LAST_FILL_CLIENT -> Math.max(parent.getHeight() - y, 0);
                    case LAST_STRICT -> Math.max(prefSize.height, minSize.height);
                };
                component.setBounds(x, y, width, height);
            }
        }
    }
}
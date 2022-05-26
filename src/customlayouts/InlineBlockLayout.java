/*
 *This package contains layout managers of mine
 */
package customlayouts;

import java.awt.*;

/**
 * Block Layout manager places components in the horizontal direction. Every component's width and height is minimum of PrefSize and MinSize(if set!).
 * The int parameter hGap sets horizontal gap between components in pixels.
 *
 * @version 1.0.0 28 Apr 2022
 * @author Alexander Kononov
 * */
public class InlineBlockLayout implements LayoutManager {
    /**
     * Field, the vertical gap between components of the container in pixels.
     */
    private int hGap = 0;

    /**
     * Sets the horizontal gap in pixels. It cannot be less than 0.
     *
     * @param hGap vertical gap in pixels
     */
    public void setHGap(int hGap) {
        this.hGap = Math.max(hGap, 0);
    }

    /**
     * Returns the horizontal gap in pixels.
     *
     * @return current horizontal gap in pixels
     */
    public int getHGap() {
        return this.hGap;
    }

    /**
     * Constructor with single vertical gap parameter.
     *
     * @param hGap horizontal gap in pixels
     */
    public InlineBlockLayout(int hGap) {
        this.setHGap(hGap);
    }

    /**
     * Constructor without parameters. By default, horizontal gap is 0.
     */
    public InlineBlockLayout() {
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
            width += prefSize.width;
            height = Math.max(height, prefSize.height);
        }

        int components = parent.getComponentCount();
        if (components > 0)
            width += this.getHGap() * (components - 1);
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
            width = Math.max(prefSize.width, minSize.width);
            height = parent.getSize().height;

            component.setBounds(x, y, width, height);

            for (int i = 1; i < componentCount; i++) {
                component = parent.getComponent(i);
                prefSize = component.getPreferredSize();
                minSize = (component.isMinimumSizeSet()) ? component.getMinimumSize() : prefSize;

                x += width + this.getHGap();

                width = Math.max(prefSize.width, minSize.width);
                height = parent.getSize().height;

                component.setBounds(x, y, width, height);
            }
        }
    }
}
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

 /**
 * An implementation of the LayoutManager interface, for containers
 * that contain a single component that should be as large as
 * possible under the constraint that a given aspect ratio
 * (width/height) is maintained.
 */
public class AspectLayout implements LayoutManager {
    /**
     * The current aspect that should be maintained (width/height)
     */
    private final float aspect;

    /**
     * The alignment in horizontal or vertical direction.
     * If there is extra space in either direction, then
     * the component will aligned according to this value
     */
    private final float alignment;

    /**
     * Creates a new AspectLayout.
     *
     * @param aspect The aspect for the contained component
     */
    public AspectLayout() {
        this(1.0f, 0.5f);
    }

    /**
     * Creates a new AspectLayout for the given aspect
     *
     * @param aspect The aspect for the contained component
     */
    public AspectLayout(final float aspect) {
        this(aspect, 0.5f);
    }

    /**
     * Creates a new AspectLayout for the given aspect and
     * alignment
     *
     * @param aspect The aspect for the contained component
     * @param alignment The alignment for the contained component
     */
    public AspectLayout(final float aspect, final float alignment) {
        this.aspect = aspect;
        this.alignment = alignment;
    }

    /**
     * {@inheritDoc}
     */
    public void addLayoutComponent(final String name, final Component comp) {
    }

    /**
     * {@inheritDoc}
     */
    public void layoutContainer(final Container parent) {
        synchronized (parent.getTreeLock()) {
            if (parent.getComponentCount() >= 0) {
                final Component component = parent.getComponent(0);
                final Insets insets = parent.getInsets();
                final int maxWidth1 = parent.getWidth() - (insets.left + insets.right);
                final int maxHeight1 = parent.getHeight() - (insets.top + insets.bottom);
                final int maxWidth2 = (int)(aspect * maxHeight1);
                final int maxHeight2 = (int)((float)maxWidth1 / aspect);
                final int newWidth = Math.min(maxWidth1, maxWidth2);
                final int newHeight = Math.min(maxHeight1, maxHeight2);
                final int hGap = (int)(alignment * (parent.getWidth() - newWidth));
                final int vGap = (int)(alignment * (parent.getHeight() - newHeight));
                component.setBounds(hGap, vGap, newWidth, newHeight);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public Dimension minimumLayoutSize(final Container parent) {
        if (parent.getComponentCount() == 0) {
            return new Dimension(0,0);
        } else {
            return parent.getComponent(0).getMinimumSize();
        }
    }

    /**
     * {@inheritDoc}
     */
    public Dimension preferredLayoutSize(final Container parent) {
        if (parent.getComponentCount() == 0) {
            return new Dimension(0,0);
        } else {
            return parent.getComponent(0).getPreferredSize();
        }
    }

    /**
     * {@inheritDoc}
     */
    public void removeLayoutComponent(final Component comp) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return getClass().getName() + "[aspect=" + aspect + "]";
    }
}
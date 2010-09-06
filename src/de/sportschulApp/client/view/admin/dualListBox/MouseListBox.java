package de.sportschulApp.client.view.admin.dualListBox;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import java.util.ArrayList;

/**
 * Either left or right hand side of a {@link DualListBox}.
 */
class MouseListBox extends Composite {

  private static final class SpacerHTML extends HTML {

    public SpacerHTML() {
      super("&nbsp;");
    }
  }

  private static final String CSS_DEMO_DUAL_LIST_EXAMPLE_ITEM = "demo-DualListExample-item";

  private static final String CSS_DEMO_DUAL_LIST_EXAMPLE_ITEM_HAS_CONTENT = "demo-DualListExample-item-has-content";

  private static final String CSS_DEMO_MOUSELISTBOX = "demo-MouseListBox";

  private ListBoxDragController dragController;

  private Grid grid;

  private int widgetCount = 0;

  /**
   * Used by {@link ListBoxDragController} to create a draggable listbox containing the selected
   * items.
   */
  MouseListBox(int size) {
    grid = new Grid(size, 1);
    initWidget(grid);
    grid.setCellPadding(0);
    grid.setCellSpacing(0);
    addStyleName(CSS_DEMO_MOUSELISTBOX);
    for (int i = 0; i < size; i++) {
      grid.getCellFormatter().addStyleName(i, 0, CSS_DEMO_DUAL_LIST_EXAMPLE_ITEM);
      setWidget(i, null);
    }
  }

  /**
   * Used by {@link DualListBox} to create the left and right list boxes.
   */
  MouseListBox(ListBoxDragController dragController, int size) {
    this(size);
    this.dragController = dragController;
  }

  void add(String text) {
    add(new Label(text));
  }

  void add(Widget widget) {
    setWidget(widgetCount++, widget);
  }

  int getWidgetCount() {
    return widgetCount;
  }

  boolean remove(Widget widget) {
    int index = getWidgetIndex(widget);
    if (index == -1) {
      return false;
    }
    for (int i = index; i < widgetCount - 1; i++) {
      // explicitly remove and add widget back for correct draggability
      setWidget(i, removeWidget(i + 1));
    }
    setWidget(widgetCount - 1, null);
    widgetCount--;
    return true;
  }

  ArrayList<Widget> widgetList() {
    ArrayList<Widget> widgetList = new ArrayList<Widget>();
    for (int i = 0; i < getWidgetCount(); i++) {
      widgetList.add(getWidget(i));
    }
    return widgetList;
  }

  private Widget getWidget(int index) {
    return grid.getWidget(index, 0);
  }
  

  private int getWidgetIndex(Widget widget) {
    for (int i = 0; i < getWidgetCount(); i++) {
      if (getWidget(i) == widget) {
        return i;
      }
    }
    return -1;
  }

  private Widget removeWidget(int index) {
    Widget widget = getWidget(index);
    if (widget != null && dragController != null && !(widget instanceof SpacerHTML)) {
      dragController.makeNotDraggable(widget);
    }
    grid.getCellFormatter().removeStyleName(index, 0, CSS_DEMO_DUAL_LIST_EXAMPLE_ITEM_HAS_CONTENT);
    grid.setWidget(index, 0, new SpacerHTML());
    return widget;
  }

  private void setWidget(int index, Widget widget) {
    removeWidget(index);
    if (widget == null) {
      widget = new SpacerHTML();
    } else {
      grid.getCellFormatter().addStyleName(index, 0, CSS_DEMO_DUAL_LIST_EXAMPLE_ITEM_HAS_CONTENT);
      if (dragController != null) {
        dragController.makeDraggable(widget);
      }
    }
    grid.setWidget(index, 0, widget);
  }
}

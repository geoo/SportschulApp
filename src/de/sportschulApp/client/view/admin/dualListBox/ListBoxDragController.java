package de.sportschulApp.client.view.admin.dualListBox;

import java.util.ArrayList;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.allen_sauer.gwt.dnd.client.util.DOMUtil;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

/**
 * DragController for {@link DualListExample}.
 */
class ListBoxDragController extends PickupDragController {

	ListBoxDragController(DualListBox dualListBox) {
		super(dualListBox, false);
		setBehaviorDragProxy(true);
		setBehaviorMultipleSelection(true);
	}

	@Override
	public void dragEnd() {
		// process drop first
		super.dragEnd();

		if (context.vetoException == null) {
			// remove original items
			MouseListBox currentMouseListBox = (MouseListBox) context.draggable.getParent().getParent();
			while (!context.selectedWidgets.isEmpty()) {
				Widget widget = context.selectedWidgets.get(0);
				toggleSelection(widget);
				currentMouseListBox.remove(widget);
			}
		}
	}

	ArrayList<Widget> getSelectedWidgets(MouseListBox mouseListBox) {
		ArrayList<Widget> widgetList = new ArrayList<Widget>();
		for (Widget widget : context.selectedWidgets) {
			if (widget.getParent().getParent() == mouseListBox) {
				widgetList.add(widget);
			}
		}
		return widgetList;
	}

	@Override
	protected Widget newDragProxy(DragContext context) {
		MouseListBox currentMouseListBox = (MouseListBox) context.draggable.getParent().getParent();
		MouseListBox proxyMouseListBox = new MouseListBox(context.selectedWidgets.size());
		proxyMouseListBox.setWidth(DOMUtil.getClientWidth(currentMouseListBox.getElement()) + "px");
		for (Widget widget : context.selectedWidgets) {
			HTML htmlClone = new HTML(DOM.getInnerHTML(widget.getElement()));
			proxyMouseListBox.add(htmlClone);
		}
		return proxyMouseListBox;
	}

	@Override
	public void previewDragStart() throws VetoDragException {
		super.previewDragStart();
		if (context.selectedWidgets.isEmpty()) {
			throw new VetoDragException();
		}
	}

	@Override
	public void setBehaviorDragProxy(boolean dragProxyEnabled) {
		if (!dragProxyEnabled) {
			throw new IllegalArgumentException();
		}
		super.setBehaviorDragProxy(dragProxyEnabled);
	}

	@Override
	public void toggleSelection(Widget draggable) {
		super.toggleSelection(draggable);
		MouseListBox currentMouseListBox = (MouseListBox) draggable.getParent().getParent();
		ArrayList<Widget> otherWidgets = new ArrayList<Widget>();
		for (Widget widget : context.selectedWidgets) {
			if (widget.getParent().getParent() != currentMouseListBox) {
				otherWidgets.add(widget);
			}
		}
		for (Widget widget : otherWidgets) {
			super.toggleSelection(widget);
		}
	}
}

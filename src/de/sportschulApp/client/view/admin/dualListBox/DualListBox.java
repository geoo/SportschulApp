package de.sportschulApp.client.view.admin.dualListBox;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import com.allen_sauer.gwt.dnd.client.DragController;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Example of two lists side by side for {@link DualListExample}.
 */
public class DualListBox extends AbsolutePanel {


	private static final int LIST_SIZE = 13;

	private Button allLeft;

	private Button allRight;

	private ListBoxDragController dragController;

	private MouseListBox left;

	private Button oneLeft;

	private Button oneRight;

	private MouseListBox right;

	public DualListBox(int visibleItems, String width) {
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		add(horizontalPanel);
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		dragController = new ListBoxDragController(this);
		left = new MouseListBox(dragController, LIST_SIZE);
		right = new MouseListBox(dragController, LIST_SIZE);

		left.setWidth(width);
		left.setStyleName("dualListBoxTable");
		right.setWidth(width);
		right.setStyleName("dualListBoxTable");

		horizontalPanel.add(left);
		horizontalPanel.add(verticalPanel);
		horizontalPanel.add(right);

		oneRight = new Button("&gt;");
		oneLeft = new Button("&lt;");
		allRight = new Button("&gt;&gt;");
		allLeft = new Button("&lt;&lt;");
		verticalPanel.add(oneRight);
		verticalPanel.add(oneLeft);
		verticalPanel.add(new HTML("&nbsp;"));
		verticalPanel.add(allRight);
		verticalPanel.add(allLeft);

		allRight.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				moveItems(left, right, false);
			}
		});

		allLeft.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				moveItems(right, left, false);
			}
		});

		oneRight.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				moveItems(left, right, true);
			}
		});

		oneLeft.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				moveItems(right, left, true);
			}
		});

		ListBoxDropController leftDropController = new ListBoxDropController(
				left);
		ListBoxDropController rightDropController = new ListBoxDropController(
				right);
		dragController.registerDropController(leftDropController);
		dragController.registerDropController(rightDropController);
	}

	public void addLeft(String string) {
		left.add(string);
	}

	/**
	 * Adds an widget to the left list box.
	 * 
	 * @param widget
	 *            the text of the item to be added
	 */
	public void addLeft(Widget widget) {
		left.add(widget);
	}

	public DragController getDragController() {
		return dragController;
	}

	protected void moveItems(MouseListBox from, MouseListBox to,
			boolean justSelectedItems) {
		ArrayList<Widget> widgetList = justSelectedItems ? dragController
				.getSelectedWidgets(from) : from.widgetList();
		for (Widget widget : widgetList) {
			// TODO let widget.removeFromParent() take care of from.remove()
			from.remove(widget);
			to.add(widget);
		}
	}

	public ArrayList<String> getWidgetListLeft() {
		String[] temp2;
		ArrayList<Widget> temp = left.widgetList();
		String temp3;
		ArrayList<String>result = new ArrayList<String>();

		Iterator itr = temp.iterator();
		while (itr.hasNext()) {
			temp3 = itr.next().toString();
			temp2 = temp3.split(">");
			temp2 = temp2[1].split("<");
			result.add(temp2[0]);
		}

		return result;
	}

	public ArrayList<String> getWidgetListRight() {
		String[] temp2;
		ArrayList<Widget> temp = right.widgetList();
		String temp3;
		ArrayList<String>result = new ArrayList<String>();

		Iterator itr = temp.iterator();
		while (itr.hasNext()) {
			temp3 = itr.next().toString();
			temp2 = temp3.split(">");
			temp2 = temp2[1].split("<");
			result.add(temp2[0]);
		}

		return result;
	}

}

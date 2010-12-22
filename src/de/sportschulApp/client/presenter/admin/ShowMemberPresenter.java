package de.sportschulApp.client.presenter.admin;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.Presenter;
import de.sportschulApp.client.services.AdminServiceAsync;
import de.sportschulApp.shared.Member;

public class ShowMemberPresenter implements Presenter{
	public interface Display{
		Widget asWidget();
		HasClickHandlers getDeleteLabel();
		HasClickHandlers getEditLabel();
		HasClickHandlers getCloseLabel();
		void setMemberCourses(String courses);
		void setMemberData(Member member);
	}

	private String courseData = new String();
	private final Display display;
	private Member member = new Member();
	private final AdminServiceAsync rpcService;
	private DialogBox popup;

	public ShowMemberPresenter(AdminServiceAsync rpcService, HandlerManager eventBus, Display display, int barcodeID, DialogBox popup) {
		this.display = display;
		this.rpcService = rpcService;
		this.popup = popup;
		bind();
		fetchMemberData(barcodeID);
	}

	private void bind() {
		display.getEditLabel().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				History.newItem("adminMembersEditMember:" + member.getBarcodeID());
			}
		});

		display.getDeleteLabel().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(Window.confirm("Bestätigen sie mit OK wenn das Mitglied wirklich gelöscht werden soll.")) {
					rpcService.deleteMemberByMemberID(member.getMemberID(), new AsyncCallback<Void>() {
						public void onFailure(Throwable caught) {
							Window.alert("Das löschen des Mitglieds ist Fehlgeschlagen");
						}
						public void onSuccess(Void result) {
							History.fireCurrentHistoryState();
						}
					});
				}
			}
		});
		
		display.getCloseLabel().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				popup.hide();
			}
		});
	}

	public void buildMemberCourses() {
		display.setMemberCourses(courseData);
	}

	public void buildShowMemberView(Member member) {
		display.setMemberData(member);
		fetchCourseData(member);
	}

	public void fetchCourseData(Member member) {
		for (int i = 0; i < member.getCourses().size(); i++) {
			if(member.getCourses().get(i) != 0) {
				rpcService.getCourseBeltPair(member.getCourses().get(i), member.getGraduations().get(i), new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
					}
					public void onSuccess(String result) {
						courseData = courseData + result;
						buildMemberCourses();
					}
				});
			}
		}
	}

	public void fetchMemberData(int barcodeID) {
		rpcService.getMemberByBarcodeID(barcodeID, new AsyncCallback<Member>() {
			public void onFailure(Throwable caught) {
			}
			public void onSuccess(Member result) {
				member = result;
				buildShowMemberView(result);
			}
		});
	}

	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}

}

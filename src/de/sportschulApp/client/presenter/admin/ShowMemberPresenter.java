package de.sportschulApp.client.presenter.admin;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.Presenter;
import de.sportschulApp.client.services.AdminServiceAsync;
import de.sportschulApp.shared.Member;

public class ShowMemberPresenter implements Presenter{
	public interface Display{
		void setMemberData(Member member);
		void setMemberCourses(String courses);
		HasClickHandlers getEditLabel();
		HasClickHandlers getDeleteLabel();
		Widget asWidget();
	}
	
	private final Display display;
	private final AdminServiceAsync rpcService;
	private String courseData = new String();
	private Member member = new Member();
	
	public ShowMemberPresenter(AdminServiceAsync rpcService, HandlerManager eventBus, Display display, int barcodeID) {
	    this.display = display;
	    this.rpcService = rpcService;
	    bind();
	    fetchMemberData(barcodeID);
	}
	
	public void fetchMemberData(int barcodeID) {
		rpcService.getMemberByBarcodeID(barcodeID, new AsyncCallback<Member>() {
			public void onSuccess(Member result) {
				member = result;
				buildShowMemberView(result);
			}
			public void onFailure(Throwable caught) {
			}
		});
	}
	
	public void fetchCourseData(Member member) {
		for (int i = 0; i < member.getCourses().size(); i++) {
			if(member.getCourses().get(i) != 0) {
				rpcService.getCourseBeltPair(member.getCourses().get(i), member.getGraduations().get(i), new AsyncCallback<String>() {
					public void onSuccess(String result) {
						courseData = courseData + result;
						buildMemberCourses();
					}
					public void onFailure(Throwable caught) {
					}
				});
			}
		}
	}
	
	public void buildShowMemberView(Member member) {
		this.display.setMemberData(member);
		fetchCourseData(member);
	}
	
	public void buildMemberCourses() {
		this.display.setMemberCourses(courseData);
	}

	private void bind() {
		this.display.getEditLabel().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				History.newItem("adminMembersEditMember:" + member.getBarcodeID());
			}
		});
		
		this.display.getDeleteLabel().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(Window.confirm("Bestätigen sie mit OK wenn das Mitglied wirklich gelöscht werden soll.")) {
					rpcService.deleteMemberByMemberID(member.getMemberID(), new AsyncCallback<Void>() {
						public void onSuccess(Void result) {
							History.fireCurrentHistoryState();
						}
						public void onFailure(Throwable caught) {
							Window.alert("Das löschen des Mitglieds ist Fehlgeschlagen");	
						}
					});
				}
			}
		});
	}

	public void go(HasWidgets container) {
		container.clear();
	    container.add(display.asWidget());
	}

}

package de.sportschulApp.client.presenter.admin;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.Presenter;
import de.sportschulApp.client.services.AdminServiceAsync;
import de.sportschulApp.client.view.admin.ShowCourseView;
import de.sportschulApp.shared.Member;

public class ShowCoursePresenter implements Presenter{
	public interface Display{
		void setMemberData(Member member);
		void setMemberCourses(String courses);
		HasClickHandlers getEditMemberLabel();
		String getBarcodeID();
		Widget asWidget();
	}
	
	private final Display display;
	private final AdminServiceAsync rpcService;
	private String courseData = new String();
	
	public ShowCoursePresenter(AdminServiceAsync rpcService, HandlerManager eventBus, Display display, int courseID) {
	    this.display = display;
	    this.rpcService = rpcService;
	    bind();
	    fetchCourseData(courseID);
	}

	public void fetchCourseData(int barcodeID) {
//		rpcService.getC(barcodeID, new AsyncCallback<Member>() {
//			public void onSuccess(Member result) {
//				buildShowMemberView(result);
//			}
//			public void onFailure(Throwable caught) {
//			}
//		});
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
		this.display.getEditMemberLabel().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				History.newItem("adminMembersEditMember:" + display.getBarcodeID());
			}
		});
	}

	public void go(HasWidgets container) {
		container.clear();
	    container.add(display.asWidget());
	}

}
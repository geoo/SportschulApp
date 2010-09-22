package de.sportschulApp.client.presenter.admin;

import org.apache.tools.ant.types.CommandlineJava.SysProperties;

import com.google.gwt.event.shared.HandlerManager;
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
		Widget asWidget();
	}
	
	private final Display display;
	private final AdminServiceAsync rpcService;
	private String courseData = new String();
	
	public ShowMemberPresenter(AdminServiceAsync rpcService, HandlerManager eventBus, Display display, int barcodeID) {
	    this.display = display;
	    this.rpcService = rpcService;
	    bind();
	    fetchMemberData(barcodeID);
	}
	
	public void fetchMemberData(int barcodeID) {
		rpcService.getMemberByBarcodeID(barcodeID, new AsyncCallback<Member>() {
			public void onSuccess(Member result) {
				buildShowMemberView(result);
			}
			public void onFailure(Throwable caught) {
			}
		});
	}
	
	public void fetchCourseData(Member member) {
//		for (int i = 0; i < member.getCourses().size(); i++) {
			System.out.println("bis hier hin und net weiter");
			System.out.println(member.getCourses().get(0));
//			if(!(member.getCourses().get(i).equals(null))) {
//				rpcService.getCourseName(member.getCourses().get(i), new AsyncCallback<String>() {
//					public void onSuccess(String result) {
//						courseData.concat(result);
//					}
//					public void onFailure(Throwable caught) {
//					}
//				});
//				buildMemberCourses();
//			}
//		}
	}
	
	public void buildShowMemberView(Member member) {
		this.display.setMemberData(member);
		fetchCourseData(member);
	}
	
	public void buildMemberCourses() {
		this.display.setMemberCourses(courseData);
	}

	private void bind() {
		
	}

	public void go(HasWidgets container) {
		container.clear();
	    container.add(display.asWidget());
	}

}

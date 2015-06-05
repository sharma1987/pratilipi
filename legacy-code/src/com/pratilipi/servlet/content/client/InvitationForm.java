package com.pratilipi.servlet.content.client;

import com.claymus.commons.shared.UserStatus;
import com.claymus.service.shared.data.UserData;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;

public class InvitationForm extends Composite {
	private TextBox emailInput = new TextBox();
	private Button inviteButton = new Button("Invite Friend");
	
	public InvitationForm(String refId){
		emailInput.addStyleName("form-control");
		emailInput.getElement().setPropertyString("placeholder", "Email");
		
		inviteButton.addStyleName("btn");
		inviteButton.addStyleName("btn-default");
		
		HorizontalPanel inviteForm = new HorizontalPanel();
		inviteForm.setSpacing(10);
		inviteForm.setStyleName("Pratilipi-InvitationForm");
		inviteForm.add(emailInput);
		inviteForm.add(inviteButton);
		
		initWidget(inviteForm);
	}
	
	public UserData getUser() {
		UserData userData = new UserData();
		userData.setEmail( emailInput.getText().trim() );
		userData.setCampaign( "SoftLaunch" );
		userData.setReferer( Window.Location.getParameter( "id" ) );
		userData.setStatus( UserStatus.PRELAUNCH_REFERRAL );
		return userData;
	}
	
	public void addInviteButtonClickHandler(ClickHandler clickHandler){
		inviteButton.addClickHandler(clickHandler);
	}
	
	public void reloadForm(){
		emailInput.setText("");
	}
	
	public void setErrorStyle(){
		emailInput.addStyleName( "gwt-TextBoxError" );
	}
	
	public void removeErrorStyle(){
		emailInput.removeStyleName( "gwt-TextBoxError" );
	}

}

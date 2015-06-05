package com.pratilipi.servlet.content.client;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

public class ContactMailForm extends Composite {
	private TextBox nameInput = new TextBox();
	private TextBox emailInput = new TextBox();
	private TextArea mailBody = new TextArea();
	private Button sendMail = new Button( "Send" );
	
	private ValidateForm validateForm = new ValidateForm();
	
	private Label serverMessage = new Label();
	private InlineLabel emailInputError = new InlineLabel();
	private InlineLabel nameInputError = new InlineLabel();
	private InlineLabel mailBodyError = new InlineLabel();
	
	public ContactMailForm(){
		Panel contactMailForm = new FlowPanel();
		
		nameInput.addStyleName( "form-control" );
		nameInput.getElement().setAttribute("placeholder", "Name");
		nameInput.addBlurHandler( new BlurHandler() {

			@Override
			public void onBlur(BlurEvent event) {
				validateName();
			}});
		
		emailInput.addStyleName( "form-control" );
		emailInput.getElement().setAttribute("placeholder", "Email");
		emailInput.getElement().getStyle().setProperty("marginTop", "15px");
		emailInput.addBlurHandler( new BlurHandler() {

			@Override
			public void onBlur(BlurEvent event) {
				validateEmail();
			}});
		
		mailBody.addStyleName( "form-control" );
		mailBody.getElement().setAttribute("placeholder", "Enter Your Query");
		mailBody.getElement().getStyle().setProperty("marginTop", "15px");
		mailBody.setVisibleLines( 5 );
		mailBody.addBlurHandler( new BlurHandler() {

			@Override
			public void onBlur(BlurEvent event) {
				validateBody();
			}});
		
		sendMail.addStyleName( "btn btn-md btn-success" );
		sendMail.getElement().getStyle().setProperty("marginTop", "15px");
		
		serverMessage.getElement().getStyle().setProperty("marginTop", "15px");
		serverMessage.getElement().setAttribute( "role", "alert" );
		serverMessage.setVisible( false );
		
		//Error message Style
		emailInputError.addStyleName( "errorMessage" );
		nameInputError.addStyleName( "errorMessage" );
		mailBodyError.addStyleName( "errorMessage" );
		
		contactMailForm.add( nameInput );
		contactMailForm.add( nameInputError );
		contactMailForm.add( emailInput );
		contactMailForm.add( emailInputError );
		contactMailForm.add( mailBody );
		contactMailForm.add( mailBodyError );
		contactMailForm.add( sendMail );
		contactMailForm.add( serverMessage );
		
		initWidget( contactMailForm );
	}
	
	public void addSendButtonClickHandler( ClickHandler clickHandler ) {
		sendMail.addClickHandler( clickHandler );
	}

	public void setEnable( boolean enabled ) {
		emailInput.setEnabled( enabled );
		nameInput.setEnabled( enabled );
		mailBody.setEnabled( enabled );
	}

	public String getEmail() {
		return emailInput.getText();
	}
	
	public String getName() {
		return nameInput.getText();
	}
	
	public String getMailBody() {
		return mailBody.getText();
	}
	
	public boolean validateEmail(){
		if( emailInput.getText().isEmpty() ){
			emailInput.addStyleName("textBoxError");
			setEmailInputError("Enter your email");
			showEmailInputError();
			return false;
		}
		else if( validateForm.ValidateEmail( emailInput.getText() ) ){
			emailInput.addStyleName("textBoxError");
			setEmailInputError("Email not in proper format");
			showEmailInputError();
			return false;
		}
		else{
			emailInput.removeStyleName("textBoxError");
			hideEmailInputError();
			return true;
		}
	}
	
	public boolean validateName(){
		if( nameInput.getText().isEmpty() ){
			nameInput.addStyleName( "textBoxError" );
			setNameInputError("Enter Your Name");
			showNameInputError();
			return false;
		}
		else{
			nameInput.removeStyleName( "textBoxError" );
			hideNameInputError();
			return true;
		}
	}
	
	public boolean validateBody(){
		if( mailBody.getText().isEmpty() ){
			mailBody.addStyleName( "textBoxError" );
			setBodyError("Enter Your Query");
			showBodyError();
			return false;
		}
		else{
			mailBody.removeStyleName( "textBoxError" );
			hideBodyError();
			return true;
		}
	}
	
	public void setEmailInputError(String error){
		emailInputError.setText( error );
	}
	
	public void showEmailInputError(){
		emailInputError.setVisible( true );
	}
	
	public void hideEmailInputError(){
		emailInputError.setVisible( false );
	}
	
	public void setNameInputError(String error){
		nameInputError.setText( error );
	}
	
	public void showNameInputError(){
		nameInputError.setVisible( true );
	}
	
	public void hideNameInputError(){
		nameInputError.setVisible( false );
	}
	
	public void setBodyError(String error){
		mailBodyError.setText( error );
	}
	
	public void showBodyError(){
		mailBodyError.setVisible( true );
	}
	
	public void hideBodyError(){
		mailBodyError.setVisible( false );
	}
	
	public void setServerMsg( String message ) {
		this.serverMessage.getElement().removeAllChildren();
		HTML msg = new HTML();
		msg.setHTML( message );
		this.serverMessage.getElement().appendChild( msg.getElement() );
	}
	
	public void setServerMsgClass ( String messageClass ) {
		this.serverMessage.addStyleName( messageClass );
	}
	
	public void setVisibleServerMsg( boolean isVisible ) {
		this.serverMessage.setVisible( isVisible );
	}
	
	public void resetForm() {
		nameInput.setText( "" );
		emailInput.setText( "" );
		mailBody.setText( "" );
	}
}

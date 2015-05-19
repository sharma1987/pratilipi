package com.pratilipi.commons.client;

import com.claymus.commons.client.ui.formfield.ListBoxFormField;
import com.claymus.commons.client.ui.formfield.TextInputFormField;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.pratilipi.service.shared.data.AuthorData;
import com.pratilipi.service.shared.data.LanguageData;

public class AuthorDataInputViewImpl extends AuthorDataInputView {
	
	private Panel panel = new FlowPanel();
	
	private Panel serverErrorRow = new FlowPanel();
	private Panel firstNameRow = new FlowPanel();
	private Panel lastNameRow = new FlowPanel();
	private Panel penNameRow = new FlowPanel();
	private Panel languageEmailRow = new FlowPanel();
	
	private Panel firstNameCol = new SimplePanel();
	private Panel lastNameCol = new SimplePanel();
	private Panel penNameCol = new SimplePanel();
	private Panel firstNameEnCol = new SimplePanel();
	private Panel lastNameEnCol = new SimplePanel();
	private Panel penNameEnCol = new SimplePanel();
	private Panel languageCol = new SimplePanel();
	private Panel emailCol = new SimplePanel();
	
	private TextInputFormField firstNameInput = new TextInputFormField();
	private TextInputFormField lastNameInput = new TextInputFormField();
	private TextInputFormField penNameInput = new TextInputFormField();
	private TextInputFormField firstNameEnInput = new TextInputFormField();
	private TextInputFormField lastNameEnInput = new TextInputFormField();
	private TextInputFormField penNameEnInput = new TextInputFormField();	
	private ListBoxFormField languageList = new ListBoxFormField();
	private TextInputFormField emailInput = new TextInputFormField();
	
	private AuthorData authorData = null;
	
	//Server Error Msg
	private Label serverError = new Label();
	
	public AuthorDataInputViewImpl(){
		firstNameInput.setPlaceholder( "First Name (Native Language)" );
		firstNameInput.getElement().getStyle().setFontSize( 12, Unit.PX);
		
		lastNameInput.setPlaceholder( "Last Name (Native Language)" );
		lastNameInput.getElement().getStyle().setFontSize( 12, Unit.PX);
		
		penNameInput.setPlaceholder( "Pen Name (Native Language)" );
		penNameInput.getElement().getStyle().setFontSize( 12, Unit.PX);

		firstNameEnInput.setPlaceholder( "First Name (English)" );
		firstNameEnInput.getElement().getStyle().setFontSize( 12, Unit.PX);
		firstNameEnInput.setRequired( true );
		
		lastNameEnInput.setPlaceholder( "Last Name (English)" );
		lastNameEnInput.getElement().getStyle().setFontSize( 12, Unit.PX);
		
		penNameEnInput.setPlaceholder( "Pen Name (English)" );
		penNameEnInput.getElement().getStyle().setFontSize( 12, Unit.PX);

		languageList.setRequired( true );
		languageList.setPlaceholder( "Primary Language" );
		
		emailInput.setPlaceholder( "Email" );
		emailInput.setRequired( true );

		//Error Message styling
		serverError.addStyleName( "alert alert-danger" );
		serverError.getElement().setAttribute( "role", "alert") ;
		serverError.setVisible( false );
		
		// Composing the widget
		panel.add( serverErrorRow );
		panel.add( firstNameRow );
		panel.add( lastNameRow );
		panel.add( penNameRow);
		panel.add( languageEmailRow );

		firstNameRow.add( firstNameCol );
		firstNameRow.add( firstNameEnCol );

		lastNameRow.add( lastNameCol );
		lastNameRow.add( lastNameEnCol );
		
		penNameRow.add( penNameCol );
		penNameRow.add( penNameEnCol );
		
		languageEmailRow.add( languageCol );
		languageEmailRow.add( emailCol );
		
		firstNameCol.add( firstNameInput );
		firstNameEnCol.add( firstNameEnInput );
		
		lastNameCol.add( lastNameInput );
		lastNameEnCol.add( lastNameEnInput );
		
		penNameCol.add( penNameInput );
		penNameEnCol.add( penNameEnInput );
		
		languageCol.add( languageList );
		emailCol.add( emailInput );
		
		serverErrorRow.add( serverError );
		
		
		// Setting required style classes
		panel.addStyleName( "container-fluid" );
		
		firstNameRow.addStyleName( "row" );
		lastNameRow.addStyleName( "row" );
		penNameRow.addStyleName( "row" );
		languageEmailRow.addStyleName( "row" );
		serverErrorRow.addStyleName( "row" );
		
		firstNameCol.addStyleName( "col-sm-5" );
		firstNameEnCol.addStyleName( "col-sm-5" );
		
		lastNameCol.addStyleName( "col-sm-5" );
		lastNameEnCol.addStyleName( "col-sm-5" );
		
		penNameCol.addStyleName( "col-sm-5" );
		penNameEnCol.addStyleName( "col-sm-5" );
		
		emailCol.addStyleName( "col-sm-5" );
		languageCol.addStyleName( "col-sm-5" );
		
		initWidget( panel );
	}


	@Override
	public void add( Button button ) {
		panel.add( button );
	}
	
	@Override
	public void addLanguageListItem( LanguageData languageData ) {
		languageList.addItem(
				languageData.getName() + " (" + languageData.getNameEn() + ")",
				languageData.getId().toString() );
		
		if( authorData != null && authorData.getLanguageId().equals( languageData.getId() ) )
			languageList.setValue( languageData.getId().toString() );
	}
		
	@Override
	public boolean validateInputs() {
		boolean validated = true;
		validated = firstNameInput.validate() && validated;
		validated = lastNameInput.validate() && validated;
		validated = penNameInput.validate() && validated;
		validated = firstNameEnInput.validate() && validated;
		validated = lastNameEnInput.validate() && validated;
		validated = penNameEnInput.validate() && validated;
		validated = emailInput.validate() && validated;
		validated = languageList.validate() && validated;
		return validated;
	}

	@Override
	public void setEnabled(boolean enabled) {
		firstNameInput.setEnabled( enabled );
		lastNameInput.setEnabled( enabled );
		penNameInput.setEnabled( enabled );
		firstNameEnInput.setEnabled( enabled );
		lastNameEnInput.setEnabled( enabled );
		penNameEnInput.setEnabled( enabled );
		emailInput.setEnabled( enabled );
		languageList.setEnabled( enabled );
	}

	@Override
	public AuthorData getAuthorData() {
		AuthorData authorData = new AuthorData();
		authorData.setId( this.authorData == null ? null : this.authorData.getId() );
		
		authorData.setLanguageId( Long.parseLong( languageList.getValue() ) );
		authorData.setFirstName( firstNameInput.getText() );
		authorData.setLastName( lastNameInput.getText() );
		authorData.setPenName( penNameInput.getText() );
		authorData.setFirstNameEn( firstNameEnInput.getText() );
		authorData.setLastNameEn( lastNameEnInput.getText() );
		authorData.setPenNameEn( penNameEnInput.getText() );
		authorData.setEmail( emailInput.getText() );
		return authorData;
	}

	@Override
	public void setAuthorData( AuthorData authorData ) {
		this.authorData = authorData;

		languageList.setValue( authorData.getLanguageId().toString() );
		firstNameInput.setText( authorData.getFirstName() );
		lastNameInput.setText( authorData.getLastName() );
		penNameInput.setText( authorData.getPenName() );
		firstNameEnInput.setText( authorData.getFirstNameEn() );
		lastNameEnInput.setText( authorData.getLastNameEn() );
		penNameEnInput.setText( authorData.getPenNameEn() );
		emailInput.setText( authorData.getEmail() );
	}
	
	@Override
	public void setServerError( String error ){
		this.serverError.getElement().removeAllChildren();
		HTML msg = new HTML();
		msg.setHTML( error );
		this.serverError.getElement().appendChild( msg.getElement() );
	}

	@Override
	public void setVisibleServerError( boolean visible ){
		this.serverError.setVisible( visible );
	}
}

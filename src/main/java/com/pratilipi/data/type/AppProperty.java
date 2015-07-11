package com.pratilipi.data.type;

import java.io.Serializable;

public interface AppProperty extends Serializable {
	
	String DATASTORE_PRATILIPI_LAST_BACKUP = "DataStore.Pratilipi.LastBackup";
	
	String getId();

	<T> T getValue();

	<T> void setValue( T value );
	
}
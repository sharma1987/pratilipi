package com.pratilipi.taskqueue;

import java.util.List;

public interface TaskQueue {

	void add( Task task );

	void addAll( List<Task> taskList );
	
}

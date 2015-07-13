package com.pratilipi.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.jdo.Query;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JDOCursorHelper;

public class GaeQueryBuilder {

	public enum Operator {
		NOT_NULL,
		EQUALS,
		LESS_THAN,
		LESS_THAN_OR_EQUAL,
		GREATER_THAN,
		GREATER_THAN_OR_EQUAL,
		CONTAINS
	}
	
	private final Query query;
	private final List<String> filters;
	private final List<String> parameteres;
	private final List<String> orderings;
	private final Map<String, Object> paramNameValueMap;
	
	
	public GaeQueryBuilder( Query query ) {
		this.query = query;
		this.filters = new LinkedList<>();
		this.parameteres = new LinkedList<>();
		this.orderings = new LinkedList<>();
		this.paramNameValueMap = new HashMap<>();
	}

	
	public GaeQueryBuilder addFilter( String param, Object value ) {
		return addFilter( param, value, Operator.EQUALS );
	}
	
	public GaeQueryBuilder addFilter( String param, Collection<?> value ) {
		return addFilter( param, value, Operator.CONTAINS );
	}
	
	public GaeQueryBuilder addFilter( String param, Object value, Operator operator ) {
		String paramKey = param + "Param";
		for( int i = 0; ; i++ ) {
			if( paramNameValueMap.get( paramKey ) == null )
				break;
			paramKey = param + "Param_" + i;
		}
		
		switch( operator ) {
			case NOT_NULL:
				filters.add( param + " != null" );
				break;
			case EQUALS:
				filters.add( param + " == " + paramKey );
				break;
			case LESS_THAN:
				filters.add( param + " < " + paramKey );
				break;
			case LESS_THAN_OR_EQUAL:
				filters.add( param + " <= " + paramKey );
				break;
			case GREATER_THAN:
				filters.add( param + " > " + paramKey );
				break;
			case GREATER_THAN_OR_EQUAL:
				filters.add( param + " >= " + paramKey );
				break;
			case CONTAINS:
				filters.add( paramKey + ".contains( " + param + " )");
				break;
			default:
				throw new UnsupportedOperationException( "Operator '" + operator + "' is not yet supported." );
		}
		
		if( operator != Operator.NOT_NULL ) {
			parameteres.add( value.getClass().getName() + " " + paramKey );
			paramNameValueMap.put( paramKey, value );
		}
		
		return this;
	}

	public GaeQueryBuilder addOrdering( String param, boolean asc ) {
		orderings.add( asc ? param : param + " DESC" );
		return this;
	}

	public GaeQueryBuilder setResult( String result ) {
		query.setResult( result );
		return this;
	}

	public GaeQueryBuilder setRange( long start, long end ) {
		query.setRange( start, end );
		return this;
	}

	public GaeQueryBuilder setCursor( String cursorStr ) {
		Cursor cursor = Cursor.fromWebSafeString( cursorStr );
		Map<String, Object> extensionMap = new HashMap<String, Object>();
		extensionMap.put( JDOCursorHelper.CURSOR_EXTENSION, cursor );
		query.setExtensions( extensionMap );
		return this;
	}

	public Query build() {
		if( filters.size() != 0 ) {
			String filterStr = filters.get( 0 );
			for( int i = 1; i < filters.size(); i++ )
				filterStr = filterStr + " && " + filters.get( i );
			query.setFilter( filterStr );
		}
		
		if( parameteres.size() != 0 ) {
			String parameterStr = parameteres.get( 0 );
			for( int i = 1; i < parameteres.size(); i++ )
				parameterStr = parameterStr + ", " + parameteres.get( i );
			query.declareParameters( parameterStr );
		}

		if( orderings.size() != 0 ) {
			String orderingStr = orderings.get( 0 );
			for( int i = 1; i < orderings.size(); i++ )
				orderingStr = orderingStr + ", " + orderings.get( i );
			query.setOrdering( orderingStr );
		}

		return query;
	}

	public Map<String, Object> getParamNameValueMap() {
		return paramNameValueMap;
	}

}

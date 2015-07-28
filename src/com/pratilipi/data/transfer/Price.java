package com.pratilipi.data.transfer;

import java.io.Serializable;
import java.util.Date;

public interface Price extends Serializable {

	public Long getId();
	
	public Long getPratilipiId();
	public void setPratilipiId( Long pratilipiId );
	
	public Long getMrpInInr();
	public void setMrpInInr( Long mrpInInr );
	
	public Long getDiscountByAuthorInInr();
	public void setAuthorDiscountInInr( Long discountByAuthorInInr );
	
	public Long getPratilipiDiscountInInr();
	public void setPratilipiDiscountInInr( Long discountByPratilipiInInr );
	
	public Long getCreatedBy();
	public void setCreatedBy( Long createdBy );
	
	public Date getCreationDate();
	public void setCreationDate( Date creationDate );
	
}

package com.pratilipi.data.transfer;

import java.io.Serializable;
import java.util.Date;

public interface PratilipiMeta extends Serializable {
public Long getId();
	
	public Long getPratilipiId();
	public void setPratilipiId( Long pratilipiId );
	
	public float getMrp();
	public void setMrp( float mrp );
	
	public float getAuthorDiscount();
	public void setAuthorDiscount( float discountAuthor );
	
	public float getPratilipiDiscount();
	public void setPratilipiDiscount( float discountPratilipi );
	
	public float getRoyalty();
	public void setRoyalty( float  royalty );
	
	public Long getCreatedBy();
	public void setCreatedBy( Long createdBy );
	
	public Date getCreationDate();
	public void setCreationDate( Date creationDate );
}

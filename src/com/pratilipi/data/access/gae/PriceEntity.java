package com.pratilipi.data.access.gae;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.pratilipi.data.transfer.Price;

@PersistenceCapable( table = "PRICE" )
public class PriceEntity implements Price {
	
	private static final long serialVersionUID = 6798007451769465807L;

	@PrimaryKey
	@Persistent( column = "PRICE_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long id;
	
	@Persistent( column = "PRATILIPI_ID" )
	private Long pratilipiId;
	
	@Persistent( column = "MRP_IN_PAISE" )
	private Long mrpInPaise;
	
	@Persistent( column = "DISCOUNT_AUTHOR_IN_PAISE" )
	private Long discountByAuthorInPaise;
	
	@Persistent( column = "DISCOUNT_PRATILIPI_IN_PAISE" )
	private Long discountByPratilipiInPaise;
	
	@Persistent( column = "ROYALTY_IN_PERCENTAGE" )
	private Long royalty;
	
	@Persistent( column = "CREATED_BY" )
	private Long createdBy;
	
	@Persistent( column = "CREATION_DATE" )
	private Date creationDate;
	
	
	@Override
	public Long getId(){
		return id;
	}
	
	@Override
	public Long getPratilipiId() {
		return pratilipiId;
	}

	@Override
	public void setPratilipiId( Long pratilipiId ) {
		this.pratilipiId = pratilipiId;
	}

	@Override
	public Long getMrpInInr() {
		return ( mrpInPaise / 100L );
	}

	@Override
	public void setMrpInInr( Long mrpInInr ) {
		this.mrpInPaise = mrpInInr * 100;
	}

	@Override
	public Long getDiscountByAuthorInInr() {
		return ( discountByAuthorInPaise / 100L );
	}

	@Override
	public void setAuthorDiscountInInr( Long discountByAuthorInInr ) {
		this.discountByAuthorInPaise = discountByAuthorInInr * 100;
	}

	@Override
	public Long getPratilipiDiscountInInr() {
		return ( discountByPratilipiInPaise / 100 );
	}

	@Override
	public void setPratilipiDiscountInInr( Long discountByPratilipiInInr ) {
		this.discountByPratilipiInPaise = discountByPratilipiInInr * 100;
	}
	
	@Override
	public Long getCreatedBy(){
		return createdBy;
	}
	
	@Override
	public void setCreatedBy( Long createdBy ){
		this.createdBy = createdBy;
	}

	@Override
	public Date getCreationDate() {
		return creationDate;
	}

	@Override
	public void setCreationDate( Date creationDate ) {
		this.creationDate = creationDate;
	}

}

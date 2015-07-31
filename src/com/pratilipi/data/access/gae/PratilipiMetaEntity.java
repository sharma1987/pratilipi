package com.pratilipi.data.access.gae;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.pratilipi.data.transfer.PratilipiMeta;

@PersistenceCapable( table = "PRICE" )
public class PratilipiMetaEntity implements PratilipiMeta {
	
	private static final long serialVersionUID = 6798007451769465807L;

	@PrimaryKey
	@Persistent( column = "PRATILIPI_META_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long id;
	
	@Persistent( column = "PRATILIPI_ID" )
	private Long pratilipiId;
	
	@Persistent( column = "MRP" )
	private Integer mrp;
	
	@Persistent( column = "DISCOUNT_AUTHOR" )
	private Integer discountAuthor;
	
	@Persistent( column = "DISCOUNT_PRATILIPI" )
	private Integer discountPratilipi;
	
	@Persistent( column = "ROYALTY" )
	private Integer royalty;
	
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
	public float getMrp() {
		// CONVERT PAISE TO RS
		return mrp == null ? 0 : ( ( float ) mrp / 100 );
	}

	@Override
	public void setMrp( float mrp ) {
		// CONVERT RS TO PAISE
		this.mrp = ( int ) mrp * 100;
	}

	@Override
	public float getAuthorDiscount() {
		// CONVERT PAISE TO RS
		return discountAuthor == null ? 0 : ( ( float ) discountAuthor / 100 );
	}

	@Override
	public void setAuthorDiscount( float discountAuthor ) {
		// CONVERT RS TO PAISE
		this.discountAuthor = ( int ) ( discountAuthor * 100 );
	}

	@Override
	public float getPratilipiDiscount() {
		// CONVERT PAISE TO RS
		return discountPratilipi == null ? 0 : ( ( float ) discountPratilipi / 100 );
	}

	@Override
	public void setPratilipiDiscount( float discountPratilipi ) {
		// CONVERT RS TO PAISE
		this.discountPratilipi = ( int ) ( discountPratilipi * 100 );
	}
	
	@Override
	public float getRoyalty() {
		return royalty == null ? 0 : ( ( float) royalty / 100 );
	}

	@Override
	public void setRoyalty(float royalty) {
		this.royalty = ( int ) ( royalty * 100 );
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

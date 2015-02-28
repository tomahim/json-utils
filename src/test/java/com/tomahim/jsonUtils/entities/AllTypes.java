package com.tomahim.jsonUtils.entities;

import java.math.BigDecimal;
import java.util.Date;

public class AllTypes {

	private Boolean typeBoolean;
	private Character typeCharacter;
	private Byte typeByte;
	private Date typeDate;
	private Short typeShort;
	private Integer typeInteger;
	private Long typeLong;
	private Float typeFloat;

	private Double typeDouble;
	private BigDecimal typeBigDecimal;
	private String typeString;

	private int primitiveInt;
	private boolean primitiveBoolean;
	private byte primitiveByte;
	private long primitiveLong;
	private float primitiveFloat;
	private double primitiveDouble;
	private char primitiveChar;
	
	public AllTypes(Boolean typeBoolean, Character typeCharacter,
			Byte typeByte, Date typeDate, Short typeShort, Integer typeInteger,
			Long typeLong, Float typeFloat, Double typeDouble,
			BigDecimal typeBigDecimal, String typeString, int primitiveInt,
			boolean primitiveBoolean, byte primitiveByte, long primitiveLong,
			float primitiveFloat, double primitiveDouble, char primitiveChar) {
		super();
		this.typeBoolean = typeBoolean;
		this.typeCharacter = typeCharacter;
		this.typeByte = typeByte;
		this.typeDate = typeDate;
		this.typeShort = typeShort;
		this.typeInteger = typeInteger;
		this.typeLong = typeLong;
		this.typeFloat = typeFloat;
		this.typeDouble = typeDouble;
		this.typeBigDecimal = typeBigDecimal;
		this.typeString = typeString;
		this.primitiveInt = primitiveInt;
		this.primitiveBoolean = primitiveBoolean;
		this.primitiveByte = primitiveByte;
		this.primitiveLong = primitiveLong;
		this.primitiveFloat = primitiveFloat;
		this.primitiveDouble = primitiveDouble;
		this.primitiveChar = primitiveChar;
	}

	public Boolean getTypeBoolean() {
		return typeBoolean;
	}

	public void setTypeBoolean(Boolean typeBoolean) {
		this.typeBoolean = typeBoolean;
	}

	public Character getTypeCharacter() {
		return typeCharacter;
	}

	public void setTypeCharacter(Character typeCharacter) {
		this.typeCharacter = typeCharacter;
	}

	public Byte getTypeByte() {
		return typeByte;
	}

	public void setTypeByte(Byte typeByte) {
		this.typeByte = typeByte;
	}

	public Date getTypeDate() {
		return typeDate;
	}

	public void setTypeDate(Date typeDate) {
		this.typeDate = typeDate;
	}

	public Short getTypeShort() {
		return typeShort;
	}

	public void setTypeShort(Short typeShort) {
		this.typeShort = typeShort;
	}

	public Integer getTypeInteger() {
		return typeInteger;
	}

	public void setTypeInteger(Integer typeInteger) {
		this.typeInteger = typeInteger;
	}

	public Long getTypeLong() {
		return typeLong;
	}

	public void setTypeLong(Long typeLong) {
		this.typeLong = typeLong;
	}

	public Float getTypeFloat() {
		return typeFloat;
	}

	public void setTypeFloat(Float typeFloat) {
		this.typeFloat = typeFloat;
	}

	public Double getTypeDouble() {
		return typeDouble;
	}

	public void setTypeDouble(Double typeDouble) {
		this.typeDouble = typeDouble;
	}

	public BigDecimal getTypeBigDecimal() {
		return typeBigDecimal;
	}

	public void setTypeBigDecimal(BigDecimal typeBigDecimal) {
		this.typeBigDecimal = typeBigDecimal;
	}

	public String getTypeString() {
		return typeString;
	}

	public void setTypeString(String typeString) {
		this.typeString = typeString;
	}

	public int getPrimitiveInt() {
		return primitiveInt;
	}

	public void setPrimitiveInt(int primitiveInt) {
		this.primitiveInt = primitiveInt;
	}

	public boolean getPrimitiveBoolean() {
		return primitiveBoolean;
	}

	public void setPrimitiveBoolean(boolean primitiveBoolean) {
		this.primitiveBoolean = primitiveBoolean;
	}

	public byte getPrimitiveByte() {
		return primitiveByte;
	}

	public void setPrimitiveByte(byte primitiveByte) {
		this.primitiveByte = primitiveByte;
	}

	public long getPrimitiveLong() {
		return primitiveLong;
	}

	public void setPrimitiveLong(long primitiveLong) {
		this.primitiveLong = primitiveLong;
	}

	public float getPrimitiveFloat() {
		return primitiveFloat;
	}

	public void setPrimitiveFloat(float primitiveFloat) {
		this.primitiveFloat = primitiveFloat;
	}

	public double getPrimitiveDouble() {
		return primitiveDouble;
	}

	public void setPrimitiveDouble(double primitiveDouble) {
		this.primitiveDouble = primitiveDouble;
	}

	public char getPrimitiveChar() {
		return primitiveChar;
	}

	public void setPrimitiveChar(char primitiveChar) {
		this.primitiveChar = primitiveChar;
	}

}

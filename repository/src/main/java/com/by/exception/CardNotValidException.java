package com.by.exception;

public class CardNotValidException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public CardNotValidException(){
		super("会员卡无效");
	}
}

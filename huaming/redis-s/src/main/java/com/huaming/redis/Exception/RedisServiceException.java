package com.huaming.redis.Exception;

public class RedisServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errorCode = null;
	private Object[] errorParam = null;
	private Throwable caseObj = null;
	
	public RedisServiceException(Throwable t) {
		super(t);
		this.caseObj = t;
	}

	public RedisServiceException(String errorCode) {
		super(errorCode);
	}

	public RedisServiceException(String errorCode, Object[] errorParam) {
		this.errorCode = errorCode;
		this.errorParam = errorParam;
	}

	public RedisServiceException(String errorCode, Object[] errorParam, Throwable t) {
		super(t);
		this.errorCode = errorCode;
		this.errorParam = errorParam;
		this.caseObj = t;
	}

	public RedisServiceException(String message, Throwable t) {
		super(message, t);
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public Object[] getErrorParam() {
		return this.errorParam;
	}

	public Throwable getCaseObj() {
		return this.caseObj;
	}

	public String getMessageErrorCode() {
		String result = null;
		/*MessageSource messageSource = (MessageSource) SpringContextHolder.getBean("messageSource");
		if ((getErrorCode() != null) && (getErrorCode().trim().length() > 0)) {
			if (getCaseObj() == null) {
				result = messageSource.getMessage(getErrorCode(), getErrorParam(), Locale.getDefault());
			} else {
				Throwable errors = getCaseObj();
				while (errors != null) {
					if (!(StringUtils.isBlank(errors.getMessage())))
						break;
					errors = errors.getCause();
				}

				if (errors != null) {
					result = messageSource.getMessage(getErrorCode(), getErrorParam(), Locale.getDefault());
				} else
					result = messageSource.getMessage(getErrorCode(), getErrorParam(), Locale.getDefault());
			}
		} else {
			result = super.getMessage();
		}*/
		return result;
	}

	public String getMessage() {
		if (getErrorCode() != null) {
			return getMessageErrorCode();
		}
		return super.getMessage();
	}

}

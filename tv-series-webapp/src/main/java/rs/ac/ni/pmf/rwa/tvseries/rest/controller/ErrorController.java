package rs.ac.ni.pmf.rwa.tvseries.rest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import rs.ac.ni.pmf.rwa.tvseries.rest.dto.ErrorCode;
import rs.ac.ni.pmf.rwa.tvseries.rest.dto.ErrorDTO;

@Slf4j
@ControllerAdvice
@ResponseBody
public class ErrorController
{

	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorDTO handleMissingParameter(final MissingServletRequestParameterException e)
	{
		return ErrorDTO.builder()
				.code(ErrorCode.GENERAL_REQUEST_ERROR)
				.details(e.getMessage())
				.build();
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorDTO handleGeneralError(final Exception e)
	{
		return ErrorDTO.builder()
				.code(ErrorCode.GENERAL_REQUEST_ERROR)
				.details(e.getMessage())
				.build();
	}
}

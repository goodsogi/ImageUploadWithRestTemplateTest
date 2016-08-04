package com.example.imageuploadwithapachlibraryexample;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

public class RestUtil {
	public static RestTemplate getRestTemplate() {
		RestTemplate restTemplate = new RestTemplate(true);

		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.add(new ByteArrayHttpMessageConverter());

	    messageConverters.add(new StringHttpMessageConverter());
	    messageConverters.add(new ResourceHttpMessageConverter());

	   // String formEncoding = this.resources.getString(R.string.mc_rest_form_encoding);
	    //messageConverters.add(new MochaFormHttpMessageConverter(Charset.forName(formEncoding)));

	    messageConverters.add(new MappingJackson2HttpMessageConverter());

		restTemplate.setMessageConverters(messageConverters);

		restTemplate.setErrorHandler(new ResponseErrorHandler() {

			public boolean hasError(ClientHttpResponse resp) throws IOException {
				HttpStatus status = resp.getStatusCode();
				if (HttpStatus.CREATED.equals(status)
						|| HttpStatus.OK.equals(status)) {
					return false;
				} else {
					return true;
				}
			}

			public void handleError(ClientHttpResponse resp) throws IOException {
				throw new HttpClientErrorException(resp.getStatusCode());
			}
		});
		return restTemplate;
	}
}

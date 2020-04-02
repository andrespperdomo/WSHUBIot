package com.claro.hubiot.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.Provider;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.apache.commons.collections4.CollectionUtils;
import org.glassfish.jersey.server.ContainerRequest;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Entities.EscapeMode;
//import org.jsoup.safety.Whitelist;
import org.owasp.esapi.ESAPI;

import com.claro.hubiot.util.Constantes;
@PreMatching
@Provider
public class XSSFilter implements ContainerRequestFilter{


	private static Logger logger = LogManager.getLogger(Constantes.LOGGER_PRINCIPAL);

	/**
	 * @see ContainerRequestFilter#filter(ContainerRequest)
	 */
	@Override
	public void filter( ContainerRequestContext request )
	{
		logger.info("Inicia Validacion de Parametros");
		cleanQueryParams( request );
		cleanHeaders( request.getHeaders() );
	}


	/**
	 * Replace the existing query parameters with ones stripped of XSS vulnerabilities
	 * @param request
	 */
	private void cleanQueryParams( ContainerRequestContext request )
	{
		UriBuilder builder = request.getUriInfo().getRequestUriBuilder();
		MultivaluedMap<String, String> queries = request.getUriInfo().getQueryParameters();

		for( Map.Entry<String, List<String>> query : queries.entrySet() )
		{
			String key = query.getKey();
			List<String> values = query.getValue();

			List<String> xssValues = new ArrayList<String>();
			for( String value : values ) {
				xssValues.add( stripXSS( value ) );
			}

			Integer size = CollectionUtils.size( xssValues );
			builder.replaceQueryParam( key );

			if( size == 1 ) {
				builder.replaceQueryParam( key, xssValues.get( 0 ) );
			} else if( size > 1 ) {
				builder.replaceQueryParam( key, xssValues.toArray() );
			}
		}

		request.setRequestUri( builder.build() );
	}


	/**
	 * Replace the existing headers with ones stripped of XSS vulnerabilities
	 * @param headers
	 */	
	private void cleanHeaders( MultivaluedMap<String, String> headers )
	{
		logger.debug("header:");
		for( Map.Entry<String, List<String>> header : headers.entrySet() )
		{
			String key = header.getKey();
			List<String> values = header.getValue();

			List<String> cleanValues = new ArrayList<String>();
			for( String value : values ) {
				cleanValues.add( stripXSS( value ) );
				logger.debug(key+"="+value);
			}
			headers.put( key, cleanValues );
		}
	}

	/**
	 * Strips any potential XSS threats out of the value
	 * @param value
	 * @return
	 */
	public String stripXSS( String value )
	{
		if( value == null )
			return null;

		// Use the ESAPI library to avoid encoded attacks.
		value = ESAPI.encoder().canonicalize( value );

		// Avoid null characters
		value = value.replaceAll("&quot;\0&quot;", "&quot;&quot;");

		// Clean out HTML
		//        Document.OutputSettings outputSettings = new Document.OutputSettings();
		//        outputSettings.escapeMode( EscapeMode.xhtml );
		//        outputSettings.prettyPrint( false );
		//        value = Jsoup.clean( value, "", Whitelist.none(), outputSettings );

		return value;
	}
}
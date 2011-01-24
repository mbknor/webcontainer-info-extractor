package com.kjetland.wcie;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by IntelliJ IDEA.
 * User: mortenkjetland
 * Date: 1/24/11
 * Time: 11:11 AM
 * Utility class that is able to extract hidden info from the WebContainer your webapp is running in.
 *
 * The info can be extracted at deplytime before any requests have been made to your app.
 *
 * The following info us extracted.
 *
 *  * The port your WebContainer currently is listening on.
 *  * The contextPath your app is mounted on.
 *
 * The following WebContainers is supported:
 *
 * Glasfish V2
 * Jetty 6x and 7x
 *
 * The info is extracted without any dependency to any servlet or WebContainer-api/jars...
 *
 *
 */
public class WCInfoExtractor {

    public static WCInfo extractWCInfo( Object servletContext){
		String contextPath = null;
		Integer port = null;
        WCInfo.WebContainer wc = null;
		try{


			if( servletContext.getClass().getName().indexOf("jetty")>0){
				//works for jetty 6 and jetty 7
                wc = WCInfo.WebContainer.JETTY_6_or_7;

				Class clazz = servletContext.getClass();
				Method method = clazz.getMethod("getContextPath");
				contextPath = (String)method.invoke(servletContext);
				//servletContext.get

				Field appContextField = servletContext.getClass().getDeclaredFields()[0];
				appContextField.setAccessible( true);
				Object appContext = appContextField.get(servletContext);
				appContextField.setAccessible( false);
				Object servletHandler = getFieldValue(appContext, "_servletHandler");

				//Object servletHandler = getField(servletContext, "servletHandler" ).get(servletContext);

				Object server = getFieldValue(servletHandler, "_server");

				Object[] connectors = (Object[])getFieldValue(server, "_connectors");

				//pick first connector
				Object connector = connectors[0];
				Class connectorClazz = connector.getClass();
				port = (Integer)connectorClazz.getMethod("getPort").invoke(connector);


			}else if(servletContext.getClass().getName().startsWith("org.apache.catalina.core.ApplicationContextFacade")){
				//glassfish v2
                wc = WCInfo.WebContainer.GLASSFISH_v2;
				Object appContext = getFieldValue(servletContext, "context");
				Object webModule = getFieldValue(appContext, "context");
				contextPath = (String)getFieldValue(webModule, "encodedPath");
				Object virtualServer = getFieldValue(webModule, "parent");
				port = ((int[])getFieldValue(virtualServer, "ports"))[0];

				int a = 0;

			}
		}catch(Exception e){
			throw new RuntimeException("Error resolving WCInfo", e);
		}


		//make sure we start with /
		if( !contextPath.startsWith("/")){
			contextPath = "/"+contextPath;
		}

		return new WCInfo(wc, contextPath, port);
    }

    private static Object getFieldValue( Object o, String name ){
		try{
			Class c = o.getClass();
			while( c != null ){
				Field f = null;
				try{
					f = c.getDeclaredField( name);

					Object value = null;
					if( !f.isAccessible() ){
						f.setAccessible( true);
						value = f.get( o );
						f.setAccessible( false );
					}else{
						value = f.get( o );
					}

					return value;
				}catch(Exception e ){
					//nop
				}

				c = c.getSuperclass();
			}
			return null;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

}

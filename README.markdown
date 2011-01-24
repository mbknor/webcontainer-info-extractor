 Utility class that is able to extract hidden info from the WebContainer your webapp is running in.

 The info can be extracted at deploytime before any requests have been made to your app.

 The following info us extracted.

 * The port your WebContainer currently is listening on.
 * The contextPath your app is mounted on.

 The following WebContainers is supported:

 Glasfish V2
 Jetty 6x and 7x

 The info is extracted **without any dependency** to any servlet or WebContainer-api/jars...
 
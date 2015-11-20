package OMOPtables;

import java.util.concurrent.ConcurrentMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.http.HTTPException;
import java.util.Arrays;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.beans.XMLEncoder;
import org.json.JSONObject;
import org.json.XML;

public class PredictionsServlet extends HttpServlet {
    private Predictions predictions; // back-end bean

    // Executed when servlet is first loaded into container.
    // Create a Predictions object and set its servletContext
    // property so that the object can do I/O.
    @Override
    public void init() {
	predictions = new Predictions();
	predictions.setServletContext(this.getServletContext());
    }

    // GET /OMOPtables
    // GET /OMOPtables?id=n
    // If the HTTP Accept header is set to application/json (or an equivalent
    // such as text/x-json), the response is JSON and XML otherwise.
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
	String param = request.getParameter("id");
	Integer key = (param == null) ? null : new Integer(param.trim());

	// Check user preference for XML or JSON by inspecting
	// the HTTP headers for the Accept key.
	boolean json = false;
	String accept = request.getHeader("accept");
	if (accept != null && accept.contains("json")) json = true;
	
        // If no query string, assume client wants the full list.
        if (key == null) {
	    ConcurrentMap<Integer, Prediction> map = predictions.getMap();

	    // Sort the map's values for readability.
	    Object[] list = map.values().toArray();
	    Arrays.sort(list);

	    String xml = predictions.toXML(list);
	    sendResponse(response, xml, json);
	}
	// Otherwise, return the specified Prediction.
	else {
	    Prediction pred = predictions.getMap().get(key);

	    if (pred == null) { // no such Prediction
		String msg = key + " does not map to a prediction.\n";
		sendResponse(response, predictions.toXML(msg), false);
	    }
	    else { // requested Prediction found
		sendResponse(response, predictions.toXML(pred), json);
	    }
	}
    }

    // Method Not Allowed
    @Override
    public void doTrace(HttpServletRequest request, HttpServletResponse response) {
        throw new HTTPException(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    // Method Not Allowed
    @Override
    public void doHead(HttpServletRequest request, HttpServletResponse response) {
        throw new HTTPException(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    // Method Not Allowed
    @Override
    public void doOptions(HttpServletRequest request, HttpServletResponse response) {
        throw new HTTPException(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }	
	
	   // Method Not Allowed
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        throw new HTTPException(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

   // Method Not Allowed
    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) {
        throw new HTTPException(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }
	
   // Method Not Allowed
    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) {
        throw new HTTPException(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }
		
    // Send the response payload to the client.
    private void sendResponse(HttpServletResponse response, String payload, boolean json) {
	try {
	    // Convert to JSON?
	    if (json) {
		JSONObject jobt = XML.toJSONObject(payload);
		payload = jobt.toString(3); // 3 is indentation level for nice look
	    }

	    OutputStream out = response.getOutputStream();
	    out.write(payload.getBytes());
	    out.flush();
	}
	catch(Exception e) {
	    throw new HTTPException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
    }
}     

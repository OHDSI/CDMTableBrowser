package OMOPtables;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Collections;
import java.beans.XMLEncoder; // simple and effective
import javax.servlet.ServletContext;
import java.sql.*;

public class Predictions {
    private ConcurrentMap<Integer, Prediction> predictions;
    private ServletContext sctx;
    private AtomicInteger mapKey;

    public Predictions() { 
	predictions = new ConcurrentHashMap<Integer, Prediction>();
	mapKey = new AtomicInteger();
    }

    //** properties

    public void setServletContext(ServletContext sctx) {
	this.sctx = sctx;
    }
    public ServletContext getServletContext() { return this.sctx; }

    public void setMap(ConcurrentMap<String, Prediction> predictions) { 
	// no-op for now
    } 
	
    public ConcurrentMap<Integer, Prediction> getMap() {
	// Has the ServletContext been set?
	if (getServletContext() == null) return null;      

	// Have the data been read already?
	if (predictions.size() < 1) populate(); 

	return this.predictions;
    }

    public String toXML(Object obj) {
	String xml = null;

	try {
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    XMLEncoder encoder = new XMLEncoder(out);
	    encoder.writeObject(obj); // serialize to XML
	    encoder.close();
	    xml = out.toString(); // stringify
	}
	catch(Exception e) { }
	return xml;
    }

    public int addPrediction(Prediction p) {
	int id = mapKey.incrementAndGet();
	p.setId(id);
	predictions.put(id, p);
	return id;
    }

    //** utility
    private void populate() {
	
      // Create a variable for the connection string. Replace XXXXX by actual login info!!!
      String connectionUrl = "jdbc:sqlserver://localhost:1433;" +
         "databaseName=omop5_vocabulary;user=XXXXX;password=XXXXX";

      // Declare the JDBC objects.
      Connection con = null;
      Statement stmt = null;
      ResultSet rs = null;

      try {
         // Establish the connection.
         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
         con = DriverManager.getConnection(connectionUrl);

         // Create and execute an SQL statement that returns OMOP table metadata.
         String SQL = "SELECT * FROM [omop5_vocabulary].[dbo].[metadata_tables]";
         stmt = con.createStatement();
         rs = stmt.executeQuery(SQL);

         // Iterate through the data in the result set and display it.				
         while (rs.next()) {
		    Prediction p = new Prediction();
		    p.setTableName(rs.getString(2));
		    p.setTableDescription(rs.getString(3));
		    addPrediction(p);						
         }
      }

      // Handle any errors that may have occurred.
      catch (Exception e) {
         e.printStackTrace();
      }
      finally {
         if (rs != null) try { rs.close(); } catch(Exception e) {}
         if (stmt != null) try { stmt.close(); } catch(Exception e) {}
         if (con != null) try { con.close(); } catch(Exception e) {}
      }	
	
    }
}





package OMOPtables;

import java.io.Serializable;

// An array of Predictions is to be serialized
// into an XML or JSON document, which is returned to 
// the consumer on a request. 
public class Prediction implements Serializable, Comparable<Prediction> {
    private String tableName;         // Table name
    private String tableDescription;  // Table description
    private int    id;                // identifier used as lookup-key

    public Prediction() { }

    public void setTableName(String tableName) {
	this.tableName = tableName;
    }
    public String getTableName() {
	return this.tableName;
    }

    public void setTableDescription(String tableDescription) {
	this.tableDescription = tableDescription;
    }
    public String getTableDescription() {
	return this.tableDescription;
    }

    public void setId(int id) {
	this.id = id;
    }
    public int getId() {
	return this.id;
    }

    // implementation of Comparable interface
    public int compareTo(Prediction other) {
	return this.id - other.id;
    }	
}
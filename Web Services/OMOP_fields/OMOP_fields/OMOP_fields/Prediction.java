package OMOP_fields;

import java.io.Serializable;

// An array of Predictions is to be serialized
// into an XML or JSON document, which is returned to 
// the consumer on a request. 
public class Prediction implements Serializable, Comparable<Prediction> {
    private String tableName;           // table name
	private String fieldName;           // column name
	private String fieldDescription;	// column description
	private String fieldType;	        // column type
	private int  fieldRelation;         // -9 : primary key, n : foreign key, -1 : not a primary or foreign key
    private int    id;                  // identifier used as lookup-key

    public Prediction() { }

    public void setFieldDescription(String fieldDescription) {
	this.fieldDescription = fieldDescription;
    }
    public String getFieldDescription() {
	return this.fieldDescription;
    }

    public void setFieldName(String fieldName) {
	this.fieldName = fieldName;
    }
    public String getFieldName() {
	return this.fieldName;
    }

    public void setTableName(String tableName) {
	this.tableName = tableName;
    }
    public String getTableName() {
	return this.tableName;
    }	
	
	public void setFieldType(String fieldType) {
	this.fieldType = fieldType;
    }
    public String getFieldType() {
	return this.fieldType;
    }	

	public void setFieldRelation(int fieldRelation) {
	this.fieldRelation = fieldRelation;
    }
    public int getFieldRelation() {
	return this.fieldRelation;
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
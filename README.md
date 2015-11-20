# CHAOS 
## Introduction
CHAOS is Common_data_model Hierarchy and Attributes Overview System. CHAOS provides interactive overview of CDM tables, fields and relationships between fields. Definitions of each table and field can be reviewed by hovering over corresponding item in the graphical representation of CDM. For fields, two types of mouseover information can be chosen depending radio button setting: definitions or data types.

## Features
 1. Hovering over a table name results in presentation of the table’s definition
 1. Hovering over a field name results in presentation of the field’s definition or data type
 1. Type of information presented by hovering over a field name is indicated by radio button at the bottom of the CDM graph
 1. Clicking a table name results in presentation of the all fields of this table
 1. Clicking a field name results in presentation of relationship with other fields. Primary key has green background and foreign key has a yellow background.

## Screenshots
<img src="https://github.com/OHDSI/CDMTableBrowser/blob/master/images/screenshot-01.png" />
<img src="https://github.com/OHDSI/CDMTableBrowser/blob/master/images/screenshot-02.png" />
<img src="https://github.com/OHDSI/CDMTableBrowser/blob/master/images/screenshot-03.png" />
  
## Technology

CHAOS is built using Java and JavaScript technologies. The overall technical design of CHAOS is presented in the figure below. Information on CDM tables and fields is stored in two tables: Metadata_tables and Metadata_fields. These tables contain complete metadata information on CDN based on Version 5 Specifications. These two tables have to be added to the database containing CDM tables. 

<img src="https://github.com/OHDSI/CDMTableBrowser/blob/master/images/technology.png" />

Web services is written in Java and can be maintained by any servlet container. OMOPtables returns a collection in XML or JSON format representing information about CDM tables. OMOPtables?id=n returns information about table n. OMOP_fields returns a collection in XML or JSON format representing information about CDM fields. OMOPfields?id=n returns information about field n. Interactive graph is based on D3.js interactive visualization library.

## System requirements
CHAOS requires Java 1.8 or higher.

## Dependencies
The CDM metadata tables are provided in Excel and should be imported into CDM database. They reflect CDM v5 specifications.

## Getting Started
 1.	Import the two metadata tables into CDM database.
 1.	Compile java code.
 1.	Install web services in a servlet container.

## Comments/Questions
Github or [jf193@columbia.edu](mailto:jf193@columbia.edu) (Joseph Finkelstein)

As we do not have an actual database to connect to, this folder will serve to mimic one.

In order to make a more true to life mimic of a database each "database" is to be stored in separate folders under
    "DataBasePretender -> -yourDB-"
Each database will also include tables, in the case of SQL, or collections, in the case of MongoDB, these are to be represented as subfolders under
    "DataBasePretender -> -yourDB- -> -yourCollection-"


For the actual data that will be stored, in the case of MongoDB / document databases it is suggested to store each document as a separate .JSON file represented by
 a numerical _id, i.e. 1, 2 etc., optionally we can use things like a patients id as the unique identifier.
    "DataBasePretender -> -yourDB- -> -yourCollection- -> -patientId.JSON-"
    OR
    "DataBasePretender -> -yourDB- -> -yourCollection- -> -customId.JSON-"

For SQL tables one might consider exchanging each "table" that comes in the form of a folder with a .CSV file, as a .CSV file might also be the easiest way of inserting
 the data to an actual SQL database table.
    "DataBasePretender -> -yourDB- -> -yourTable- -> -yourTable.CSV-"
    OR
    "DataBasePretender -> -yourDB- -> -yourTable.CSV-"
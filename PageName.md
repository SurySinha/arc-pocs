# Introduction #
Notes


# Details #
http://www.hibernate.org/hib_docs/v3/api/

```
cd .m2
cd repository
cd hsqldb
cd hsqldb
cd 1.7.3.0

java -cp hsqldb-1.7.3.0.jar org.hsqldb.Server -database.0 file:/db/testdb -dbname.0 xdb
```

# JTA missing fix #
```
<dependency>
        <groupId>org.apache.geronimo.specs</groupId>
        <artifactId>geronimo-jta_1.1_spec</artifactId>
        <version>1.1</version>
</dependency>

```

# Adding log4j #

```
	<dependency>
		<groupId>log4j</groupId>
		<artifactId>log4j</artifactId>
		<version>1.2.13</version>
		<type>jar</type>
	</dependency>

```

# Main method that runs dbunit #
```
package com.vm;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;

/**
 * Loads test data in the form of DbUnit XML into a database.
 * 
 * @author Rick Hightower
 */
public class DbUnitDataLoader {
	public static void main(String... args) throws Exception {

		InputStream testData = DbUnitDataLoader.class
				.getResourceAsStream("/com/vmc/smac/test/spring/ski-test-data.xml");

		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/vmc", "vmc", "vmc");

		DbUnitDataLoader loader = new DbUnitDataLoader(testData, connection);

		loader.populateTestData();
		
		
		connection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/vmc", "vmc", "vmc");


		testData = DbUnitDataLoader.class
		.getResourceAsStream("/com/vmc/smac/test/spring/edu-test-data.xml");

		loader = new DbUnitDataLoader(testData, connection);
		
		loader.populateTestData();
		
		System.out.println("SUCCESS");
	}

	private InputStream testData;
	private Connection connection;

	public DbUnitDataLoader(InputStream testData, Connection connection) {
		this.testData = testData;
		this.connection = connection;
	}

	/**
	 * Replace existing data with test data
	 * 
	 * @throws Exception
	 */
	public void populateTestData() throws Exception {
		FlatXmlDataSet dataSet = new FlatXmlDataSet(testData);

		IDatabaseConnection con = new DatabaseConnection(connection);

		DatabaseOperation.CLEAN_INSERT.execute(con, dataSet);

		con.close();
		
		
	}
}

```

```
	<dependency>

		<groupId>log4j</groupId>

		<artifactId>log4j</artifactId>

		<version>1.2.13</version>

		<type>jar</type>

	</dependency>

```

Make sure you connect this way
```
db.connection.url=jdbc:hsqldb:hsql://localhost/xdb

hibernate.connection.url=jdbc:hsqldb:hsql://localhost/xdb

org.hibernate.cache.HashtableCacheProvider
```

http://java.dzone.com/articles/dependency-injection-an-introd
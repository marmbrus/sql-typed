# Typed Spark SQL

A library for integrating Spark SQL with [Scala Records](https://github.com/scala-records/scala-records)
                                                                                                                                                                                      
This library adds a string interpolator that allows users to run Spark SQL queries that return type-safe
results in Scala. SQL interpolation is invoked by prefixing a string literal with `sql`, and supports including RDDs using `$`. For example:

```scala
val sqlContext = new org.apache.spark.sql.SQLContext(sc)
import sqlContext._

case class Person(firstName: String, lastName: String, age: Int)
val people = sc.makeRDD(Person("Michael", "Armbrust", 30) :: Nil)

val michaels = sql"SELECT * FROM $people WHERE firstName = 'Michael'"
```

The result RDDs of interpolated SQL queries contain [Scala records](https://github.com/scala-records/scala-records) that have been *refined* with the output schema of the query.  This refinement means that you can access the columns of the result as you would normal fields of objects in scala, and that these fields will return the correct type.  Continuing the previous example:
```scala
assert(michaels.first().firstName == "Michael")
```

You can also use interpolation to include labmda functions that are in scope as UDFs.

```scala
import java.util.Calendar
val birthYear = (age: Int) => Calendar.getInstance().get(Calendar.YEAR) - age
val years = sql"SELECT $birthYear(age) FROM $people"
```

Results can also be refined into existing case class types when the names of the columns match up with the arguments to the class's constructor.
```scala
case class Employee(name: String, birthYear: Int)
val employees: RDD[Employee] =
  sql"SELECT lastName AS name, $birthYear(age) AS birthYear FROM $people".map(_.to[Employee])
```

Known limitations:
 - SQL Interpolation will only work then the included RDDs are of case classes and the type of the case class can be determined statically at compile time.
 - Null values for primitive columns will raise an Exception.
 - Escapes in strings may not be handled correctly.
 - Doesn't work with `"""` and new lines

Thanks to @gzm0 @vjovanov @hubertp @densh for Scala records and @ahirreddy for the initial work on the interpolator.                                                                              
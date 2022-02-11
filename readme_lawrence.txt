Libraries added: commons-csv-1.9.0.jar
                 hamcrest-core-1.3.jar
                 junit-4.13.2.jar

Build and run: same as before

Java version:java 11.0.14 2022-01-18 LTS
             Java(TM) SE Runtime Environment 18.9 (build 11.0.14+8-LTS-263)
             Java HotSpot(TM) 64-Bit Server VM 18.9 (build 11.0.14+8-LTS-263, mixed mode)

Assumption: the table in html file always has id "directory".
            the table in html file always has the same structure/tags (tr, th, td)

Design decision: In the case that the second file has the row with the same ID with the table in first file (e.g. 5555)
I choose not to merge the row from second file and issue a warning.
Although they have the same IDs, their row values may be different, so
forcing it to merge may result a row containing row values from different tables or overwriting existing values.
I don't think the behavior is desired.
#Deduplicater
A command line application that removes duplicate entries from a json file.

##How to use
1. clone this repository
1. `$ cd deduplicator/src`
1. `$ javac Main.java -sourcepath ../src -cp ../lib/json-simple-1.1.jar`
1. `$ java -cp "../lib/json-simple-1.1.jar:." Main fileLocation` replacing `fileLocation` with the location of the json file you would like to use

##Deduplication Criteria
Two records are considered duplicates if they have the same `id` or the same `email`.

If two records are duplicates, the record with the later date is preferred. If dates are the same, then the record that appears later in the file is preferred.

We assume that both `id` and `email` are case sensitive.

##Implementation Details

The program converts the JSON file in an array of `Record` objects (let's call that `records`). The array is sorted by date, and if those are equal then by order in the JSON file.

Then the `Deduplicater` creates a graph where each record is a node and an edge exists between two nodes if the nodes are duplicates.

After this graph is created, the `Deduplicater` removes the greatest record from the `records` array, adds it to an array called `uniqueRecords`, and removes all of its neighbors from `records`. This process repeats itself until there are no nodes left in `records`.

The resulting unique records are then printed to the console as well as the duplicate records that were removed.

##Sample Input
```
{
  "leads": [
    {
      "lastName": "Smith",
      "_id": "1",
      "address": "123 Maple St",
      "email": "john@bar.com",
      "entryDate": "2014-05-07T17:30:20+00:00",
      "firstName": "John"
    },
    {
      "lastName": "Kane",
      "_id": "1",
      "address": "44 Main St",
      "email": "tim@bar.com",
      "entryDate": "2014-05-07T17:30:20+00:00",
      "firstName": "Tim"
    }
  ]
}
```

##Sample Output
```
Deduplicater finished processing. Removed 1 duplicate!

Input (2):
{"leads":[{"lastName":"Smith","_id":"1","address":"123 Maple St","email":"john@bar.com","entryDate":"2014-05-07T17:30:20+00:00","firstName":"John"},{"lastName":"Kane","_id":"1","address":"44 Main St","email":"tim@bar.com","entryDate":"2014-05-07T17:30:20+00:00","firstName":"Tim"}]}

Output (1):
{"leads":[{"lastName":"Kane","_id":"1","address":"44 Main St","email":"tim@bar.com","entryDate":"2014-05-07T17:30:20+00:00","firstName":"Tim"}]}


Duplicate #1:

Original Record:
 {"lastName":"Smith","_id":"1","address":"123 Maple St","email":"john@bar.com","entryDate":"2014-05-07T17:30:20+00:00","firstName":"John"}
New Record:
 {"lastName":"Kane","_id":"1","address":"44 Main St","email":"tim@bar.com","entryDate":"2014-05-07T17:30:20+00:00","firstName":"Tim"}
Field Changes:
    email: john@bar.com -> tim@bar.com
    firstName: John -> Tim
    lastName: Smith -> Kane
    address: 123 Maple St -> 44 Main St
```
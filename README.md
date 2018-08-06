# parquet-generator

Parquet files generator. Useful for generating files for testing purposes. Allows defining uniqueness levels (percent value) for each column. 

## Config file

Config file allows you to define a structure of generated parquet file. Data types can be defined on the configuration file. If a nullable type is defined around 10% of values in that column will be filled with NULLs.

There is also a possibility to define uniquenessPercentage value. It defines how many unique values you can expect in a generated column.

Parquet data file will be filled up with random data.

```
{
  "outputFile":"/tmp/file.parquet",
  "rows":5000,
  "columns":[
    {
      "name":"id",
      "uniquenessPercentage":10,
      "type": ["int","null"]
    },
    {
      "name":"name",
      "uniquenessPercentage":100,
      "type": ["string"]
    },
    {
      "name":"location",
      "uniquenessPercentage":70,
      "type": ["string","null"]
    }
  ]
}
```

## Build
This is how you can build the project

```
git clone https://github.com/jwszolek/parquet-generator.git
cd ./parquet-generator
mvn clean install
```

## How to use it

```
java jar parquet-generator.jar -config sample-config.json
```

expected outout:

```
{"id": "1786", "name": "sD2ojyRWLX", "location": "FlOSmC0iRr"}
{"id": "null", "name": "i1ZsxFUVhq", "location": "M3lNXWm3ra"}
{"id": "null", "name": "VhwSfTFb0x", "location": "M4jFyRBU5M"}
{"id": "null", "name": "9yAyElnACg", "location": "aQowXmxnPs"}
{"id": "null", "name": "xaFnqvA8my", "location": "s10olWjMT3"}
{"id": "null", "name": "JO0NQpmlq6", "location": "lZF3UtH53U"}
{"id": "null", "name": "klJFA5EO8o", "location": "null"}
{"id": "null", "name": "B6qaJa2zr9", "location": "null"}
{"id": "null", "name": "XB0gaohb3b", "location": "null"}
{"id": "null", "name": "Vtqg6cektd", "location": "null"}
```











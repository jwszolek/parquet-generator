# parquet-generator

Parquet files generator. Useful for generating files for testing purposes. Allows defining uniqueness levels (percent value) for each column. 

## Config file

Config file allows you to define a structure of generated parquet file. The file will be filled up with random data. The type of data can be defined on the configuration file. If a nullable type is defined around 10% of values in that column will be filled with NULLs.

There is also a possibility to define uniquenessPercentage value. It defines how many unique values you can expect in a generated column.

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
      "type": ["string","null"]
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





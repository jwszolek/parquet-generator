# parquet-generator

Parquet files generator. Useful for generating files for testing purposes. Allows defining uniqueness levels (percent value) for each column. 

## Config file

'''
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
'''

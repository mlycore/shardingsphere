+++
title = "Key Generate Algorithm"
weight = 3
+++

## Background

In traditional database software development, automatic primary key generation is a basic requirement and various databases provide support for this requirement, such as MySQL's self-incrementing keys, Oracle's self-incrementing sequences, etc. 

After data sharding, it is a very tricky problem to generate global unique primary keys from different data nodes. Self-incrementing keys between different actual tables within the same logical table generate duplicate primary keys because they are not mutually perceived. 

Although collisions can be avoided by constraining the initial value and step size of self-incrementing primary keys, additional O&M rules must to be introduced, making the solution lack completeness and scalability. 

There are many third-party solutions that can perfectly solve this problem, such as UUID, which relies on specific algorithms to generate non-duplicate keys, or by introducing primary key generation services. 

In order to cater to the requirements of different users in different scenarios, Apache ShardingSphere not only provides built-in distributed primary key generators, such as UUID, SNOWFLAKE, but also abstracts the interface of distributed primary key generators to facilitate users to implement their own customized primary key generators. 

## Parameters
### Snowflake

Type: SNOWFLAKE

Attributes:

| *Name*                                        | *DataType* | *Description*                                                                | *Default Value* |
| --------------------------------------------- | ---------- | ---------------------------------------------------------------------------- | --------------- |
| max-tolerate-time-difference-milliseconds (?) | long       | The max tolerate time for different server's time difference in milliseconds | 10 milliseconds |
| max-vibration-offset (?)                      | int        | The max upper limit value of vibrate number, range `[0, 4096)`. Notice: To use the generated value of this algorithm as sharding value, it is recommended to configure this property. The algorithm generates key mod `2^n` (`2^n` is usually the sharding amount of tables or databases) in different milliseconds and the result is always `0` or `1`. To prevent the above sharding problem, it is recommended to configure this property, its value is `(2^n)-1`| 1 |

### Nano ID

Type:NANOID

Configurable Property:none

### UUID

Type: UUID

Attributes: None

### CosId

Type: COSID

Attributes：

| *Name*    | *DataType* | *Description*                                                                                                                                                                      | *Default Value* |
|-----------|------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------|
| id-name   | String     | ID generator name                                                                                                                                                                  | `__share__`     |
| as-string | bool       | Whether to generate a string type ID: Convert `long` type ID to Base-62 `String` type (`Long.MAX_VALUE` maximum string length is 11 digits), and ensure the ordering of string IDs | `false`         |

### CosId-Snowflake

Type: COSID_SNOWFLAKE

Attributes：

| *Name*    | *DataType* | *Description*                                                                                                                                                                      | *Default Value* |
|-----------|------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------|
| epoch     | String     | EPOCH of Snowflake ID Algorithm                                                                                                                                                    | `1477929600000` |
| as-string | bool       | Whether to generate a string type ID: Convert `long` type ID to Base-62 `String` type (`Long.MAX_VALUE` maximum string length is 11 digits), and ensure the ordering of string IDs | `false`         |

## Procedure

1. Policy of distributed primary key configurations is for columns when configuring data sharding rules.

## Sample

- Snowflake Algorithms

```PlainText
keyGenerators:
  snowflake:
    type: SNOWFLAKE
```

- NanoID

```PlainText
keyGenerators:
  nanoid:
    type: NANOID
```

- UUID

```PlainText
keyGenerators:
  nanoid:
    type: UUID

```
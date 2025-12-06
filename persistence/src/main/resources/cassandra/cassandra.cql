CREATE KEYSPACE meteo WITH replication =
    {'class': 'SimpleStrategy', 'replication_factor' : 2};

CREATE TABLE meteo.geometeo (device_id varchar,      -- "70:ee:50:84:33:6a"
                             module_id varchar,      -- "02:00:00:84:50:66", "03:00:00:07:c3:ee", "03:00:00:07:11:9c"
                             type varchar,           -- "temperature", "humidity", "CO2", "pressure", "noise"
                             date_begin timestamp,   -- Timestamp of the first measure to retrieve (Local Unix Time in seconds). By default, it will retrieve the oldest data available.
                             date_end timestamp,     -- Timestamp of the last measure to retrieve (Local Unix Time in seconds)
                             scale varchar,          -- Timeframe between two measurements {30min, 1hour, 3hours, 1day, 1week, 1month}
                             optimize boolean,       -- Optimized format of the answer. Default is true. For mobile apps we recommend True to save bandwidth. If bandwidth isn't an issue, we recommend False as it is easier to parse.
                             realtime boolean,       --  If scale different than max, timestamps are by default offset + scale/2. To get exact timestamps, use true. Default is false.
                             measure map<timestamp, double>,  -- measure
                             PRIMARY KEY ((device_id, module_id, type), date_begin, date_end, scale) )
    WITH comment = '/getmeasure for a specified time range from a specified Netatmo device Id and module id';
-- Only clustering key columns can be defined in CLUSTERING ORDER directive


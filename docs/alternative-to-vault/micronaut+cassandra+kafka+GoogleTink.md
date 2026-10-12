cassandra as persistence storage for tokens.
Kafka topic for notifications: `token-notifications`: token-updated
GoogleTink for cryptography.

The instance the first the token is updated is the one that 
    1. sends request to netatmo
      curl -X POST "https://api.netatmo.com/oauth2/token" \
        -H "Content-Type: application/x-www-form-urlencoded;charset=UTF-8" \
        -d "grant_type=refresh_token" \
        -d "refresh_token=61ec5c9e06cc231aa74e2978|495a027b79b80d448a06628ec97afb46" \
        -d "client_id=client id" \
        -d "client_secret=client secret" \
        -d "scope=read_station read_thermostat write_thermostat
      
    2. on point 1 success updates the token in cassandra.
    3. send the message: token-updated{access token is updated} to kafka topic.

on token-updated each instance will update the token from cassandra.
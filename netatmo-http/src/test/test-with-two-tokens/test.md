# Test with two tokens
## Create two tokens manually: https://dev.netatmo.com/apps/65757c71fcc8cd8fda0399ff?code=6768b18607839682c47aec2d8f07de40
    ### Access token: 61ec5c9e06cc231aa74e2978|873102f467177c8aeb33a79610dbb453
    ### Refresh token: 61ec5c9e06cc231aa74e2978|495a027b79b80d448a06628ec97afb46
## Get token1
    ### Access token: 61ec5c9e06cc231aa74e2978|873102f467177c8aeb33a79610dbb453
    ### Refresh token: 61ec5c9e06cc231aa74e2978|495a027b79b80d448a06628ec97afb46
## Get token2, using refresh token from token1
    curl -X POST "https://api.netatmo.com/oauth2/token" \
        -H "Content-Type: application/x-www-form-urlencoded;charset=UTF-8" \
        -d "grant_type=refresh_token" \
        -d "refresh_token=61ec5c9e06cc231aa74e2978|495a027b79b80d448a06628ec97afb46" \
        -d "client_id=65757c71fcc8cd8fda0399fe" \
        -d "client_secret=Kn85T8tRUl5ovBTMCqlwcagAkVJW1xg4OAKXvuTsJuc"
{"access_token":"61ec5c9e06cc231aa74e2978|07b8082fc1bef0fde023f19f72bad465","refresh_token":"61ec5c9e06cc231aa74e2978|495a027b79b80d448a06628ec97afb46","expires_in":10800,"expire_in":10800,"scope":["read_station"]}

## Call API with token1
curl -X GET "https://api.netatmo.com/api/getmeasure?device_id=70%3AEE%3A50%3A84%3A33%3A6A&scale=1day&type=temperature&type=humidity&type=co2&type=pressure&optimize=false&real_time=false" -H "accept: application/json" -H "Authorization: Bearer 61ec5c9e06cc231aa74e2978|873102f467177c8aeb33a79610dbb453"
{"error":{"code":2,"message":"Invalid access token"}} as soon as "Get token2" is called the token1 is invalidated.
## Call API with token2
curl -X GET "https://api.netatmo.com/api/getmeasure?device_id=70%3AEE%3A50%3A84%3A33%3A6A&scale=1day&type=temperature&type=humidity&type=co2&type=pressure&optimize=false&real_time=false" -H "accept: application/json" -H "Authorization: Bearer 61ec5c9e06cc231aa74e2978|07b8082fc1bef0fde023f19f72bad465"
working ok.
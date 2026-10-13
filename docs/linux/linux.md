lsof -i:9042
sudo lsof -nP -iTCP -sTCP:LISTEN | grep 9042
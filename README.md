## IndexTracker

```
docker build --no-cache=true -t indextracker .

docker run --rm -it -d \
-v $(pwd)/:/java/ \
-e TZ=Asia/Kolkata \
-p 18990:18990 \
--name indextracker_container \
indextracker
```

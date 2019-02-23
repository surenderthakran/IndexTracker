## IndexTracker

```
docker build --no-cache=true -t blogger .

docker run --rm -it -d \
-v $(pwd)/:/java/ \
-p 18990:18990 \
--name indextracker_container \
indextracker
```

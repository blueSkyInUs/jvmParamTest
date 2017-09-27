local lock=redis.call("get",KEYS[1])
if (not lock)
then
    local expireSecond=ARGV[1]
    redis.call("setex",KEYS[1],expireSecond,ARGV[2])
    return "true"
else
    return "false"
end
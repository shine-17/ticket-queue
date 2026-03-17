--1. active count < capacity 일 경우 active user 등록 후 active token 발급
--  key: active:{showId}, member: userId, score: expireTime
--2. active count > capacity 일 경우 waiting user 등록 후 waiting token 발급
--  key: waiting:{showId}, member: userId, score: currentTime

-- 입력 매개변수 파싱
local userId = tonumber(ARGV[1])
local capacity = tonumber(ARGV[2])
local currentTime = tonumber(ARGV[3])
local expireTime = tonumber(ARGV[4])

if activeCount < capacity then
    -- active queue 등록
    redis.call("ZADD", KEYS[1], expireTime, userId)
    return {"ACTIVE"}
else
    -- waiting queue 등록
    redis.call("ZADD", KEYS[2], currentTime, userId)

    -- 대기 순서
    local position = redis.call("ZRANK", KEYS[2], userId)

    return {"WAITING", position}
end
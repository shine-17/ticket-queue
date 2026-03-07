--1. polling
--1-1. active user 일 경우 active token 발급 (TTL: 5분)
--1-2. waiting user 일 경우 waiting status 응답

--2. active count < capacity 일 경우 active user 등록 후 active token 발급
--3. active count > capacity 일 경우 waiting user 등록 후 waiting token 발급

-- 입력 매개변수 파싱
local userId = tonumber(ARGV[1])
local capacity = tonumber(ARGV[1])
local currentTimeStamp = ARGV[2]
local ttl = tonumber(ARGV[3])

-- active 상태인지 확인 (중복 요청 방지)
local isActive = redis.call("ZSCORE", KEYS[1], userId)
if isActive then
    return {"ALREADY_ACTIVE"}
end

-- waiting 상태인지 확인
local isWaiting = redis.call("ZSCORE", KEYS[2], userId)
if isWaiting then
    local rank = redis.call("ZRANK", KEYS[2], userId)
    return {"ALREADY_WAITING", rank}
end

local activeCount = redis.call("GET", KEYS[1])
if not activeCount then
    activeCount = 0
end

if activeCount < capacity then
    -- active queue 등록
    redis.call("ZADD", KEYS[1], currentTimeStamp, userId)
    return {"ACTIVE"}
else
    -- waiting queue 등록
    redis.call("ZADD", KEYS[2], currentTimeStamp, userId)

    -- 대기 순서
    local rank = redis.call("ZRANK", KEYS[2], userId)

    return {"WAITING", rank}
end
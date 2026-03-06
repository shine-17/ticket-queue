--1. active count < capacity 일 경우 active user 등록 후 active token 발급

--2. active count > capacity 일 경우 waiting user 등록 후 waiting token 발급
--3. polling
--3-1. active user 일 경우 active token 발급 (TTL: 5분)
--3-2. waiting user 일 경우 waiting status 응답

-- 입력 매개변수 파싱
local userId = tonumber(ARGV[1])
local capacity = tonumber(ARGV[1])
local currentTimeStamp = ARGV[2]
local ttl = tonumber(ARGV[3])

local activeCount = redis.call("GET", KEYS[1])

if not activeCount then
    activeCount = 0
end

-- 이미 active 상태인지 확인 (중복 요청 방지)
local isActive = redis.call("ZSCORE", KEYS[1], userId)
if isActive then
    return {"ALREADY_ACTIVE"}
end

if activeCount < capacity then
    -- active 허용
    redis.call("INCR", KEYS[1])
    redis.call("SET", KEYS[3], ARGV[2], "EX", ARGV[4])
    return { "ADMIT" }
else
    -- waiting queue 등록 (중복 방지)
    redis.call("ZADD", KEYS[2], "NX", ARGV[3], ARGV[2])
    local rank = redis.call("ZRANK", KEYS[2], ARGV[2])
    return { "WAIT", rank }
end
var TimeLimitedCache = function () {
    this.cache = new Map();
};

/**
 * @param {number} key
 * @param {number} value
 * @param {number} duration
 * @return {boolean}
 */
TimeLimitedCache.prototype.set = function (key, value, duration) {
    const now = Date.now();

    const exists =
        this.cache.has(key) && this.cache.get(key).expire > now;

    this.cache.set(key, {
        value: value,
        expire: now + duration
    });

    return exists;
};

/**
 * @param {number} key
 * @return {number}
 */
TimeLimitedCache.prototype.get = function (key) {
    const data = this.cache.get(key);

    if (!data || data.expire <= Date.now()) {
        return -1;
    }

    return data.value;
};

/**
 * @return {number}
 */
TimeLimitedCache.prototype.count = function () {
    const now = Date.now();
    let cnt = 0;

    for (const [key, data] of this.cache) {
        if (data.expire > now) {
            cnt++;
        }
    }

    return cnt;
};